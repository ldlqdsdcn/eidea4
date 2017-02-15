package com.dsdl.eidea.base.web.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.vo.ChangelogVo;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dsdl.eidea.base.entity.bo.ChangelogBo;
import com.dsdl.eidea.base.service.ChangelogService;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;

@Controller
@RequestMapping("/base/changelog")
public class ChangelogController {
    @Autowired
    private ChangelogService changelogService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/changelog/changelog");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    public ApiResult<List<ChangelogBo>> list(HttpSession session) {
        List<ChangelogBo> changelogBoList = changelogService.getChangelogList(new Search());
        return ApiResult.success(changelogBoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    public ApiResult<ChangelogVo> get(Integer id) {

        ChangelogBo changelogBo = changelogService.getChangelogBo(id);
        List<TableColumnBo> tableColumnBoList = changelogService.getChangelogHeader(changelogBo.getName());
        List<ChangelogBo> changelogBoList = new ArrayList<>();
        changelogBoList.add(changelogBo);
        ChangelogVo changelogVo = buildChangeLogVo(tableColumnBoList, changelogBoList);
        changelogVo.setChangelogBo(changelogBo);


        return ApiResult.success(changelogVo);
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

    @RequestMapping(value = "/showAllChanges", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    public ApiResult<ChangelogVo> showTableChanges(String tableName) {
        Search search = new Search();
        search.addFilterEqual("tablePo.tableName", tableName);
        List<ChangelogBo> changelogBoList = changelogService.getChangelogList(search);
        List<TableColumnBo> tableColumnBoList = changelogService.getChangelogHeader(tableName);
        ChangelogVo changelogVo = buildChangeLogVo(tableColumnBoList, changelogBoList);
        return ApiResult.success(changelogVo);
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
