package com.sangeng.filter;


import com.alibaba.fastjson.JSON;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.utils.JwtUtil;
import com.sangeng.utils.RedisCache;
import com.sangeng.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头的token
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)){
            // 如果对应的接口不需要登录信息
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 解析token获取userid
        Claims claims = null;
        try{
            claims = JwtUtil.parseJWT(token);
        }catch (Exception e){
            e.printStackTrace();
            // token超时 或者非法
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        String userID = claims.getSubject();
        // 从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userID);
        if(Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        // 保存到 securitycontextholder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
