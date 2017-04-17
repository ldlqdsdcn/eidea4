package com.dsdl.eidea.core.dao;

import com.dsdl.eidea.core.entity.po.ReportPo;
import com.dsdl.eidea.core.entity.union.MsgUnion;

import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
public interface ReportDao extends BaseDao<ReportPo,String> {
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
