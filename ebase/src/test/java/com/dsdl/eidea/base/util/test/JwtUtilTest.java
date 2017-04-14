package com.dsdl.eidea.base.util.test;

import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.util.JwtUtil;
import org.junit.Test;

import java.net.URLDecoder;

/**
 * Created by 刘大磊 on 2017/1/24 10:47.
 */
public class JwtUtilTest {
    @Test
    public void testGeneratorToken()throws Exception
    {
        UserBo userBo=new UserBo();
        userBo.setName("刘大磊");
        userBo.setUsername("administrator");
        String token= JwtUtil.getTokenString(userBo);
        System.out.println(token);
        System.out.println(token.length());
        URLDecoder decoder=new URLDecoder();
        String decodeToken=URLDecoder.decode(token,"UTF-8");
        System.out.println(decodeToken);
    }
}
