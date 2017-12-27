package indi.liudalei.eidea.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liudalei
 * @version 1.0.0
 * @date 2017/12/27
 * @description
 */
public class RandomKeysUtilTest {
    @Test
    public void testGeneratePassword()
    {
        String keys=RandomKeysUtil.getRandomString(128);
        System.out.println(keys);
        Assert.assertTrue(keys.length()==128);
    }
}
