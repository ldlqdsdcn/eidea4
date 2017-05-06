package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.bo.ChangelogBo;
import com.dsdl.eidea.base.service.ChangelogService;
import com.dsdl.eidea.base.web.vo.ChangelogVo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;


@Controller
@RequestMapping("/base/changelog")
public class ChangelogController {
    @Autowired
    private ChangelogService changelogService;

    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/changelog/changelog");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDbPaging());
        return modelAndView;
    }

    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<PaginationResult<ChangelogBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        PaginationResult<ChangelogBo> changelogBoList = changelogService.getChangelogList(new Search(),queryParams);
        return JsonResult.success(changelogBoList);
    }

    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<ChangelogVo> get(Integer id) {

        ChangelogBo changelogBo = changelogService.getChangelogBo(id);
        List<TableColumnBo> tableColumnBoList = changelogService.getChangelogHeader(changelogBo.getName());
        List<ChangelogBo> changelogBoList = new ArrayList<>();
        changelogBoList.add(changelogBo);
        ChangelogVo changelogVo = buildChangeLogVo(tableColumnBoList, changelogBoList);
        changelogVo.setChangelogBo(changelogBo);


        return JsonResult.success(changelogVo);
    }

    private ChangelogVo buildChangeLogVo(List<TableColumnBo> tableColumnBoList, List<ChangelogBo> changelogBoList) {
        ChangelogVo changelogVo = new ChangelogVo();
        List<String> headerKeyList = new ArrayList<>();
        List<String> headerList = new ArrayList<>();
        headerList.add("主键");
        headerList.add("业务主键");
        headerList.add("操作用户");
        headerList.add("操作");
        headerList.add("操作时间");

        for (TableColumnBo tableColumnBo : tableColumnBoList) {
            headerList.add(tableColumnBo.getName());
            headerKeyList.add(tableColumnBo.getPropertyName());
        }
        changelogVo.setHeader(headerList);

        List<List<String>> bodyList = new ArrayList<>();


        for (ChangelogBo item : changelogBoList) {
            Map<String, Object> body = jsonToMap(item);
            List<String> columnList = new ArrayList<>();
            columnList.add(item.getPk());
            columnList.add(item.getBuPk());
            columnList.add(item.getSysUser());
            String operateType = item.getOperateType();
            if ("D".equals(operateType)) {
                columnList.add("删除");
            } else if ("I".equals(operateType)) {
                columnList.add("添加");
            } else if ("U".equals(operateType)) {
                columnList.add("更新");
            }
            columnList.add(String.valueOf(item.getInDate()));
            for (String header : headerKeyList) {
                String value = String.valueOf(body.get(header));
                columnList.add(value);
            }
            bodyList.add(columnList);
        }
        changelogVo.setBodyList(bodyList);
        return changelogVo;
    }

    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/showAllChanges", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<ChangelogVo> showTableChanges(String tableName, String pk) {
        Search search = new Search();
        search.addFilterEqual("tablePo.tableName", tableName);
        search.addFilterEqual("pk",pk);
        List<ChangelogBo> changelogBoList = changelogService.getChangelogList(search,new QueryParams()).getData();
        List<TableColumnBo> tableColumnBoList = changelogService.getChangelogHeader(tableName);
        ChangelogVo changelogVo = buildChangeLogVo(tableColumnBoList, changelogBoList);
        return JsonResult.success(changelogVo);
    }

    private Map<String, Object> jsonToMap(ChangelogBo cl) {
        Gson gson = new Gson();
        Map<String, Object> valueMap = new HashMap<String, Object>();
        // JSONObject jsonObject=JSONObject.fromObject(cl.getContext());
        Map<String, String> object = gson.fromJson(cl.getContext(), HashMap.class);
        Set<String> set = object.keySet();
        String key = null;
        Object value = null;
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();
            value = object.get(key);
            valueMap.put(key, value);
        }

        return valueMap;
    }
}
