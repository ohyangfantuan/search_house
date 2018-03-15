package com.oywy.core.security;

import cn.hutool.core.util.ObjectUtil;
import com.oywy.entity.User;
import com.oywy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证
 * Created by oywy on 2018/3/14.
 */
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String LOGIN_ERROR = "authError";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        //认证
        //判断user是否存在
        User user = userService.findByName(userName);
        if (ObjectUtil.isNull(user))
            throw new AuthenticationCredentialsNotFoundException(LOGIN_ERROR);
        //判断密码是否正确
        if (passwordEncoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        throw new BadCredentialsException(LOGIN_ERROR);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
