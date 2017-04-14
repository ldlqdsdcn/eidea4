package com.dsdl.eidea.report.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 刘大磊 on 2016/9/30 10:20.
 */
public class DateUtil {
    private static final DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getCurrentTimeForYyyyMMddHHmmssFormat()
    {
        return yyyyMMddHHmmss.format(new Date());
    }
    public static String getCurrentDaqteYyyyMMdd()
    {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

}
