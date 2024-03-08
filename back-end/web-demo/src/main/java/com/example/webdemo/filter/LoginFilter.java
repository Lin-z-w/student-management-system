package com.example.webdemo.filter;

import com.alibaba.fastjson2.JSONObject;
import com.example.webdemo.pojo.Result;
import com.example.webdemo.utils.JwtUtils;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();
        log.info("request url: {}", url);

        if (url.matches(".+/login")) {
            log.info("login");
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }

        String jwt = request.getHeader("token");
        if (StringUtil.isEmpty(jwt)) {
            Result error = Result.error("NO_LOGIN");
            String noLogin = JSONObject.toJSONString(error);
            response.getWriter().write(noLogin);
            return ;
        }

        try {
            JwtUtils.parseJWT(jwt);
        }
        catch (Exception e) {
            Result error = Result.error("NO_LOGIN");
            String noLogin = JSONObject.toJSONString(error);
            response.getWriter().write(noLogin);
            return ;
        }

        log.info("login success or already login");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
