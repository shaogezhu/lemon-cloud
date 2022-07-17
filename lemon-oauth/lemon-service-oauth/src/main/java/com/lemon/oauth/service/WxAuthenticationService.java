package com.lemon.oauth.service;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WxAuthenticationService
 **/
public interface WxAuthenticationService {
    String codeToSession(String account);
}
