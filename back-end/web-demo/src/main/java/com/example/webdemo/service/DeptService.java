package com.example.webdemo.service;

import com.example.webdemo.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> list();

    void deleteById(Integer id);
}
