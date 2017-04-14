package com.dsdl.setting;

import com.dsdl.eidea.util.ReadPropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 刘大磊 on 2017/4/14 15:36.
 */
@Slf4j
public class RmiSettingHelper {
    private static Properties config = null;

    static {
        InputStream in = ReadPropertiesUtil.class.getClassLoader().getResourceAsStream("conf/comm.properties");
        config = new Properties();
        try {
            config.load(in);
            in.close();
        } catch (IOException e) {
            log.error("No AreaPhone.properties defined error", e);
        }
    }
   public static String getMainRmiUrl()
   {
      return config.getProperty("web.url");
   }
    public static String getApiRmiUrl()
    {
        return config.getProperty("api.url");
    }
    public static String getReportRmiUrl()
    {
        return config.getProperty("report.url");
    }
}
