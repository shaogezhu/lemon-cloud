package com.lemon.exception;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ParameterException
 **/
public class ParameterException extends HttpException{
    public ParameterException(int code){
        this.code = code;
        this.httpStatusCode = 400;
    }
}

