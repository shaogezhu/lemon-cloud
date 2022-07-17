package com.lemon.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.exception.TokenInvalidException;
import com.lemon.oauth.config.CmsProperties;
import com.lemon.oauth.mapper.ClientMapper;
import com.lemon.oauth.pojo.ClientInfo;
import com.lemon.oauth.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ClientServiceImpl
 **/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, ClientInfo> implements ClientService {
    @Value("${lemon.security.jwt-key}")
    private String jwtKey;

    @Autowired
    private CmsProperties cmsProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getSecretKey(String clientId, String secret) {
        this.verification(clientId, secret);
        return jwtKey;
    }

    @Override
    public CmsProperties getSecretJwtKey(String clientId, String secret) {
        this.verification(clientId, secret);
        return cmsProperties;
    }


    private void verification(String clientId, String secret) {
        if (!StringUtils.hasText(clientId)||!StringUtils.hasText(secret)){
            throw new TokenInvalidException(20010);
        }
        ClientInfo clientInfo = this.getOne(new QueryWrapper<ClientInfo>().lambda().eq(ClientInfo::getClientId,clientId));
        if (clientInfo==null){
            throw new TokenInvalidException(20010);
        }
        String encode = passwordEncoder.encode(secret);
        if (!passwordEncoder.matches(secret,clientInfo.getSecret())){
            throw new TokenInvalidException(20011);
        }
    }

}
