package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.ChangelogBo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.TableColumnBo;
import indi.liudalei.eidea.core.params.QueryParams;
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
