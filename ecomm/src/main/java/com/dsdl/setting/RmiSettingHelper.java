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

    /**
     * 获取主服务rmi端口
     *
     * @return
     */
    public static int getMainPort() {
        return Integer.parseInt(config.getProperty("web.port"));
    }

    /**
     * 获取主rmi url地址
     *
     * @return
     */
    public static String getMainRmiUrl() {
        return config.getProperty("web.base.url") + ":" + config.getProperty("web.port") + "/";
    }

    /**
     * 获取Api rmi url地址
     *
     * @return
     */
    public static String getApiRmiUrl() {
        return config.getProperty("api.base.url") + ":" + config.getProperty("api.port") + "/";
    }

    /**
     * 获取api rmi端口
     *
     * @return
     */
    public static int getApiPort() {
        return Integer.parseInt(config.getProperty("api.port"));
    }

}
