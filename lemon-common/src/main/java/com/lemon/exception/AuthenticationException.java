package com.lemon.exception;

import org.springframework.http.HttpStatus;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName AuthenticationException
 **/
public class AuthenticationException extends HttpException {
    public AuthenticationException(int code) {
        this.httpStatusCode = HttpStatus.UNAUTHORIZED.value();
        this.code = code;
    }
}

