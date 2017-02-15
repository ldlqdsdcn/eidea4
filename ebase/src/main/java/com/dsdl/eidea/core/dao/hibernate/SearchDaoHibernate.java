package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.SearchDao;
import com.dsdl.eidea.core.entity.bo.CommonSearchParam;
import com.dsdl.eidea.core.entity.bo.CommonSearchResult;
import com.dsdl.eidea.core.entity.po.SearchPo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 10:25.
 */
@Repository
public class SearchDaoHibernate  extends BaseDaoHibernate<SearchPo, Integer>  implements SearchDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Override
    public List<CommonSearchResult> selectCommonList(CommonSearchParam commonSearchParam) {
        return sqlSessionTemplate.selectList("com.delmar.core.mybatis.sql.SearchMapper.selectCommonList",commonSearchParam);
    }

}
