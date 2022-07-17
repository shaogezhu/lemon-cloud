package com.lemon.exception;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName NotFoundException
 **/
public class NotFoundException extends HttpException {
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
