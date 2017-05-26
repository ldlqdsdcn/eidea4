package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.po.StudentPo;
import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * Created by 车东明 on 2017/5/25.
 */
public interface StudentService {
    PaginationResult<StudentPo> getStudentListByPaging(Search search, QueryParams queryParams);
    StudentPo getStudent(Integer id);
    void saveStudent(StudentPo studentPo);
    void deletes(Integer[] ids);
}
