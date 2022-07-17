package com.lemon.exception;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UnAuthenticatedException
 **/
public class UnAuthenticatedException extends HttpException{
    public UnAuthenticatedException(int code){
        this.code = code;
        this.httpStatusCode = 401;
    }
}
