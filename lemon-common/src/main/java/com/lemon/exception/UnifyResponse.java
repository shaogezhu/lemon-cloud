package com.lemon.exception;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName UnifyResponse
 **/
public class UnifyResponse {
    private final int code;
    private final String message;
    private final String request;

    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }
}

