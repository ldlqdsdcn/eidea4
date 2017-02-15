package com.dsdl.eidea.core.service;

import java.util.List;

import com.dsdl.eidea.core.entity.bo.LabelBo;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.googlecode.genericdao.search.ISearch;

public interface LabelService {
	  void save(LabelBo labelBo);
	  boolean findExistClient(String no);
	List<LabelBo> getLabelList(ISearch search);
	void deletes(String[] codes);
	LabelBo getLabelBo(String key);
	List<LabelBo> findLanguageListForActivated(String[] notInCodes);
	  
}
