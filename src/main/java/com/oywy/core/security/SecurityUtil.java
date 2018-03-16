package com.oywy.core.security;

import com.oywy.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by oywy on 2018/3/16.
 */
public class SecurityUtil {
    public static User currentUser() {
        User currUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currUser;
    }

    public static Long currentUserId() {
        return currentUser().getId();
    }
}