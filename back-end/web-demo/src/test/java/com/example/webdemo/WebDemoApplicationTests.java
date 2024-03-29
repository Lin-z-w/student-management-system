package com.example.webdemo;

import com.example.webdemo.mapper.DeptLogMapper;
import com.example.webdemo.pojo.DeptLog;
import com.example.webdemo.service.DeptLogService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.security.KeyPairGenerator;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class WebDemoApplicationTests {

    @Autowired
    private DeptLogService deptLogService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testJWT() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name","Tom");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"kakaademo000000000000000000000000000000000000000000000000")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParser() {
        byte[] key = "kakaademo000000000000000000000000000000000000000000000000".getBytes();
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey("kakaademo000000000000000000000000000000000000000000000000")
                .build()
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiaWQiOjEsImV4cCI6MTcwOTgzMDQwMX0.y9LNdGRnaOMIx6oFzr27odVdwovzWVe78WT0s_euIpI")
                .getBody();
        System.out.println(claims);
    }

    @Test
    public void testDeptLog() {
        DeptLog deptLog = new DeptLog(LocalDateTime.now(), "解散部门："+1);
        deptLogService.insert(deptLog);
    }
}
