package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.common.web.vo.SearchColumnVo;
import com.dsdl.eidea.common.web.vo.SearchConditionParam;
import com.dsdl.eidea.common.web.vo.SearchFormRow;
import com.dsdl.eidea.core.def.RelOperDef;
import com.dsdl.eidea.core.def.PageFieldInputType;
import com.dsdl.eidea.core.def.SearchPageType;
import com.dsdl.eidea.core.entity.bo.CommonSearchResult;
import com.dsdl.eidea.core.entity.bo.SearchBo;
import com.dsdl.eidea.core.entity.bo.SearchColumnBo;
import com.dsdl.eidea.core.service.SearchService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.util.StringUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/20 10:12.
 * 生成公共查询代码模块
 */
@Controller
public class CommonSearchController {
    private static final Logger log = Logger.getLogger(CommonSearchController.class);
    @Autowired
    private SearchService searchService;
    @RequestMapping(value = "/common/searchPage", method = RequestMethod.GET)
    public ModelAndView init(String uri, HttpSession session) {

        RelOperDef[] operDefs = RelOperDef.values();
        Gson gson = new Gson();
        ModelAndView modelAndView = new ModelAndView("/common/common_search");
        SearchBo search = searchService.getSearchBoByUri(uri);
        modelAndView.addObject("uri", uri);
        if (search == null||search.getSearchColumnBoList().size()==0) {
            modelAndView.setViewName("/common/common_search_empty");
            return modelAndView;
        } else if (search.getShowType() == SearchPageType.NORMAL.getKey()) {
            modelAndView.setViewName("/common/common_search_normal");
        }
        List<SearchColumnBo> searchColumns = search.getSearchColumnBoList();
        searchColumns.sort(Comparator.comparingInt(SearchColumnBo::getId));
        List<SearchColumnVo> searchColumnVos = new ArrayList<>();
        for (SearchColumnBo searchColumn : searchColumns) {
            SearchColumnVo searchColumnVo = new SearchColumnVo();
            searchColumnVo.setColumnId(searchColumn.getId());
            searchColumnVo.setColumnName(searchColumn.getPropertyName());
            searchColumnVo.setColumnLabel(searchColumn.getName());
            searchColumnVo.setDataType(searchColumn.getDataType());
            searchColumnVo.setShowType(searchColumn.getShowType());
            searchColumnVo.setNewline(searchColumn.getNewline());

            String relOper = searchColumn.getRelOper();
            int[] ids = searchService.getRelOpersForOperStr(relOper);
            List<String> relOpearArray = new ArrayList<>();
            for (int id : ids) {
                for (RelOperDef operDef : operDefs) {
                    if (id == operDef.getKey()) {
                        relOpearArray.add(operDef.getDesc());
                    }
                }
            }
            searchColumnVo.setOpearType(relOpearArray.get(0));
            searchColumnVo.setRelOpearList(relOpearArray);
            if (PageFieldInputType.SELECT.getKey() == searchColumn.getShowType()) {
                List<CommonSearchResult> commonSearchResultList = searchService.getCommonSearchListByColumnId(searchColumn.getId());
                searchColumnVo.setCommonSearchResultList(commonSearchResultList);
            }
            searchColumnVos.add(searchColumnVo);
        }
        List<SearchColumnVo> addedSearchColumnVoList = (List<SearchColumnVo>) session.getAttribute(uri + WebConst.SESSION_SEARCH_PARAM);
        if (search.getShowType() == SearchPageType.NORMAL.getKey()) {
            /**
             * 把已经赋值的值，赋给查询界面
             */
            if (addedSearchColumnVoList != null) {
                for (SearchColumnVo addedVo : addedSearchColumnVoList) {
                    for (SearchColumnVo searchColumnVo : searchColumnVos) {
                        if (addedVo.getColumnId().equals(searchColumnVo.getColumnId())) {
                            searchColumnVo.setValue(addedVo.getValue());
                        }
                    }
                }
            }
            List<SearchFormRow> searchFormRows = new ArrayList<>();
            SearchFormRow searchFormRow = null;
            for (SearchColumnVo searchColumnVo : searchColumnVos) {
                if (searchFormRow == null) {
                    searchFormRow = new SearchFormRow();
                } else if ("Y".equals(searchColumnVo.getNewline())) {
                    searchFormRows.add(searchFormRow);
                    searchFormRow = new SearchFormRow();
                }
                searchFormRow.addSearchColumnVo(searchColumnVo);
            }
            searchFormRows.add(searchFormRow);
            modelAndView.addObject("searchFormRows", searchFormRows);
        } else {

            if (addedSearchColumnVoList == null) {
                modelAndView.addObject("addedSearchColumnVoListJson", "[]");
            } else {
                modelAndView.addObject("addedSearchColumnVoListJson", gson.toJson(addedSearchColumnVoList));
            }
        }
        modelAndView.addObject("searchColumnJson", gson.toJson(searchColumnVos));
        return modelAndView;
    }

    @RequestMapping(value = "/common/doSearch", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Void> saveSearchConditions(@RequestBody SearchConditionParam searchConditionParam, HttpSession session) {
        session.setAttribute(searchConditionParam.getUri() + WebConst.SESSION_SEARCH_PARAM, searchConditionParam.getSearchColumnVoList());
        return JsonResult.success(null);
    }

    @RequestMapping(value = "/common/doSearch2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonResult<Void> saveSearchConditions2(@RequestBody SearchConditionParam searchConditionParam, HttpSession session) {
        List<SearchColumnVo> searchColumnVoList=searchConditionParam.getSearchColumnVoList();
        String uri=searchConditionParam.getUri();
        try {
            List<SearchColumnVo> voList = new ArrayList<>();
            for (SearchColumnVo searchColumnVo : searchColumnVoList) {
                if (StringUtil.isNotEmpty(searchColumnVo.getValue()))
                    voList.add(searchColumnVo);
            }
            session.setAttribute(uri + WebConst.SESSION_SEARCH_PARAM, voList);
            return JsonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), e.getMessage());
        }
    }
}
