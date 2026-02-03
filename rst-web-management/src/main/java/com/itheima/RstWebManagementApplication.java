package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // 开启了SpringBoot对Servlet组件的支持
@SpringBootApplication
public class RstWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RstWebManagementApplication.class, args);
    }
}
