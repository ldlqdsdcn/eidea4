package com.dsdl.core.spring.processor;

import com.dsdl.core.spring.annotation.DataAccess;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.aop.PersistentClassInjection;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;

/**
 * Created by 刘大磊 on 2017/2/15 17:12.
 */
public class DataAccessFieldCallback implements ReflectionUtils.FieldCallback {
    private static Logger logger = LoggerFactory.getLogger(DataAccessFieldCallback.class);

    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;

    private static String ERROR_ENTITY_VALUE_NOT_SAME = "@DataAccess(entity) "
            + "value should have same type with injected generic type.";
    private static String WARN_NON_GENERIC_VALUE = "@DataAccess annotation assigned "
            + "to raw (non-generic) declaration. This will make your code less type-safe.";
    private static String ERROR_CREATE_INSTANCE = "Cannot create instance of "
            + "type '{}' or instance creation is failed because: {}";

    private ConfigurableListableBeanFactory configurableBeanFactory;
    private Object bean;

    public DataAccessFieldCallback(ConfigurableListableBeanFactory bf, Object bean) {
        configurableBeanFactory = bf;
        this.bean = bean;
    }

    @Override
    public void doWith(Field field)
            throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(DataAccess.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        Type fieldGenericType = field.getGenericType();
        Class<?> generic = field.getType();
        Class<?> classValue = field.getDeclaredAnnotation(DataAccess.class).entity();
        if (genericTypeIsValid(classValue, fieldGenericType,field.getName())) {
            String beanName = classValue.getSimpleName() + generic.getSimpleName();
            Object beanInstance = getBeanInstance(beanName, generic, classValue);
            field.set(bean, beanInstance);
            } else {
            throw new IllegalArgumentException(ERROR_ENTITY_VALUE_NOT_SAME);
        }
    }

    public boolean genericTypeIsValid(Class<?> clazz, Type field,String fieldName) {
        if (field instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field;
            Type type = parameterizedType.getActualTypeArguments()[0];

            return type.equals(clazz);
        } else {
            logger.warn(WARN_NON_GENERIC_VALUE);
            throw new RuntimeException("@DataAccess 指定的属性没有设置泛型类 类："+bean.getClass().getName()+"属性:"+fieldName);
        }
    }

    public Object getBeanInstance(
            String beanName, Class<?> genericClass, Class<?> paramClass) {
        PersistentClassInjection persistentClassInjection =configurableBeanFactory.getBean(PersistentClassInjection.class);
        persistentClassInjection.setPersistentClass(paramClass);
        return persistentClassInjection;
    }
}
