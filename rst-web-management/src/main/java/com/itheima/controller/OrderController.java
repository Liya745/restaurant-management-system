package com.itheima.controller;

import com.itheima.mapper.OrderMapper;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderQueryPara;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/getOrders")
    public Result getOrders() {
        List<Order> orderList = orderService.getOrders();
        return Result.success(orderList);
    }

    @PostMapping("/addOrder")
    public Result addDish(@RequestBody Order order){
        Result result =  orderService.addOrder(order);
        Integer code = result.getCode();
        String msg ;
        if(code == 1){
            return Result.success();
        } else if (code == 0) {
            msg = result.getMsg();
            return Result.error(msg);
        }
        return Result.error("--正常情况不会输出此语句--");
    }

    @PostMapping("/deleteOrderById")
    public Result deleteOrderById(@RequestBody Order order){
        orderService.deleteOrderById(order);
        return Result.success();
    }

    @PostMapping("/updateOrderById")
    public Result updateOrderById(@RequestBody Order order, Integer originalOrderId){
        Result result = orderService.updateOrderById(order, originalOrderId);

        Integer code = result.getCode();
        String msg ;
        if(code == 1){
            return Result.success();
        } else if (code == 0) {
            msg = result.getMsg();
            return Result.error(msg);
        }
        return Result.error("--正常情况不会输出此语句--");
    }

    @PostMapping("/paginationQueryOrders")
    public Result paginationQueryOrders(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize){
        PageResult<Order> pageResult = orderService.paginationQueryOrders(page,pageSize);
        return Result.success(pageResult);
    }
    /**
     * 条件查询
     */
    @PostMapping("/conditionQueryOrder")
    public Result conditionQueryOrder(@RequestBody OrderQueryPara orderQueryPara){
        PageResult<Order> pageResult = orderService.conditionQueryOrder(orderQueryPara);
        return Result.success(pageResult);
    }

    @PostMapping("/getOrdersByRstuserId")
    public Result getOrdersByRstuserId(@RequestParam Integer rstuserId){
        Result result = orderService.getOrdersByRstuserId(rstuserId);
        return Result.success(result.getData());
    }
}
