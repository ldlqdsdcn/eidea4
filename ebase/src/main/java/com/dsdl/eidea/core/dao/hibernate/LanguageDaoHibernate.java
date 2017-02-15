package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.LanguageDao;
import com.dsdl.eidea.core.entity.po.LanguagePo;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/8 16:03.
 */
@Repository
public class LanguageDaoHibernate extends BaseDaoHibernate<LanguagePo, String> implements LanguageDao {

}
