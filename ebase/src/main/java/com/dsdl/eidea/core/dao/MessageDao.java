package com.dsdl.eidea.core.dao;

import com.dsdl.eidea.core.entity.po.MessagePo;
import com.dsdl.eidea.core.entity.union.MsgUnion;

import java.util.List;


/**
 * Created by admin on 2016/12/16.
 */
public interface MessageDao extends BaseDao<MessagePo, String> {
    /*
    * 获取国际化label
    * @param lang 国际码
     */
    List<MsgUnion> selectLabelTrl(String lang);

    /**
     * 获取国际化消息
     * @param lang 国际码
     * @return
     */
    List<MsgUnion> selectMessageTrl(String lang);
}
