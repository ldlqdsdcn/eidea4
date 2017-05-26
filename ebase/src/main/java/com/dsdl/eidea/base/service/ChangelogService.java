package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.ChangelogBo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

public interface ChangelogService {
	PaginationResult<ChangelogBo> getChangelogList(Search search, QueryParams queryParams);
	ChangelogBo getChangelogBo(Integer id);
	List<TableColumnBo> getChangelogHeader(String tableName);

	/**
	 * getChangeLogList:查询log列表
	 * @return
	 */
	List<ChangelogBo> getChangeLogModelList(Search search);
}
