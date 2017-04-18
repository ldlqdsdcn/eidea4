package com.dsdl.eidea.core.test;

import java.text.MessageFormat;

/**
 * Created by 刘大磊 on 2017/4/18 16:21.
 */
public class StringFormat {
    public static void main(String[] args)
    {
        String param="{0}你好啊,欢迎来到{1}，今天清明";
        String out=String.format(param,"刘大磊","青岛");

        System.out.println(out);
        System.out.println(MessageFormat.format(param,"刘大磊","青岛"));
    }
}
