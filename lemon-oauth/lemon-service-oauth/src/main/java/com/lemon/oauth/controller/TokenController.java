package com.lemon.oauth.controller;

import com.lemon.exception.NotFoundException;
import com.lemon.oauth.core.JwtToken;
import com.lemon.oauth.dto.token.TokenDTO;
import com.lemon.oauth.dto.token.TokenGetDTO;
import com.lemon.oauth.service.WxAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName TokenController
 **/
@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private WxAuthenticationService wxAuthenticationService;

    @PostMapping("")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody @Validated TokenGetDTO userData) {
        Map<String, String> map = new HashMap<>();
        String token = null;
        switch (userData.getType()) {
            case USER_WX:
                token = wxAuthenticationService.codeToSession(userData.getAccount());
                break;
            case USER_Email:
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token", token);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verify(@RequestBody TokenDTO token) {
        Map<String, Boolean> map = new HashMap<>();
        Boolean valid = jwtToken.verifyToken(token.getToken());
        map.put("is_valid", valid);
        return ResponseEntity.ok(map);
    }
}

