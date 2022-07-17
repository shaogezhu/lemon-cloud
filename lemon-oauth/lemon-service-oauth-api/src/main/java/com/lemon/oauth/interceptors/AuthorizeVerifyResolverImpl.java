package com.lemon.oauth.interceptors;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.lemon.entity.LocalUser;
import com.lemon.exception.AuthenticationException;
import com.lemon.exception.AuthorizationException;
import com.lemon.exception.NotFoundException;
import com.lemon.exception.TokenInvalidException;
import com.lemon.oauth.bean.MetaInfo;
import com.lemon.oauth.bean.PermissionMetaCollector;
import com.lemon.oauth.feign.CmsUserFeign;
import com.lemon.oauth.interfaces.AuthorizeVerifyResolver;
import com.lemon.oauth.pojo.cms.PermissionDO;
import com.lemon.oauth.pojo.cms.UserDO;
import io.github.talelin.core.enumeration.UserLevel;
import io.github.talelin.core.token.DoubleJWT;
import io.github.talelin.core.util.AnnotationUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName AuthorizeVerifyResolverImpl
 **/
public class AuthorizeVerifyResolverImpl implements AuthorizeVerifyResolver {

    public final static String AUTHORIZATION_HEADER = "Authorization";

    public final static String BEARER_PATTERN = "^Bearer$";

    private DoubleJWT jwt;
    private CmsUserFeign userFeign;

    public AuthorizeVerifyResolverImpl(DoubleJWT jwt, CmsUserFeign userFeign) {
        this.jwt = jwt;
        this.userFeign = userFeign;
    }

    @Override
    public boolean handleLogin(HttpServletRequest request, HttpServletResponse response, MetaInfo meta) {
        String tokenStr = verifyHeader(request, response);
        Map<String, Claim> claims;
        try {
            claims = jwt.decodeAccessToken(tokenStr);
        } catch (TokenExpiredException e) {
            throw new TokenInvalidException(10051);
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            throw new TokenInvalidException(10041);
        }
        return getClaim(claims);
    }

    @Override
    public boolean handleGroup(HttpServletRequest request, HttpServletResponse response, MetaInfo meta) {
        handleLogin(request, response, meta);
        UserDO user = userFeign.getUserById(LocalUser.getUserId());
        if (verifyAdmin(user)) {
            return true;
        }
        Long userId = user.getId();
        String permission = meta.getPermission();
        String module = meta.getModule();
        List<PermissionDO> permissions = userFeign.getUserPermissions(userId);
        boolean matched = permissions.stream().anyMatch(it -> it.getModule().equals(module) && it.getName().equals(permission));
        if (!matched) {
            throw new AuthenticationException(10001);
        }
        return true;
    }

    @Override
    public boolean handleAdmin(HttpServletRequest request, HttpServletResponse response, MetaInfo meta) {
        handleLogin(request, response, meta);
        UserDO user = userFeign.getUserById(LocalUser.getUserId());
        if (!verifyAdmin(user)) {
            throw new AuthenticationException(10001);
        }
        return true;
    }


    @Override
    public boolean handleRefresh(HttpServletRequest request, HttpServletResponse response, MetaInfo meta) {
        String tokenStr = verifyHeader(request, response);
        Map<String, Claim> claims;
        try {
            claims = jwt.decodeRefreshToken(tokenStr);
        } catch (TokenExpiredException e) {
            throw new TokenInvalidException(10052);
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            throw new TokenInvalidException(10042);
        }
        return getClaim(claims);
    }

