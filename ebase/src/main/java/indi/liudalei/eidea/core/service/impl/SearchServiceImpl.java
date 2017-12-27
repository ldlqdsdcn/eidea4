package indi.liudalei.eidea.core.service.impl;

import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dao.SearchColumnDao;
import indi.liudalei.eidea.core.dao.SearchDao;
import indi.liudalei.eidea.core.def.RelOperDef;
import indi.liudalei.eidea.core.def.SearchDataTypeDef;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.CommonSearchParam;
import indi.liudalei.eidea.core.entity.bo.CommonSearchResult;
import indi.liudalei.eidea.core.entity.bo.SearchBo;
import indi.liudalei.eidea.core.entity.bo.SearchColumnBo;
import indi.liudalei.eidea.core.entity.dto.SearchColumnDto;
import indi.liudalei.eidea.core.entity.po.LabelPo;
import indi.liudalei.eidea.core.entity.po.SearchColumnPo;
import indi.liudalei.eidea.core.entity.po.SearchPo;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.service.SearchService;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.util.DateTimeHelper;
import indi.liudalei.eidea.util.StringUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 10:21.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @DataAccess(entity = LabelPo.class)
    private CommonDao<LabelPo, String> labelDao;
    @Autowired
    private SearchColumnDao searchColumnDao;
    private ModelMapper modelMapper = new ModelMapper();
    public SearchServiceImpl()
    {
        modelMapper.addMappings(new PropertyMap<SearchColumnPo, SearchColumnBo>() {
            @Override
            protected void configure() {
                map().setLabelKey(source.getCoreLabel().getKey());
            }
        });
    }
    @Override
    public PaginationResult<SearchBo> findList(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<SearchBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<SearchPo> searchResult = searchDao.searchAndCount(search);
            List<SearchBo> list = modelMapper.map(searchResult.getResult(),new TypeToken<List<SearchBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(list,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        }else{
            List<SearchPo> searchPoList = searchDao.search(search);
            List<SearchBo> searchBoList = modelMapper.map(searchPoList,new TypeToken<List<SearchBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(searchBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }

    public SearchBo getSearchBo(Integer id) {
        SearchPo searchPo = searchDao.find(id);
        SearchBo searchBo = convertPoToBo(searchPo);
        return searchBo;
    }

    private SearchBo convertPoToBo(SearchPo searchPo) {



        SearchBo searchBo = modelMapper.map(searchPo, SearchBo.class);
        List<SearchColumnBo> searchColumnBoList = modelMapper.map(searchPo.getCoreSearchColumns(), new TypeToken<List<SearchColumnBo>>() {
        }.getType());
        searchBo.setSearchColumnBoList(searchColumnBoList);
        return searchBo;
    }

    public SearchBo saveSearchBo(SearchBo searchBo) {
        ModelMapper modelMapper = new ModelMapper();

        List<SearchColumnPo> searchColumnPoList = new ArrayList<>();
        SearchPo searchPo = modelMapper.map(searchBo, SearchPo.class);
        searchBo.getSearchColumnBoList().forEach(e -> {
            StringBuilder sb = new StringBuilder();
            if (e.getRelOperList() != null)
                for (int i = 0; i < e.getRelOperList().size(); i++) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    if (e.getRelOperList().get(i).isChecked()) {
                        sb.append(e.getRelOperList().get(i).getKey());
                    }

                }


            SearchColumnPo s = modelMapper.map(e, SearchColumnPo.class);
            s.setRelOper(sb.toString());
            if (StringUtil.isNotEmpty(e.getLabelKey())) {
                s.setCoreLabel(labelDao.find(e.getLabelKey()));
            }

            s.setCoreSearch(searchPo);
            searchColumnPoList.add(s);
        });
        List<Integer> notNeedRemovedColumnList = new ArrayList<>();
        for (SearchColumnPo searchColumnPo : searchColumnPoList) {
            if (searchColumnPo.getId() != null) {
                notNeedRemovedColumnList.add(searchColumnPo.getId());
            }
        }
        if( searchBo.getId()!=null)
        {
            searchColumnDao.removeSearchColumnIdNotIn(notNeedRemovedColumnList, searchBo.getId());
        }
        searchPo.setCoreSearchColumns(searchColumnPoList);

        searchDao.saveForLog(searchPo);
        searchBo.setId(searchPo.getId());

        return searchBo;
    }

    public SearchBo getSearchBoByUri(String uri) {
        Search search = new Search();
        search.addFilterEqual("uri", uri);
        SearchPo searchPo = searchDao.searchUnique(search);
        if (searchPo != null) {
            SearchBo searchBo = convertPoToBo(searchPo);
            return searchBo;
        }
        return null;
    }

    public void deleteSearches(Integer[] ids) {
        searchDao.removeByIdsForLog(ids);
    }

    public List<CommonSearchResult> getCommonSearchListByColumnId(Integer columnId) {
        SearchColumnPo searchColumn = searchColumnDao.find(columnId);
        CommonSearchParam param = new CommonSearchParam();
        param.setKeyValue(searchColumn.getFkKeyColumn());
        param.setLabelValue(searchColumn.getFkLabelColumn());
        if (StringUtil.isNotEmpty(searchColumn.getCoditions()))
            param.setConditions(searchColumn.getCoditions());
        param.setTableName(searchColumn.getFkTable());
        return searchDao.selectCommonList(param);
    }

    @Override
    public int[] getRelOpersForOperStr(String operStr) {
        int[] ids = new int[0];
        if (StringUtil.isNotEmpty(operStr)) {
            String[] reArray = operStr.split(",");
            ids = new int[reArray.length];
            for (int i = 0; i < reArray.length; i++) {
                ids[i] = Integer.parseInt(reArray[i]);
            }
            return ids;
        }
        return new int[0];
    }

    @Override
    public Search getSearchParam(List<SearchColumnDto> searchColumnDtoList) {
        Search search = new Search();

        for (SearchColumnDto searchColumnDto : searchColumnDtoList) {
            String columnName = searchColumnDto.getColumnName();
            Object value = searchColumnDto.getValue();
            if (searchColumnDto.getDataType() == SearchDataTypeDef.DATE.getKey()) {
                value = DateTimeHelper.parseDate(searchColumnDto.getValue());
            } else if (searchColumnDto.getDataType() == SearchDataTypeDef.DATETIME.getKey()) {
                value = DateTimeHelper.parseDateTime(searchColumnDto.getValue());
            }

            if (RelOperDef.EQUAL.getDesc().equals(searchColumnDto.getOpearType())) {
                if (searchColumnDto.getDataType() == SearchDataTypeDef.DATE.getKey()) {
                    Date date = (Date) value;
                    search.addFilterGreaterOrEqual(columnName, DateTimeHelper.getDayBeginTime(date));
                    search.addFilterLessOrEqual(columnName, DateTimeHelper.getDayEndTime(date));
                } else {
                    search.addFilterEqual(columnName, value);
                }
            } else if (RelOperDef.GREATER_EQ_THAN.getDesc().equals(searchColumnDto.getOpearType())) {
                search.addFilterGreaterOrEqual(columnName, value);
            } else if (RelOperDef.GREATER_EQ_THAN.getDesc().equals(searchColumnDto.getOpearType())) {
                search.addFilterGreaterOrEqual(columnName, value);
            } else if (RelOperDef.LESS_EQ_THAN.getDesc().equals(searchColumnDto.getOpearType())) {
                if (searchColumnDto.getDataType() == SearchDataTypeDef.DATE.getKey()) {
                    search.addFilterLessOrEqual(columnName, DateTimeHelper.getDayEndTime((Date) value));
                } else if (searchColumnDto.getDataType() == SearchDataTypeDef.DATETIME.getKey()) {
                    Date date = (Date) value;
                    date.setTime(date.getTime() + 1000);
                    search.addFilterLessOrEqual(columnName, date);
                } else {
                    search.addFilterLessOrEqual(columnName, value);
                }

            } else if (RelOperDef.LESS_THAN.getDesc().equals(searchColumnDto.getOpearType())) {
                search.addFilterLessThan(columnName, value);

            } else if (RelOperDef.LIKE.getDesc().equals(searchColumnDto.getOpearType())) {
                search.addFilterLike(columnName, "%" + value + "%");
            }
        }
        return search;
    }
}
