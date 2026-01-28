package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装登陆信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Integer id; // 用户ID
    private String username; // 用户名
    private String token; // 登陆令牌
}
