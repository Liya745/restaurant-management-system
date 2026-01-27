package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer orderId;
    private Integer rstuserId;
    private Integer dishId;
    private LocalDateTime orderTime;
    private Integer quantity;
    private double totalPrice;
    private String userName;
    private String dishName;
}
