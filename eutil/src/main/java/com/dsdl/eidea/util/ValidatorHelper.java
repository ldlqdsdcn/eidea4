package com.dsdl.eidea.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2017/9/15 17:45.
 */
public class ValidatorHelper {
    private static final ValidatorHelper VALIDATORHELPER = new ValidatorHelper();
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private ValidatorHelper() {

    }

    public static ValidatorHelper getInstance() {
        return VALIDATORHELPER;
    }

    /**
     * 验证，如果有错误消息返回 错误消息，没有返回null
     *
     * @param param
     * @return
     */
    public List<String> validator(Object param) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(param);
        if (constraintViolations.size() > 0)
            return constraintViolations.stream().map(e -> e.getMessage()).collect(Collectors.toList());
        return null;
    }

    public String validatorBackHtmlString(Object param) {
        List<String> errorMessageList = validator(param);
        if (errorMessageList == null) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String errorMsg : errorMessageList) {
                stringBuilder.append(errorMsg).append("<br>");
            }
            return stringBuilder.toString();

        }
    }

}
