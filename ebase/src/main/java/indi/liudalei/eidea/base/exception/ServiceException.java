package indi.liudalei.eidea.base.exception;

/**
 * Created by 刘大磊 on 2017/5/19 15:10.
 * 系统业务层异常
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message)
    {
        super(message);
    }
    public ServiceException(String message,Throwable throwable)
    {
        super(message,throwable);
    }
}
