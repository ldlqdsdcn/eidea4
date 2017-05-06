package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.entity.po.UserSessionPo;
import com.dsdl.eidea.base.service.UserSessionService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserSessionServiceImpl implements UserSessionService {
    @DataAccess(entity = UserSessionPo.class)
    private CommonDao<UserSessionPo, Integer> userSessionDao;
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

    public PaginationResult<UserSessionBo> getUserSessionPagingList(Search search, QueryParams queryParam) {
        List<UserSessionBo> userSessionBoList = new ArrayList<>();
        search.setFirstResult(queryParam.getFirstResult());
        search.setMaxResults(queryParam.getPageSize());
        PaginationResult<UserSessionBo> paginationResult = null;
        if (queryParam.isInit()) {
            SearchResult<UserSessionPo> searchResult = userSessionDao.searchAndCount(search);
            for (UserSessionPo up : searchResult.getResult()) {
                UserSessionBo u = modelMapper.map(up, UserSessionBo.class);
                u.setUsername(up.getUserPo().getUsername());
                userSessionBoList.add(u);
            }
            paginationResult = PaginationResult.pagination(userSessionBoList, searchResult.getTotalCount(), queryParam.getPageNo(), queryParam.getPageSize());
        } else {
            List<UserSessionPo> userSessionPoList = userSessionDao.search(search);
            for (UserSessionPo up : userSessionPoList) {
                UserSessionBo u = modelMapper.map(up, UserSessionBo.class);
                u.setUsername(up.getUserPo().getUsername());
                userSessionBoList.add(u);
            }
            paginationResult = PaginationResult.pagination(userSessionBoList, queryParam.getTotalRecords(), queryParam.getPageNo(), queryParam.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public void saveLoginOutDate(String sessionId) {
        Search search = new Search();
        search.addFilterEqual("sessionId", sessionId);
        UserSessionPo userSessionPo = userSessionDao.searchUnique(search);
        if (userSessionPo != null) {
            userSessionPo.setLogoutDate(new Date());
            userSessionDao.save(userSessionPo);
        }

    }

}
