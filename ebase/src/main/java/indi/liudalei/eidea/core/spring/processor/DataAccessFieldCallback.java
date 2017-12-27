package indi.liudalei.eidea.core.spring.processor;

import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dao.hibernate.CommonDaoHibernate;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
        DataAccess dataAccess=field.getDeclaredAnnotation(DataAccess.class);
        Class<?> classValue = dataAccess.entity();
        Class<?> instanceDaoClass=dataAccess.instanceDaoClass();
        if (genericTypeIsValid(classValue, fieldGenericType,field.getName())) {
            String beanName = classValue.getSimpleName() + generic.getSimpleName();
            logger.debug("init beanName="+beanName);
            Object beanInstance = getBeanInstance(beanName, generic, classValue,field.getName(),instanceDaoClass);
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
            String beanName, Class<?> genericClass, Class<?> paramClass,String fieldName,Class<?> instanceDaoClass) {
        if(!genericClass.equals(CommonDao.class))
        {
            throw new RuntimeException("@DataAccess注入的字段，必须为："+CommonDao.class.getName()+"类型,类："+bean.getClass().getName()+"属性:"+fieldName);
        }
        Object daoInstance = null;
        if (!configurableBeanFactory.containsBean(beanName)) {
            logger.info("Creating new DataAccess bean named '{}'.", beanName);

            Object toRegister = null;
            if(instanceDaoClass==null)
            {
                toRegister=new CommonDaoHibernate(paramClass);
            }
            else
            {
                try {
                    Constructor<?> ctr = instanceDaoClass.getConstructor(Class.class);
                    toRegister = ctr.newInstance(paramClass);
                } catch (Exception e) {
                    logger.error(ERROR_CREATE_INSTANCE, genericClass.getTypeName(), e);
                    throw new RuntimeException(e);
                }
            }

            daoInstance = configurableBeanFactory.initializeBean(toRegister, beanName);
            configurableBeanFactory.autowireBeanProperties(daoInstance, AUTOWIRE_MODE, true);
            configurableBeanFactory.registerSingleton(beanName, daoInstance);
            logger.info("Bean named '{}' created successfully.", beanName);
        } else {
            daoInstance = configurableBeanFactory.getBean(beanName);
            logger.info(
                    "Bean named '{}' already exists used as current bean reference.", beanName);
        }
        return daoInstance;
    }
}
