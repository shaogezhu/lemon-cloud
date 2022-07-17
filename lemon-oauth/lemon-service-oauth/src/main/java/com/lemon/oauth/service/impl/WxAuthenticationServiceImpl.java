package com.lemon.oauth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemon.exception.ParameterException;
import com.lemon.oauth.core.JwtToken;
import com.lemon.oauth.service.WxAuthenticationService;
import com.lemon.oauth.feign.UserFeign;
import com.lemon.oauth.pojo.wx.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName WxAuthenticationServiceImpl
 **/
@Service
public class WxAuthenticationServiceImpl implements WxAuthenticationService {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserFeign userFeign;

    @Value("${wx.codetosession}")
    private String codeToSessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public String codeToSession(String code) {
        String url = MessageFormat.format(this.codeToSessionUrl, this.appid, this.appsecret, code);
        RestTemplate rest = new RestTemplate();
        Map<String, Object> session = new HashMap<>();
        String sessionText = rest.getForObject(url, String.class);
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        String openid = (String)session.get("openid");
        if (openid == null){
            throw new ParameterException(20004);
        }
        User userOptional = this.userFeign.findByOpenid(openid);
        if(userOptional!=null){
            // 默认等级为8，将来可用于权限校验
            return jwtToken.makeToken(userOptional.getId(),8);
        }
        Long uid = userFeign.insertByOpenid(openid);
        return jwtToken.makeToken(uid);
    }
}
