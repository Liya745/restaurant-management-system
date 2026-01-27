package com.itheima.service;


import com.itheima.pojo.Order;
import com.itheima.pojo.OrderQueryPara;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    Result addOrder(Order order);

    void deleteOrderById(Order order);

    Result updateOrderById(Order order, Integer originalOrder);

    PageResult<Order> paginationQueryOrders(Integer page, Integer pageSize);

    PageResult<Order> conditionQueryOrder(OrderQueryPara orderQueryPara);

    Result getOrdersByRstuserId(Integer rstuserId);
}
