package com.lemon.oauth.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.lemon.entity.LocalUser;
import com.lemon.exception.ForbiddenException;
import com.lemon.exception.UnAuthenticatedException;
import com.lemon.interceptors.ScopeLevel;
import com.lemon.oauth.core.JwtToken;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName LoginInterceptor
 **/
public class LoginInterceptor implements HandlerInterceptor {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER_PATTERN = "^Bearer$";

    private JwtToken jwtToken;

    public LoginInterceptor(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        if (!scopeLevel.isPresent()) {
            return true;
        }
        String bearerToken = request.getHeader(LoginInterceptor.AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(bearerToken)) {
            throw new UnAuthenticatedException(10004);
        }
        String tokens[] = bearerToken.split(" ");
        if (!(tokens.length == 2)) {
            throw new UnAuthenticatedException(10004);
        }
        if (!Pattern.matches(LoginInterceptor.BEARER_PATTERN,tokens[0])){
            throw new UnAuthenticatedException(10004);
        }
        String token = tokens[1];
        Optional<Map<String, Claim>> optionalMap = jwtToken.getClaims(token);
        Map<String, Claim> map = optionalMap
                .orElseThrow(() -> new UnAuthenticatedException(10004));
        boolean valid = this.hasPermission(scopeLevel.get(), map);
        if (valid) {
            this.setToThreadLocal(map);
        }
        return valid;
    }

    private void setToThreadLocal(Map<String, Claim> map) {
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();
        LocalUser.set(uid, scope);
    }

    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map) {
        int level = scopeLevel.value();
        Integer scope = map.get("scope").asInt();
        if (level > scope) {
            throw new ForbiddenException(10005);
        }
        return true;
    }

    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel == null) {
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}