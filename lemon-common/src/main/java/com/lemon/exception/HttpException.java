package com.lemon.exception;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName HttpException
 **/
public class HttpException extends RuntimeException{
    protected  Integer code;
    protected  Integer httpStatusCode = 500;

    public Integer getCode() {
        return code;
    }
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}