package com.lemon.exception;

import org.springframework.http.HttpStatus;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TokenInvalidException
 **/
public class TokenInvalidException extends HttpException {
    public TokenInvalidException(int code) {
        this.httpStatusCode = HttpStatus.UNAUTHORIZED.value();
        this.code = code;
    }
}