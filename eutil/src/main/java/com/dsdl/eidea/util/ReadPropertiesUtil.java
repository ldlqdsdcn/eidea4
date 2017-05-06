package com.dsdl.eidea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 类描述：读取配置文件
 *
 * @version 1.0
 * @author: lq
 * @date： 日期：2016年5月25日 时间：下午1:10:18
 */
public class ReadPropertiesUtil {
    private final static Logger logger = LoggerFactory.getLogger(ReadPropertiesUtil.class);
    private static Properties config = null;

    static {
        InputStream in = ReadPropertiesUtil.class.getClassLoader().getResourceAsStream("conf/aes.properties");
        config = new Properties();
        try {
            config.load(in);
            in.close();
        } catch (IOException e) {
            logger.info("No AreaPhone.properties defined error");
        }
    }

    // 根据key读取value
    public static String readValue(String key) {
        // Properties props = new Properties();
        try {
            String value = config.getProperty(key);
            logger.info(key + "=" + value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("ConfigInfoError" + e.toString());
            return null;
        }
    }

    public static void main(String args[]) {
        ReadPropertiesUtil.readValue("aeskey");
        String aeskey = ReadPropertiesUtil.readValue("aeskey");
        System.out.println("---------------------aeskey:" + aeskey);
        String a = "zhangsan";
        String b = "yfe4TvE8qHwB0zns";
        System.out.println("原始内容===" + a);
        System.out.println("加密后===" + AesUtils.encrypt(a, b));
        System.out.println("解密后===" + AesUtils.decrypt(AesUtils.encrypt(a, b), b));
    }
}
