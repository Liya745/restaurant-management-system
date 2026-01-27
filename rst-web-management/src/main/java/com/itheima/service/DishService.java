package com.itheima.service;

import com.itheima.pojo.Dish;
import com.itheima.pojo.DishQueryPara;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface DishService {
    List<Dish> getDishes();


    void deleteDishById(Integer id);


    void addDish(Dish dish);

    void updateDishById(Dish dish);

    Dish queryDishById(Integer id);

    PageResult<Dish> paginationQueryDishes(Integer page, Integer pageSize);

    PageResult<Dish> conditionQueryDish(DishQueryPara dishQueryPara);
}
