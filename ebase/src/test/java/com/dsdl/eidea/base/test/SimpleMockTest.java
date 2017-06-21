package com.dsdl.eidea.base.test;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by cityre on 2017/6/21.
 */
public class SimpleMockTest {
    @Test
    public void testExample()
    {
        List<String> list = mock(List.class);
        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        Mockito.verify(list).get(0);

        //junit测试
        Assert.assertEquals("helloworld", result);
    }
}
