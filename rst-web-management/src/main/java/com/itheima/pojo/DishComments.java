package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishComments {
    private Integer rstuserId;
    private Integer dishId;
    private String userComment;
}
