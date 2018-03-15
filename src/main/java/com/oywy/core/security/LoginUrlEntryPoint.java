package com.oywy.core.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于role的登录入口控制器
 * Created by oywy on 2018/3/14.
 */
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final Map<String, String> authEntryPoint = new HashMap<>();

    private void init() {
        authEntryPoint.put("/admin/**", "/admin/login");
        authEntryPoint.put("/user/**", "/user/login");
    }

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        init();
    }

    /**
     * 根据请求跳转到指定的页面
     *
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String requestURI = request.getRequestURI();
        for (Map.Entry<String, String> authEntry : authEntryPoint.entrySet()) {
            if (pathMatcher.match(authEntry.getKey(), requestURI))
                return authEntry.getValue();
        }
        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
