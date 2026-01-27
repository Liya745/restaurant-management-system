package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RstUserQueryPara {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String username;
    private Integer gender;
    private Integer age;
    private String phone;
}
