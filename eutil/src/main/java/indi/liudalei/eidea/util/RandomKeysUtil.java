package indi.liudalei.eidea.util;

import java.util.Random;

/**
 * @author liudalei
 * @version 1.0.0
 * @date 2017/12/27
 * @description
 */
public class RandomKeysUtil {
    private static String base = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String getRandomString(int length) { //length表示生成字符串的长度

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
