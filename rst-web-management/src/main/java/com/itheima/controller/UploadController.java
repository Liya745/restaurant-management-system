package com.itheima.controller;


import com.itheima.anno.LogOperation;
import com.itheima.pojo.Result;
import com.itheima.pojo.RstUser;
import com.itheima.service.RstUserService;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private RstUserService rstUserService;

    /**
     * 上传文件并更新用户头像
     * @param file 头像文件
     * @param userId 用户ID（可选）
     * @return 返回头像URL
     * @throws Exception
     */
    @LogOperation
    @PostMapping("/upload")
    public Result upload(MultipartFile file, @RequestParam(required = false) Integer userId) throws Exception {
        log.info("文件上传：{}", file.getOriginalFilename());
        
        // 将文件上传到 OSS
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传到OSS，url：{}", url);
        
        // 如果传入了 userId，则更新用户头像
        if (userId != null) {
            log.info("开始更新用户 {} 的头像", userId);
            
            // 查询用户是否存在
            RstUser user = rstUserService.queryRstUserById(userId);
            if (user == null) {
                log.error("用户ID {} 不存在", userId);
                return Result.error("用户不存在");
            }
            
            // 更新用户头像
            user.setAvatar(url);
            rstUserService.updateRstUserById(user);
            log.info("用户 {} 头像更新成功：{}", userId, url);
        }
        
        return Result.success(url);
    }
}
