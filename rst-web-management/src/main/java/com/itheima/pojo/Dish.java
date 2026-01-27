package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private Integer id;
    private String dishname;
    private Integer dishimage;
    private double price;
    private int remain;
    private String intro;
}
