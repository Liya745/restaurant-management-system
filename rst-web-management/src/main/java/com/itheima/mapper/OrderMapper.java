package com.itheima.mapper;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderQueryPara;
import com.itheima.pojo.Result;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select order_id, rstuser_id, dish_id, order_time, quantity, total_price, username, dishname from rstorder")
    List<Order> getOrders();

    @Insert("insert into rstorder(rstuser_id, dish_id, order_time, quantity, total_price, username, dishname) VALUES (#{rstuserId}, #{dishId}, #{orderTime}, #{quantity}, #{totalPrice}, #{userName}, #{dishName})")
    void addOrder(Order order);

    @Delete("delete from rstorder where order_id = #{id}")
    void deleteOrderById(Integer id);

    @Update("update rstorder set dishName = #{dishName}, quantity = #{quantity}, total_price = #{totalPrice} where order_id = #{orderId}")
    void updateOrderById(Order order);

    @Select("select order_id, rstuser_id, dish_id, order_time, quantity, total_price, username, dishname from rstorder where order_id = #{id}")
    Order queryOrderById(Integer id);

    List<Order> conditionQueryOrder(OrderQueryPara orderQueryPara);

    @Select("select order_id, rstuser_id, dish_id, order_time, quantity, total_price, userName, dishName from rstorder where rstuser_id = #{rstuserId}")
    List<Order> getOrdersByRstuserId(Integer rstuserId);
}
