package com.dsdl.eidea.base.service;

import java.util.List;

import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.googlecode.genericdao.search.Search;

public interface UserSessionService {

	List<UserSessionBo> getUserSessionList(Search search);

	void saveLoginOutDate(String sessionId);

}
