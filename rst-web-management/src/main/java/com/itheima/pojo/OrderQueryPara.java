package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryPara {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String userName;
    private String dishName;
}
