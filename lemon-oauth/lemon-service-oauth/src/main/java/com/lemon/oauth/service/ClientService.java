package com.lemon.oauth.service;

import com.lemon.oauth.config.CmsProperties;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ClientService
 **/
public interface ClientService {
    String getSecretKey(String clientId, String secret);

    CmsProperties getSecretJwtKey(String clientId, String secret);
}
