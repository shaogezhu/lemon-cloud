package com.lemon.exception;


/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ForbiddenException
 **/
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code=code;
        this.httpStatusCode=403;
    }
}
