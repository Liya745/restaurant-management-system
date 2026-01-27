package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishQueryPara {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String dishName;
}
