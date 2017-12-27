package indi.liudalei.eidea.base.web.annotation;

import indi.liudalei.eidea.base.def.OperatorDef;
import indi.liudalei.eidea.base.web.def.ReturnType;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * Created by 刘大磊 on 2016/12/26 16:11.
 * 权限控制注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface PrivilegesControl {
    /**
     * 操作控制权限
     *
     * @return
     */
    OperatorDef[] operator() default OperatorDef.VIEW;
    ReturnType returnType() default ReturnType.JSON;
}
