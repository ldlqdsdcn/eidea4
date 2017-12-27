package indi.liudalei.eidea.base.exception;

/**
 * Created by 刘大磊 on 2017/5/19 14:01.
 * 找不到组织异常
 */
public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(String message)
    {
        super(message);
    }
    public OrganizationNotFoundException(String message,Throwable e)
    {
        super(message,e);
    }
}
