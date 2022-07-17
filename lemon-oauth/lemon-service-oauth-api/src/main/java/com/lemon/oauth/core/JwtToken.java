package com.lemon.oauth.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName JwtToken
 **/

public class JwtToken {
    private String jwtKey;
    private final static Integer expiredTimeIn = 1800;
    private final static Integer defaultScope = 8;

    public JwtToken(String key) {
        this.jwtKey = key;
    }

    public Optional<Map<String, Claim>> getClaims(String token) {
        DecodedJWT decodedJWT;
        Algorithm algorithm = Algorithm.HMAC256(this.jwtKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
        return Optional.of(decodedJWT.getClaims());
    }

    public Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.jwtKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }


    public String makeToken(Long uid, Integer scope) {
        return this.getToken(uid, scope);
    }

    public String makeToken(Long uid) {
        return this.getToken(uid, JwtToken.defaultScope);
    }

    private String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtKey);
        Map<String, Date> map = this.calculateExpiredIssues();

        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(map.get("expiredTime"))
                .withIssuedAt(map.get("now"))
                .sign(algorithm);
    }

    private Map<String, Date> calculateExpiredIssues() {
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
        map.put("now", now);
        map.put("expiredTime", calendar.getTime());
        return map;
    }
}