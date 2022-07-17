package com.lemon.exception;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ServerErrorException
 **/
public class ServerErrorException extends HttpException{
    public ServerErrorException(Integer code) {
        this.code = code;
        this.httpStatusCode = 500;
    }
}
