package com.example.webdemo.aop;

import com.alibaba.fastjson2.JSONObject;
import com.example.webdemo.mapper.OperateLogMapper;
import com.example.webdemo.pojo.OperateLog;
import com.example.webdemo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;
    @Around("@annotation(com.example.webdemo.anno.Log)")
    public Object recordLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取用户id
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer userId = (Integer) claims.get("id");
        log.info("user id : {}", userId);

        // 获取起始时间
        LocalDateTime operateTime = LocalDateTime.now();

        // 获取类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        // 获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();

        // 获取方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        // 获取方法返回值
        Object result = proceedingJoinPoint.proceed();
        String returnValue = JSONObject.toJSONString(result);
        long end = System.currentTimeMillis();

        // 操作耗时
        long costTime = end - begin;

        // 记录操作日志
        OperateLog operateLog = new OperateLog(null, userId, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insert(operateLog);
        return result;
    }
}
