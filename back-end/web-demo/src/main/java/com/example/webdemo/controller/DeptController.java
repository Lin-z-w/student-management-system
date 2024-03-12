package com.example.webdemo.controller;

import com.example.webdemo.anno.Log;
import com.example.webdemo.pojo.Dept;
import com.example.webdemo.pojo.Result;
import com.example.webdemo.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    // @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping("/depts")
    public Result list() {
        log.info("query all dept info");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    /**
     * delete dept
     * @return Result
     */
    @Log
    @DeleteMapping("/depts/{id}")
    public Result deleteById(@PathVariable Integer id) {
        log.info("delete dept by id : {}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept) {
        deptService.save(dept);
        return Result.success();
    }
}
