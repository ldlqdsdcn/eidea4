package indi.liudalei.eidea.base.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by 刘大磊 on 2017/6/21.
 */
@Slf4j
public class SimpleMockTest {
    @Test
    public void testExample()
    {
        List<String> list = mock(List.class);
        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        verify(list).get(0);

        //junit测试
        Assert.assertEquals("helloworld", result);
    }
    @Test
    public void argumentMatcherTest()
    {
        List<String> list = mock(List.class);

        when(list.get(anyInt())).thenReturn("hello","world","apple","orange");

        String result = list.get(0)+list.get(0);
        String result2=list.get(0)+list.get(4);
        verify(list,times(4)).get(anyInt());

        Assert.assertEquals("helloworld", result);
        log.warn(result);
        log.warn("result2="+result2);
        log.warn(list.get(3));
        log.warn(list.get(7)+list.get(8));
        log.warn(list.get(8)+list.get(9));
    }
}
