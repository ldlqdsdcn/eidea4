package com.dsdl.eidea.core.web.interceptor;

import com.dsdl.eidea.core.web.annotation.ParamValid;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.util.ValidatorHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by 刘大磊 on 2017/9/15 17:42.
 */
@Aspect
@Component
public class ParameterValidatorInterceptor {
    ValidatorHelper validatorHelper = ValidatorHelper.getInstance();

    @Around("execution(* *..controller.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object controllerMethodPointcut(ProceedingJoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] methodAnnotations = method.getParameterAnnotations();
        if (methodAnnotations != null) {
            for (int i = 0; i < methodAnnotations.length; i++) {
                Annotation[] annotations = methodAnnotations[i];
                for (Annotation annotation : annotations) {
                    if (annotation instanceof ParamValid) {
                        Object obj = joinPoint.getArgs()[i];
                        if (obj != null) {
                            String errorMsg = validatorHelper.validatorBackHtmlString(obj);
                            if (errorMsg != null) {
                                stringBuilder.append(errorMsg);
                            }
                        }
                    }
                }
            }
        }
        if (stringBuilder.length() > 0) {
            if (method.getReturnType().equals(ModelAndView.class)) {
                ModelAndView modelAndView = new ModelAndView("error/validator_errors");
                modelAndView.addObject("errors", stringBuilder.toString());
                return modelAndView;
            }
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), stringBuilder.toString());
        }
        try {
            Object result = joinPoint.proceed(joinPoint.getArgs());
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
