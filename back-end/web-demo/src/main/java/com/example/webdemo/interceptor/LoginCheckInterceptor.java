package com.example.webdemo.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.example.webdemo.pojo.Result;
import com.example.webdemo.utils.JwtUtils;
import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");

        String url = request.getRequestURL().toString();
        log.info("request url: {}", url);

        if (url.matches(".+/login")) {
            log.info("login");
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        String jwt = request.getHeader("token");
        if (StringUtil.isEmpty(jwt)) {
            Result error = Result.error("NOT_LOGIN");
            String noLogin = JSONObject.toJSONString(error);
            response.getWriter().write(noLogin);
            return false;
        }

        try {
            JwtUtils.parseJWT(jwt);
        }
        catch (Exception e) {
            Result error = Result.error("NOT_LOGIN");
            String noLogin = JSONObject.toJSONString(error);
            response.getWriter().write(noLogin);
            return false;
        }

        log.info("login success or already login");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
