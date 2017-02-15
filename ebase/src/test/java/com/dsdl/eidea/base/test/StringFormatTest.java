package com.dsdl.eidea.base.test;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * Created by 刘大磊 on 2017/1/13 10:57.
 */
public class StringFormatTest {
    @Test
    public void testStringFormat()
    {
        //ResourceBundle resourceBundle=ResourceBundle.getBundle("");

       String value= MessageFormat.format("获取{0}失败","实体信息");
        System.out.println(value);
    }
}
