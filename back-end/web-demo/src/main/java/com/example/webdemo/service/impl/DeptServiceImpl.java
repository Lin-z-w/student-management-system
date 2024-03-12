package com.example.webdemo.service.impl;

import com.example.webdemo.mapper.DeptMapper;
import com.example.webdemo.mapper.EmpMapper;
import com.example.webdemo.pojo.Dept;
import com.example.webdemo.pojo.DeptLog;
import com.example.webdemo.service.DeptLogService;
import com.example.webdemo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private DeptLogService deptLogService;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteById(Integer id) {
        try {
            deptMapper.deleteById(id);
            empMapper.deleteByDeptId(id);
        }
        finally {
            DeptLog deptLog = new DeptLog(LocalDateTime.now(), "解散部门："+id);
            deptLogService.insert(deptLog);
        }
    }

    @Override
    public void save(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }
}
