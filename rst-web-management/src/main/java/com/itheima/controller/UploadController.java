package com.itheima.controller;


import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
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

    /**
     * 本地磁盘存储的方案（不推荐）
     * @param file
     * @return
     * @throws IOException
     */
//    @PostMapping("/upload")
//    public Result upload(MultipartFile file) throws IOException {
//        log.info("接收参数：{}", file);
//        //获取原始文件名
//        String originalFilename = file.getOriginalFilename();
//        //获取文件扩展名并赋予随机文件名字
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString() + extension;
//
//        //保存文件
//        file.transferTo(new File("E:/JavaWeb-java-items/web-ai-project02/images/" + newFileName));
//        return Result.success();
//    }


    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}", file.getOriginalFilename());
        //需要将文件交给oss来存储管理
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传到OSS，url：{}", url);
        return Result.success(url);
    }
}
