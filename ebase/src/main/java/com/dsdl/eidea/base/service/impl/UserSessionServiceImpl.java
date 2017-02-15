package com.dsdl.eidea.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsdl.eidea.base.dao.UserSessionDao;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.entity.po.UserSessionPo;
import com.dsdl.eidea.base.service.UserSessionService;
import com.googlecode.genericdao.search.Search;

@Service
public class UserSessionServiceImpl implements UserSessionService {
    @Autowired
    private UserSessionDao userSessionDao;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserSessionBo> getUserSessionList(Search search) {
        List<UserSessionBo> userSessionBoList = new ArrayList<UserSessionBo>();
        List<UserSessionPo> userSessionPoList = userSessionDao.search(search);
        for (UserSessionPo up : userSessionPoList) {
            UserSessionBo u = modelMapper.map(up, UserSessionBo.class);
            u.setUsername(up.getUserPo().getUsername());
            userSessionBoList.add(u);
        }
        return userSessionBoList;

    }

    @Override
    public void saveLoginOutDate(String sessionId) {
        Search search = new Search();
        search.addFilterEqual("sessionId", sessionId);
        UserSessionPo userSessionPo = userSessionDao.searchUnique(search);
        if(userSessionPo!=null)
        {
            userSessionPo.setLogoutDate(new Date());
            userSessionDao.save(userSessionPo);
        }

    }

}
