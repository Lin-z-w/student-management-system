package com.example.webdemo.service;

import com.example.webdemo.pojo.DeptLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface DeptLogService {
    public void insert(DeptLog deptLog);
}
