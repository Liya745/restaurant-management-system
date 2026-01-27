package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RstUser {
    private Integer id;
    private String username;
    private Integer age;
    private Integer gender;
    private String phone;
    private String password;
    private Integer avater;
    private Integer level;
    private Integer signInCount;
}
