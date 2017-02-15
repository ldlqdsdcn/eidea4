package com.dsdl.eidea.base.service;

import java.util.List;

import com.dsdl.eidea.base.entity.bo.ChangelogBo;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.googlecode.genericdao.search.ISearch;

public interface ChangelogService {
	List<ChangelogBo> getChangelogList(ISearch search);
	ChangelogBo getChangelogBo(Integer id);
	List<TableColumnBo> getChangelogHeader(String tableName);
}