    @Override
    public boolean handleNotHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void handleAfterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 很重要，请求结束后，一定要清理 ThreadLocal 中的用户信息
        LocalUser.clear();
    }

    private boolean getClaim(Map<String, Claim> claims) {
        if (claims == null) {
            throw new TokenInvalidException(10041);
        }
        Long identity = claims.get("identity").asLong();
        UserDO user = userFeign.getUserById(identity);
        if (user == null) {
            throw new NotFoundException(10021);
        }
        LocalUser.set(user.getId());
        return true;
    }

    /**
     * 检查用户是否为管理员
     *
     * @param user 用户
     */
    private boolean verifyAdmin(UserDO user) {
        return userFeign.checkIsRootByUserId(user.getId());
    }

    private String verifyHeader(HttpServletRequest request, HttpServletResponse response) {
        // 处理头部header,带有access_token的可以访问
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization == null || Strings.isBlank(authorization)) {
            throw new AuthorizationException(10012);
        }
        String[] splits = authorization.split(" ");
        if (splits.length != 2) {
            throw new AuthorizationException(10013);
        }
        // Bearer 字段
        String scheme = splits[0];
        // token 字段
        String tokenStr = splits[1];
        if (!Pattern.matches(BEARER_PATTERN, scheme)) {
            throw new AuthorizationException(10013);
        }
        return tokenStr;
    }

    /**
     * @author shaogezhu
     * @version 1.0.0
     * @ClassName AuthorizeInterceptor
     **/
    public static class AuthorizeInterceptor extends HandlerInterceptorAdapter {

        @Autowired
        private AuthorizeVerifyResolver authorizeVerifyResolver;

        @Autowired
        private PermissionMetaCollector permissionMetaCollector;

        private String[] excludeMethods = new String[]{"OPTIONS"};

        public AuthorizeInterceptor() {
        }

        /**
         * 构造函数
         *
         * @param excludeMethods 不检查方法
         */
        public AuthorizeInterceptor(String[] excludeMethods) {
            this.excludeMethods = excludeMethods;
        }

        /**
         * 前置处理
         *
         * @param request  request 请求
         * @param response 相应
         * @param handler  处理器
         * @return 是否成功
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            if (checkInExclude(request.getMethod())) {
                // 有些请求方法无需检测，如OPTIONS
                return true;
            }
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String identity = className + "#" + methodName;
                MetaInfo meta = permissionMetaCollector.findMeta(identity);
                // 考虑两种情况，1. 有 meta；2. 无 meta
                if (meta == null) {
                    // 无meta的话，adminRequired和loginRequired
                    return this.handleNoMeta(request, response, method);
                } else {
                    // 有meta在权限范围之内，需要判断权限
                    return this.handleMeta(request, response, method, meta);
                }
            } else {
                // handler不是HandlerMethod的情况
                return authorizeVerifyResolver.handleNotHandlerMethod(request, response, handler);
            }
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
            authorizeVerifyResolver.handlePostHandle(request, response, handler, modelAndView);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            authorizeVerifyResolver.handleAfterCompletion(request, response, handler, ex);
        }

        private boolean handleNoMeta(HttpServletRequest request, HttpServletResponse response, Method method) {
            Annotation[] annotations = method.getAnnotations();
            UserLevel level = AnnotationUtil.findRequired(annotations);
            switch (level) {
                case LOGIN:
                case GROUP:
                    // 分组权限
                    // 登陆权限
                    return authorizeVerifyResolver.handleLogin(request, response, null);
                case ADMIN:
                    // 管理员权限
                    return authorizeVerifyResolver.handleAdmin(request, response, null);
                case REFRESH:
                    // 刷新令牌
                    return authorizeVerifyResolver.handleRefresh(request, response, null);
                default:
                    return true;
            }
        }

        private boolean handleMeta(HttpServletRequest request, HttpServletResponse response, Method method, MetaInfo meta) {
            UserLevel level = meta.getUserLevel();
            switch (level) {
                case LOGIN:
                    // 登陆权限
                    return authorizeVerifyResolver.handleLogin(request, response, meta);
                case GROUP:
                    // 分组权限
                    return authorizeVerifyResolver.handleGroup(request, response, meta);
                case ADMIN:
                    // 管理员权限
                    return authorizeVerifyResolver.handleAdmin(request, response, meta);
                case REFRESH:
                    // 刷新令牌
                    return authorizeVerifyResolver.handleRefresh(request, response, meta);
                default:
                    return true;
            }
        }

        private boolean checkInExclude(String method) {
            for (String excludeMethod : excludeMethods) {
                if (method.equals(excludeMethod)) {
                    return true;
                }
            }
            return false;
        }
    }
}
