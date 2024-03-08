package com.example.webdemo.controller;

import com.example.webdemo.pojo.Result;
import com.example.webdemo.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

//    @PostMapping("/upload")
//    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
//        log.info("upload file: {}, {}, {}", username, age, image);
//        String originalFilename = image.getOriginalFilename();
//        assert originalFilename != null;
//        int index = originalFilename.lastIndexOf(".");
//        String extname = originalFilename.substring(index);
//        String newFileName = UUID.randomUUID().toString() + extname;
//        log.info("new file name: {}", newFileName);
//        image.transferTo(new File("D:\\"+newFileName));
//        return Result.success();
//    }

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("upload file : {}", image);
        String url = aliOSSUtils.upload(image);
        return Result.success(url);
    }
}
