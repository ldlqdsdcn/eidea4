package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.def.JavaDataType;
import com.dsdl.eidea.core.entity.bo.TableBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.service.TableService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 16:24.
 */
@Controller
@RequestMapping("/core/table")
public class TableController {
    private static final String URI = "core_table";
    @Autowired
    private TableService tableService;
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/table/table");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<List<TableBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<TableBo> tablePoList = tableService.findList(search);
        return JsonResult.success(tablePoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<TableBo> get(Integer id) {
        TableBo tableBo = null;
        if (id == null) {
            tableBo = new TableBo();
        } else {

            tableBo = tableService.getTableBo(id);


        }
        return JsonResult.success(tableBo);
    }

    /**
     * @param tableBo
     * @return
     */
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<TableBo> saveForUpdated(@RequestBody TableBo tableBo) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (tableBo.getId() == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.primary_key.isempty"));
        }
        tableBo = tableService.saveTableBo(tableBo);
        return get(tableBo.getId());
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<TableBo> saveForCreated(@RequestBody TableBo tableBo) {
        tableBo = tableService.saveTableBo(tableBo);
        return get(tableBo.getId());
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<List<TableBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (ids == null || ids.length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.select_delete"));
        }
        tableService.deleteTables(ids);
        return list(session);
    }

    @RequestMapping(value = "/getJavaTypeList", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<String> getJavaTypeList() {
        JavaDataType[] columnDataTypes = JavaDataType.values();
        JsonArray jsonArray = new JsonArray();
        for (JavaDataType columnDataType : columnDataTypes) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", columnDataType.getKey());
            jsonObject.addProperty("desc", columnDataType.getDesc());
            jsonArray.add(jsonObject);
        }

        return JsonResult.success(jsonArray.toString());
    }

    @RequestMapping(value = "/getTableInfo", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<TableMetaDataBo> getTableInfo(String tableName) {
        TableMetaDataBo tableMetaDataBo = tableService.getTableDescription(tableName);
        return JsonResult.success(tableMetaDataBo);
    }

    @RequestMapping(value = "/saveTableInfo", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<TableMetaDataBo> saveTableInfo(@RequestBody TableMetaDataBo tableInfo) {
        try {
            tableService.saveTableInfoByWizard(tableInfo);
        } catch (Exception validateException) {
            validateException.printStackTrace();
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), validateException.getMessage());
        }
        return JsonResult.success(tableInfo);
    }
}
