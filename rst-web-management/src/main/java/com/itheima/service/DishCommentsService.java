package com.itheima.service;

import com.itheima.pojo.DishComments;
import com.itheima.pojo.MyComments;
import com.itheima.pojo.OneDishComments;

import java.util.List;

public interface DishCommentsService {
    void addDishComments(Integer rstuserId, Integer dishId, String userComment);

    List<DishComments> queryDishCommentsByRstuserId(Integer rstuserId);

    List<MyComments> getDishCommentsByRstuserId(Integer rstuserId);

    void deleteDishComments(Integer rstuserId,String dishname,String userComment);

    List<OneDishComments> getDishCommentsByDishId(Integer dishId);

    void deleteDishCommentsByRstuserIds(List<Integer> rstuserIds);
}
