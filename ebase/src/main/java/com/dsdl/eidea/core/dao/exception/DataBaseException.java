package com.dsdl.eidea.core.dao.exception;

/**
 * Created by admin on 2016/8/18.
 */
public class DataBaseException extends RuntimeException {
    public DataBaseException(String msg)
    {
        super(msg);
        this.message=msg;
    }
    public DataBaseException(String msg, Throwable e)
    {
        super(msg,e);
        this.message=msg;
    }
    public DataBaseException()
    {

    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    private String message;

}
