package com.itheima.service.impl;

import com.itheima.mapper.DishCommentsMapper;
import com.itheima.mapper.DishMapper;
import com.itheima.mapper.RstUserMapper;
import com.itheima.pojo.*;
import com.itheima.service.DishCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishCommentsServiceImpl implements DishCommentsService {

    @Autowired
    DishCommentsMapper dishCommentsMapper;

    @Autowired
    RstUserMapper rstUserMapper;

    @Autowired
    DishMapper dishMapper;

    @Override
    public void addDishComments(Integer rstuserId, Integer dishId, String userComment) {
        dishCommentsMapper.addDishComments(rstuserId,dishId,userComment);
    }

    @Override
    public List<DishComments> queryDishCommentsByRstuserId(Integer rstuserId) {
        List<DishComments> dishCommentsList = dishCommentsMapper.queryDishCommentsByRstuserId(rstuserId);
        return dishCommentsList;
    }

    @Override
    public List<MyComments> getDishCommentsByRstuserId(Integer rstuserId) {
        List<DishComments> dishCommentsList = dishCommentsMapper.queryDishCommentsByRstuserId(rstuserId);

        List<MyComments> myCommentsList = new ArrayList<>();

        for (DishComments dishComments : dishCommentsList) {
            // 获取用户名
            RstUser rstUser = rstUserMapper.queryRstUserById(dishComments.getRstuserId());
            String username = rstUser != null ? rstUser.getUsername() : "未知用户";

            // 获取菜品名
            Dish dish = dishMapper.queryDishById(dishComments.getDishId());
            String dishname = dish != null ? dish.getDishname() : "未知菜品";

            // 封装成MyComments对象
            MyComments myComments = new MyComments();
            myComments.setUsername(username);
            myComments.setDishname(dishname);
            myComments.setUserComment(dishComments.getUserComment());
            myCommentsList.add(myComments);

        }
        return myCommentsList;
    }

    @Override
    public void deleteDishComments(Integer rstuserId,String dishname, String userComment) {
        Dish dish = dishMapper.queryDishByDishname(dishname);
        Integer dishId = dish.getId();
        dishCommentsMapper.deleteDishComments(rstuserId,dishId,userComment);
    }

    @Override
    public List<OneDishComments> getDishCommentsByDishId(Integer dishId) {
        // 通过dishId查询到所有的这个菜品的评论数据
        List<DishComments> dishCommentsList = dishCommentsMapper.queryDishCommentsByDishId(dishId);

        // 创建OneDishComments列表
        List<OneDishComments> oneDishCommentsList = new ArrayList<>();

        // 遍历每个评论数据
        for (DishComments dishComments : dishCommentsList) {
            // 根据rstuserId查询到该用户的用户名
            RstUser rstUser = rstUserMapper.queryRstUserById(dishComments.getRstuserId());
            String username = rstUser != null ? rstUser.getUsername() : "未知用户";

            // 封装到OneDishComments对象中
            OneDishComments oneDishComments = new OneDishComments();
            oneDishComments.setRstuserId(dishComments.getRstuserId());
            oneDishComments.setUsername(username);
            oneDishComments.setUserComments(dishComments.getUserComment());

            // 添加到列表中
            oneDishCommentsList.add(oneDishComments);
        }

        return oneDishCommentsList;
    }

    @Override
    public void deleteDishCommentsByRstuserIds(List<Integer> rstuserIds) {
        dishCommentsMapper.deleteDishCommentsByRstuserIds(rstuserIds);
    }
}
