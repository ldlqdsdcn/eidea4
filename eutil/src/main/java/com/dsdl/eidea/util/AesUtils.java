package com.dsdl.eidea.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author xinxin
 * @since 7/6/2016
 */
public class AesUtils {
    public static String encrypt(String content,String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            Key secretKey = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("Aes encrypt failed," + e.getMessage(), e);
        }
    }

    public static String decrypt(String encryptStr,String password) {
        try {
            byte[] e = Base64.decodeBase64(encryptStr);

            Cipher cipher = Cipher.getInstance("AES");
            Key secretKey = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(e), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Aes decrypt failed," + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
    //System.out.println(decrypt("+KosuD0pd9U6/P8kiHT+Xw==", "yfe4TvE8qHwB0zns"));
    //System.out.println(decrypt("+KosuD0pd9U6/P8kiHT+Xw==", "yfe4TvE8qHwB0zns"));
    //git test
        String a="zhangsan";
        String b="yfe4TvE8qHwB0zns";
        System.out.println("原始内容==="+a);
        System.out.println("加密后==="+AesUtils.encrypt(a,b));
        System.out.println("解密后==="+AesUtils.decrypt(AesUtils.encrypt(a,b),b));
    }
}
