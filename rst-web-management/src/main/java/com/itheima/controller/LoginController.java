package com.itheima.controller;

import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import com.itheima.pojo.RstUser;
import com.itheima.service.RstUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆Controller
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private RstUserService rstUserService;

    /**
     * 登陆
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody  RstUser rstUser){
        log.info("登陆: {}", rstUser);
        LoginInfo loginInfo = rstUserService.login(rstUser);
        if(loginInfo != null){
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }
}
