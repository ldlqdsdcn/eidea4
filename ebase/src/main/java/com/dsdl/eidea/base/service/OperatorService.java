package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.OperatorBo;
import com.dsdl.eidea.base.entity.po.OperatorPo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by admin on 2016/12/20.
 */
public interface OperatorService {

    List<OperatorBo> findOperator(Search search);
    //根据id 删除菜单
    void deleteOperatorById(Integer[] ids);
    void deleteOperatorByObject(OperatorBo operatorBo);
    OperatorBo findOperatorByid(Integer id);
    //保存
    void save(OperatorBo operatorBo);

    //判断url是否存在
    boolean findExistId(Integer id);
    OperatorBo getOperatorBo(Integer id);
}
