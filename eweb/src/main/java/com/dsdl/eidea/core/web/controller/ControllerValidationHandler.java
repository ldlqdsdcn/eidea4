package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.vo.ErrorDTO;
import com.dsdl.eidea.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/13 17:27.
 */
@ControllerAdvice
public class ControllerValidationHandler {
    @Autowired
    private HttpSession session;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult<List<ErrorDTO>> processValidationError(MethodArgumentNotValidException ex) {
        UserResource userResource=(UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrorList = result.getFieldErrors();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        fieldErrorList.forEach(e -> {
            errorDTOList.add(processFieldError(e,userResource));
        });

        return ApiResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), errorDTOList);
    }

    private ErrorDTO processFieldError(FieldError error,UserResource userResource) {
        if (error != null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setFieldName(error.getField());
            error.getCode();
            String errorMsg=userResource.getMessage(error.getDefaultMessage());
            if(StringUtil.isEmpty(errorMsg))
            {
                errorMsg=error.getDefaultMessage();
            }
            errorDTO.setMessage(errorMsg);
            return errorDTO;
        }
        return null;
    }
}
