package indi.liudalei.eidea.base.service;

import java.util.List;

import indi.liudalei.eidea.base.entity.bo.UserSessionBo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
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
