package com.dsdl.eidea.base.service;

import java.util.List;

import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

public interface UserSessionService {

	List<UserSessionBo> getUserSessionList(Search search);

	void saveLoginOutDate(String sessionId);

	/**
	 *
	 * @param search 查询条件
	 * @param queryParam 分页条件
	 * @return
	 */
	PaginationResult<UserSessionBo> getUserSessionPagingList(Search search, QueryParams queryParam);
}
