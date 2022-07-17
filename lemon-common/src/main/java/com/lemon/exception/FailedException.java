package com.lemon.exception;

import org.springframework.http.HttpStatus;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName FailedException
 **/
public class FailedException extends HttpException{

    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public FailedException(int code) {
        this.code = code;
        this.httpStatusCode = httpCode;
    }

}

