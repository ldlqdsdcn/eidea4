package com.dsdl.eidea.soap.dao.impl;

import com.dsdl.eidea.soap.dao.WslogDao;
import com.dsdl.eidea.soap.model.Wslog;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by 刘大磊 on 2016/9/8.
 */
@Slf4j
public class WslogDaoForLog implements WslogDao {

    @Override
    public void insertWslog(Wslog wslog) {
      log.info(wslog.toString());
    }
}
