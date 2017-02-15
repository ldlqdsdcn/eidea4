package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.MessageDao;
import com.dsdl.eidea.core.entity.po.MessagePo;
import com.dsdl.eidea.core.entity.union.MsgUnion;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/8 16:03.
 */
@Repository
public class MessageDaoHibernate extends BaseDaoHibernate<MessagePo, String> implements MessageDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<MsgUnion> selectLabelTrl(String lang) {
        return sqlSessionTemplate.selectList("com.delmar.core.mybatis.sql.MessageAndLabelUnionMapper.selectLabelTrl", lang);
    }

    @Override
    public List<MsgUnion> selectMessageTrl(String lang) {
        return sqlSessionTemplate.selectList("com.delmar.core.mybatis.sql.MessageAndLabelUnionMapper.selectMessageTrl", lang);
    }
}
