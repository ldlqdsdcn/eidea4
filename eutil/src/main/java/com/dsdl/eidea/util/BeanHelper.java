package com.dsdl.eidea.util;

import java.lang.reflect.Field;

/**
 * Created by 刘大磊 on 2017/7/7 14:21.
 * 反射赋值工具类
 */
public class BeanHelper {
    /**
     * 可以直接通过反射赋值给bean对象的字段，该字段不用实现get set方法
     * @param model
     * @param filedName
     * @param value
     */
    public static void setProperty(Object model, String filedName, Object value) {
        Field testAField = null;
        try {
            testAField = model.getClass().getDeclaredField(filedName);
            testAField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("找不到对应的字段", e);
        }
        try {
            testAField.set(model, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("字段赋值出错", e);
        }
    }
}
