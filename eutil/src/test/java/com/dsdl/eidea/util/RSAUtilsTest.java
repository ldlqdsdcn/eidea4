package com.dsdl.eidea.util;


import org.junit.Test;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/2/19.
 */
public class RSAUtilsTest {
    @Test
    public void testGenerateKey()throws Exception
    {
       Map<String, Object> keys= RSAUtils.getKeys();
        RSAPublicKey rsaPublicKey=(RSAPublicKey)keys.get("public");
        System.out.println(rsaPublicKey.getAlgorithm());
        System.out.println(rsaPublicKey.toString());
    }
}
