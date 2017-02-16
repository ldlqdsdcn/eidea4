package com.dsdl.eidea.core.spring.annotation;

import com.dsdl.eidea.core.dao.hibernate.CommonDaoHibernate;

import java.lang.annotation.*;

/**
 * Created by 刘大磊 on 2017/2/15 17:08.
 * 数据库持久成注解
 * 该类只能注入 com.dsdl.eidea.core.dao.CommonDao接口
 * 通过改注解，生命并创建Dao spring bean对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Documented
public @interface DataAccess {
    Class<?> entity();
    /**
     * dao实现类
     * @return
     */
    Class<?> instanceDaoClass() default CommonDaoHibernate.class;
}