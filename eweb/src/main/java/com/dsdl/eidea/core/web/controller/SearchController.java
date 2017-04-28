package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.def.RelOperDef;
import com.dsdl.eidea.core.def.SearchDataTypeDef;
import com.dsdl.eidea.core.def.SearchPageFieldInputType;
import com.dsdl.eidea.core.def.SearchPageType;
import com.dsdl.eidea.core.entity.bo.KeyValue;
import com.dsdl.eidea.core.entity.bo.SearchBo;
import com.dsdl.eidea.core.entity.bo.SearchColumnBo;
import com.dsdl.eidea.core.service.SearchService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 13:41.
 */
@Controller
@RequestMapping("/core/search")
public class SearchController {
    private static final String URI = "core_search";
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/search/search");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<SearchBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<SearchBo> searchBoList = searchService.findList(search);
        return JsonResult.success(searchBoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<SearchBo> get(Integer id) {
        SearchBo searchBo = null;
        if (id == null) {
            searchBo = new SearchBo();
            searchBo.setIsactive("N");
        } else {
            searchBo = searchService.getSearchBo(id);
            List<SearchColumnBo> searchColumnBoLists = searchBo.getSearchColumnBoList();
            for (SearchColumnBo searchColumnBo : searchColumnBoLists) {
                mapperRelOperator(searchColumnBo);
            }
        }
        return JsonResult.success(searchBo);
    }

    @RequestMapping(value = "/addOneColumn", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<SearchColumnBo> addNewColumn() {
        SearchColumnBo searchColumnBo = new SearchColumnBo();
        mapperRelOperator(searchColumnBo);
        return JsonResult.success(searchColumnBo);
    }

    private void mapperRelOperator(SearchColumnBo searchColumnBo) {
        List<KeyValue> keyValueList = new ArrayList<>();

        String relOper = searchColumnBo.getRelOper();
        int[] ids = searchService.getRelOpersForOperStr(relOper);
        RelOperDef[] relOperDefs = RelOperDef.values();
        for (RelOperDef relOperDef : relOperDefs) {
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(relOperDef.getKey());
            keyValue.setDesc(relOperDef.getDesc());
            for (int id : ids) {
                if (id == relOperDef.getKey()) {
                    keyValue.setChecked(true);
                    break;
                }
            }
            keyValueList.add(keyValue);
        }
        searchColumnBo.setRelOperList(keyValueList);
    }

    /**
     * @param searchBo
     * @return
     */
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<SearchBo> saveForCreated(@RequestBody @Validated SearchBo searchBo, HttpSession session) {
        UserResource userResource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (searchService.getSearchBoByUri(searchBo.getUri()) != null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), userResource.getMessage("search.error.url_exist"));
        }
        searchBo = searchService.saveSearchBo(searchBo);
        return get(searchBo.getId());
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<SearchBo> saveForUpdated(@RequestBody @Validated SearchBo searchBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (searchBo.getId() == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.primary_key.isempty"));
        }
        if (searchService.getSearchBoByUri(searchBo.getUri()) != null) {
            if (searchService.getSearchBoByUri(searchBo.getUri()).getId() == searchBo.getId()) {
                searchBo = searchService.saveSearchBo(searchBo);
            } else {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("search.error.url_exist"));
            }
        }else {
            searchBo = searchService.saveSearchBo(searchBo);

        }
        return get(searchBo.getId());
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<List<SearchBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (ids == null || ids.length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        searchService.deleteSearches(ids);
        return list(session);
    }

    @RequestMapping(value = "/getSelectList", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<String> getSelectList() {
        SearchPageType[] searchPageTypes = SearchPageType.values();
        JsonObject listObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (SearchPageType columnDataType : searchPageTypes) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", columnDataType.getKey());
            jsonObject.addProperty("desc", columnDataType.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("searchPageType", jsonArray);
        jsonArray = new JsonArray();
        for (RelOperDef relOperDef : RelOperDef.values()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", relOperDef.getKey());
            jsonObject.addProperty("desc", relOperDef.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("relOper", jsonArray);
        jsonArray = new JsonArray();
        for (SearchPageFieldInputType searchPageFieldInputType : SearchPageFieldInputType.values()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", searchPageFieldInputType.getKey());
            jsonObject.addProperty("desc", searchPageFieldInputType.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("searchPageFieldInput", jsonArray);
        jsonArray = new JsonArray();
        for (SearchDataTypeDef searchDataTypeDef : SearchDataTypeDef.values()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("key", searchDataTypeDef.getKey());
            jsonObject.addProperty("desc", searchDataTypeDef.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("searchDataType", jsonArray);
        return JsonResult.success(listObject.toString());
    }
}
