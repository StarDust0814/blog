package com.sangeng.utils;

import com.mysql.cj.log.Log;
import com.sangeng.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 从security中获取用户信息
 */
public class SecurityUtils {
    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication(){
        return  SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }
}
