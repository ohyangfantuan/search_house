package com.oywy.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * Created by oywy on 2018/3/14.
 */
public class LoginAuthFailhandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private LoginUrlEntryPoint loginUrlEntryPoint;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String target = loginUrlEntryPoint.determineUrlToUseForThisRequest(request, response, exception);
        target += "?" + exception.getMessage();
        System.out.println(target);
        this.setDefaultFailureUrl(target);
        super.onAuthenticationFailure(request, response, exception);
    }
}
