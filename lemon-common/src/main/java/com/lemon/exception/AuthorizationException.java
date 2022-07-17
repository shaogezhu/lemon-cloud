package com.lemon.exception;

import org.springframework.http.HttpStatus;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName AuthorizationException
 **/
public class AuthorizationException extends HttpException {
    public AuthorizationException(int code) {
        this.httpStatusCode = HttpStatus.FORBIDDEN.value();
        this.code = code;
    }
}
