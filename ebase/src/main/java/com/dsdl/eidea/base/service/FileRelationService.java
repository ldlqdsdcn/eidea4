package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.FileRelationBo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by admin on 2017/5/17.
 */
public interface FileRelationService {
    List<FileRelationBo> getFileRelationList(Search search);
}
