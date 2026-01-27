package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.DishMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.RstUserMapper;
import com.itheima.mapper.RstUserMsgMapper;
import com.itheima.pojo.*;
import com.itheima.service.OrderService;
import com.itheima.service.RstUserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RstUserMapper rstUserMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private RstUserMsgMapper rstUserMsgMapper;

    @Override
    public List<Order> getOrders() {
        return orderMapper.getOrders();
    }

    @Override
    public Result addOrder(Order order) {
        //将订单的下单时间赋予中国时区最新时间
        order.setOrderTime(LocalDateTime.now());

        //将订单的用户ID和菜品ID提取到变量中，还有下单菜品数量也提取出来
        Integer rstuserId = order.getRstuserId();
        Integer dishId = order.getDishId();
        Integer orderQuantity = order.getQuantity();

        //根据用户ID和菜品ID查询到该ID下的用户和菜品实体
        RstUser rstUsrRes = rstUserMapper.queryRstUserById(rstuserId);
        Dish dishRes = dishMapper.queryDishById(dishId);

        //将菜品的单价和库存提取到变量中
        double unitPrice = dishRes.getPrice();
        Integer remainRes = dishRes.getRemain();
        System.out.println("-----------------------------订单信息为：" + order);

        //下单后的库存剩余数量
        Integer afterOrderRemain = remainRes - orderQuantity;

        //查询该用户的余额
        double balance = rstUserMsgMapper.queryRstUserMsgBalanceById(rstuserId);

        //算出订单的总价格totalPrice
        double totalPrice = unitPrice * orderQuantity;

        double afterBalance = balance - totalPrice;

        if(rstUsrRes == null){
            return Result.error("用户表中不存在此用户");
        }else if(afterOrderRemain < 0){
            return Result.error("库存不足");
        } else if (afterBalance < 0) {
            return Result.error("余额不足");
        } else {
            //更新菜品的库存以及订单赋予总价格
            dishRes.setRemain(afterOrderRemain);
            order.setTotalPrice(totalPrice);
            dishMapper.updateDishById(dishRes);

            //将用户名赋予这个order
            RstUser rstUser = rstUserMapper.queryRstUserById(rstuserId);
            String userName = rstUser.getUsername();
            order.setUserName(userName);

            //添加订单
            orderMapper.addOrder(order);

            rstUserMsgMapper.updateRstUserMsgBalanceById(rstuserId,afterBalance);

            return Result.success();
        }
    }

    @Override
    public void deleteOrderById(Order order) {
        Integer id = order.getOrderId();
        orderMapper.deleteOrderById(id);
    }


    /**
     * order当做updateOrder懒得改变量了
     * 待优化事项:1.优化修改不同菜品时的数据更改（已优化）
     * @param order
     * @param
     * @return
     */
    @Transactional
    @Override
    public Result updateOrderById(Order order, Integer originalOrderId) {

        //获取旧的order对象数据
        Order originalOrder = orderMapper.queryOrderById(originalOrderId);

        //提取新旧order中dishId
        Integer orderDishId = order.getDishId();
        Integer originalOrderDishId = originalOrder.getDishId();

        //提取新旧order中的quantity
        Integer orderQuantity = order.getQuantity();
        Integer originalOrderQuantity = originalOrder.getQuantity();

        if(orderDishId == originalOrderDishId) {
            Integer changeAmount = originalOrderQuantity - orderQuantity;
            Dish dishRes = dishMapper.queryDishById(orderDishId);
            if(dishRes.getRemain() + changeAmount < 0 ) {
                return Result.error("菜品库存不足，当前库存剩余：" + dishRes.getRemain() + "份");
            }else {
                dishRes.setRemain(dishRes.getRemain() + changeAmount);
                dishMapper.updateDishById(dishRes);

                Double unitDishPrice = dishRes.getPrice();
                order.setTotalPrice(orderQuantity * unitDishPrice);
                orderMapper.updateOrderById(order);
                return Result.success();
            }
        }else {
            Dish orderDishRes = dishMapper.queryDishById(orderDishId);
            Dish originalOrderDishRes = dishMapper.queryDishById(originalOrderDishId);

            if(orderDishRes.getRemain() < order.getQuantity()) {
                return Result.error("您点的菜品库存不足，当前菜品剩余：" + orderDishRes.getRemain() +"份");
            } else {
                orderDishRes.setRemain(orderDishRes.getRemain() - order.getQuantity());
                dishMapper.updateDishById(orderDishRes);
                originalOrderDishRes.setRemain(originalOrderDishRes.getRemain() + originalOrder.getQuantity());
                dishMapper.updateDishById(originalOrderDishRes);

                double orderUnitDishPrice = orderDishRes.getPrice();
                order.setTotalPrice(orderQuantity * orderUnitDishPrice);
                orderMapper.updateOrderById(order);

                return Result.success();
            }
        }
    }

    @Override
    public PageResult<Order> paginationQueryOrders(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Order> orders = orderMapper.getOrders();
        Page<Order> p = (Page<Order>) orders;
        return new PageResult<Order>(p.getTotal(),p.getResult());
    }

    @Override
    public PageResult<Order> conditionQueryOrder(OrderQueryPara orderQueryPara) {
        PageHelper.startPage(orderQueryPara.getPage(),orderQueryPara.getPageSize());
        List<Order> orderList =  orderMapper.conditionQueryOrder(orderQueryPara);
        Page<Order> p = (Page<Order>) orderList;
        return new PageResult<Order>(p.getTotal(),p.getResult());
    }

    @Override
    public Result getOrdersByRstuserId(Integer rstuserId) {
        List<Order> orderList = orderMapper.getOrdersByRstuserId(rstuserId);
        return Result.success(orderList);
    }


//    public int complexUpdate(Dish dish, RstUser user, String reason) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("dishId", dish.getId());
//        params.put("newPrice", dish.getPrice());
//        params.put("userId", user.getId());
//        params.put("username", user.getUsername());
//        params.put("reason", reason);
//
//        return sqlSessionTemplate.update(
//                "update menu set price = #{newPrice} where id = #{dishId} and exists(select 1 from rstuser where id = #{userId} and username = #{username})",
//                params);
//    }

}
