package com.dsdl.eidea.core.dao.util;

import com.dsdl.eidea.core.entity.bo.TableBo;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/12/22 13:29.
 */
public class ChangelogHelper {
    private static final Logger logger= Logger.getLogger(ChangelogHelper.class);
    private static final Map<String, TableBo> LOG_MAP = new HashMap<>();
    public static void init(List<TableBo> tableBoList)
    {
        logger.debug("--------------------->操作日志初始化");
        for (TableBo tableBo : tableBoList) {

            ChangelogHelper.put(tableBo.getPoClass(), tableBo);
        }
    }
    public static void put(String poClassName, TableBo tableBo) {
        LOG_MAP.put(poClassName, tableBo);
    }

    public static TableBo get(String poClassName) {
        return LOG_MAP.get(poClassName);
    }
}
