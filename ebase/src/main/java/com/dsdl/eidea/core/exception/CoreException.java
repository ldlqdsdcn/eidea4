package com.dsdl.eidea.core.exception;

/**
 * Created by 刘大磊 on 2017/6/5 11:30.
 * 核心模块发生异常
 */
public class CoreException extends RuntimeException {
    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
