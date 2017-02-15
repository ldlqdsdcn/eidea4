package com.dsdl.eidea.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/18 12:52.
 */
public class Md5SaltTest {
    private static Map users = new HashMap();

    public static void main(String[] args)throws Exception{
        String userName = "zyg";
        String password = "123";
        registerUser(userName,password);

        userName = "changong";
        password = "456";
        registerUser(userName,password);

        String loginUserId = "changong";
        String pwd = "456";
        if(loginValid(loginUserId,pwd)){
            System.out.println("欢迎登陆！！！");
        }else{
            System.out.println("口令错误，请重新输入！！！");
        }
    }

    /**
     * 注册用户
     *
     * @param userName
     * @param password
     */
    public static void registerUser(String userName,String password){
            String encryptedPwd = null;
            encryptedPwd = Md5SaltTool.getEncryptedPwd(password);
            System.out.println("encryptedPwd="+encryptedPwd);
            users.put(userName, encryptedPwd);


    }

    /**
     * 验证登陆
     *
     * @param userName
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static boolean loginValid(String userName,String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
             /*String loginUserId = "zyg";
               String pwd = "1232";*/
        String pwdInDb = (String)users.get(userName);
        if(null!=pwdInDb){ // 该用户存在
            return Md5SaltTool.validPassword(password, pwdInDb);
        }else{
            System.out.println("不存在该用户！！！");
            return false;
        }
    }
}
