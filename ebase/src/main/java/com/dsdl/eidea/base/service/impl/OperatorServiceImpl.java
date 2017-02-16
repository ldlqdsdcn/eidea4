package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.OperatorBo;
import com.dsdl.eidea.base.entity.po.OperatorPo;
import com.dsdl.eidea.base.service.OperatorService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/12/20.
 */
@Service
public class OperatorServiceImpl implements OperatorService {
@DataAccess(entity = OperatorPo.class)
private CommonDao<OperatorPo,Integer> operatorDao;
private ModelMapper modelMapper = new ModelMapper();
    @Override
    public List<OperatorBo> findOperator(Search search) {
        List<OperatorPo>   operatorPoList=operatorDao.search(search );
        List<OperatorBo> operatorBoList = modelMapper.map(operatorPoList, new TypeToken<List<OperatorBo>>() {
        }.getType());
        return operatorBoList;
    }

    @Override
    public void deleteOperatorById(Integer[] ids) {
        operatorDao.removeByIds(ids);
    }

    @Override
    public void deleteOperatorByObject(OperatorBo operatorBo) {
        OperatorPo operatorPo = modelMapper.map(operatorBo, new TypeToken<OperatorPo>() {
        }.getType());
        operatorDao.remove(operatorPo);
    }

    @Override
    public OperatorBo findOperatorByid(Integer id) {
      OperatorPo operatorPo=  operatorDao.find(id);
        OperatorBo operatorBo = modelMapper.map(operatorPo, new TypeToken() {
        }.getType());
        return operatorBo;
    }

    @Override
    public void save(OperatorBo operatorBo) {
        OperatorPo operatorPo = modelMapper.map(operatorBo, OperatorPo.class);
        operatorDao.save(operatorPo);
        operatorBo.setId(operatorPo.getId());

    }

    @Override
    public boolean findExistId(Integer id) {
        return false;
    }


    @Override
    public OperatorBo getOperatorBo(Integer id) {
        OperatorPo operatorPo = operatorDao.find(id);
        OperatorBo operatorBo = modelMapper.map(operatorPo, OperatorBo.class);
        return operatorBo;
    }
}
