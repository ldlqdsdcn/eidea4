package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.po.StudentPo;
import com.dsdl.eidea.base.service.StudentService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import java.util.List;

/**
 * Created by 车东明 on 2017/5/25.
 */
public class StudentServiceImpl implements StudentService {
    @DataAccess(entity =StudentPo.class)
    private CommonDao<StudentPo,Integer> studentDao;


    @Override
    public PaginationResult<StudentPo> getStudentListByPaging(Search search, QueryParams queryParams)
    {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<StudentPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<StudentPo> searchResult = studentDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        else
        {
            List<StudentPo> studentPoList = studentDao.search(search);
            paginationResult = PaginationResult.pagination(studentPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }
    public StudentPo getStudent(Integer id)
    {
        return studentDao.find(id);
    }
    public void saveStudent(StudentPo tab)
    {
        studentDao.save(tab);
    }
    public void deletes(Integer[] ids)
    {
        studentDao.removeByIds(ids);
    }

}
