package com.dsdl.core.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * Created by 刘大磊 on 2017/2/15 17:11.
 */
@Component
public class DataAccessAnnotationProcessor implements BeanPostProcessor {
    private ConfigurableListableBeanFactory configurableBeanFactory;

    @Autowired
    public DataAccessAnnotationProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        this.scanDataAccessAnnotation(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    protected void scanDataAccessAnnotation(Object bean) {
        this.configureFieldInjection(bean);
    }


    private void configureFieldInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fieldCallback =
                new DataAccessFieldCallback(configurableBeanFactory, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);

    }
}
