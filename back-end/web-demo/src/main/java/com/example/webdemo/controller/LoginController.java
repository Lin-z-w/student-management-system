package com.example.webdemo.controller;

import com.example.webdemo.pojo.Emp;
import com.example.webdemo.pojo.Result;
import com.example.webdemo.service.EmpService;
import com.example.webdemo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("emp login: {}", emp);
        Emp e = empService.login(emp);
        // 登录成功
        if (e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",emp.getId());
            claims.put("username",emp.getUsername());
            claims.put("name",emp.getName());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        return Result.error("用户名或密码错误");
    }
}
