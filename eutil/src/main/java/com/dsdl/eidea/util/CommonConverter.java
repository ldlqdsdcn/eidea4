package com.dsdl.eidea.util;

import org.apache.commons.beanutils.BeanUtils;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 刘大磊 2014年12月22日 上午10:59:16
 */
public class CommonConverter {
    public static <T> T convertObjectExclude(Object from, Class<T> classOfT, String[] excludsArray) {
        List<String> excludesList = null;
        if (excludsArray != null && excludsArray.length > 0) {
            excludesList = Arrays.asList(excludsArray); //构造列表对象
        }
        T toObject = null;
        try {
            toObject = classOfT.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("转换对象-实例化目标对象失败", e);
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = toObject.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get"))
                continue;
            //排除列表检测
            if (excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null)
                continue;
            Object value = null;
            try {
                value = fromMethod.invoke(from, new Object[0]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (value == null)
                continue;
            //集合类判空处理
            if (value instanceof Collection) {
                Collection newValue = (Collection) value;
                if (newValue.size() <= 0)
                    continue;
            }
            try {
                toMethod.invoke(toObject, new Object[]{value});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return toObject;
    }

    /**
     * @param fromList 拷贝的list
     * @param classOfT 转换的 对象类型
     * @return
     */
    public static List convertList(List fromList, Class classOfT) {
        ModelMapper modelMapper = new ModelMapper();
        List toList = new ArrayList();
        for (Object obj : fromList) {
            Object toObject = modelMapper.map(obj, classOfT);
            toList.add(toObject);
        }
        return toList;
    }

    /**
     * 对象转换，把对象相同名字的属性复制给新对象
     *
     * @param obj      要转换的对象
     * @param classOfT 转换后的类
     * @return 转换后类的实例
     */
    public static <T> T convertObject(Object obj, Class<T> classOfT) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(obj, classOfT);
    }

    /**
     * 把对象转换为Map
     *
     * @param obj 要转换的对象
     * @return 返回HashMap实例
     */
    public static Map convertObjectToMap(Object obj) {
        Map<String, Object> result = new HashMap<>();
        try {
            BeanUtils.copyProperties(result, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从方法数组中获取指定名称的方法
     *
     * @param methods
     * @param name
     * @return
     */
    public static Method findMethodByName(Method[] methods, String name) {
        for (int j = 0; j < methods.length; j++) {
            if (methods[j].getName().equals(name))
                return methods[j];
        }
        return null;
    }
}
