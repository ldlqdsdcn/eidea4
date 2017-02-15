package com.dsdl.eidea.base.service.impl;

import com.dsdl.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.entity.po.UserSessionPo;
import com.dsdl.eidea.base.service.UserSessionService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserSessionServiceImpl implements UserSessionService {
    @DataAccess(entity = UserSessionPo.class)
    private CommonDao<UserSessionPo,Integer> userSessionDao;
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
