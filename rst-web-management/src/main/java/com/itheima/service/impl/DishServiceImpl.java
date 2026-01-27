package com.itheima.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.DishMapper;
import com.itheima.pojo.Dish;
import com.itheima.pojo.DishQueryPara;
import com.itheima.pojo.PageResult;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;


    @Override
    public List<Dish> getDishes() {
        return dishMapper.getDishes();
    }

    @Override
    public void deleteDishById(Integer id) {
        dishMapper.deleteDishById(id);
    }

    @Override
    public void addDish(Dish dish) {
        dishMapper.addDish(dish);
    }

    @Override
    public void updateDishById(Dish dish) {
        dishMapper.updateDishById(dish);
    }

    @Override
    public Dish queryDishById(Integer id) {
        Dish dish = dishMapper.queryDishById(id);
        return dish;
    }

    @Override
    public PageResult<Dish> paginationQueryDishes(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Dish> dishList = dishMapper.getDishes();
        Page<Dish> p = (Page<Dish>) dishList;
        return new PageResult<Dish>(p.getTotal(), p.getResult());
    }

    @Override
    public PageResult<Dish> conditionQueryDish(DishQueryPara dishQueryPara) {
        PageHelper.startPage(dishQueryPara.getPage(),dishQueryPara.getPageSize());
        List<Dish> dishList = dishMapper.conditionQueryDish(dishQueryPara);
        Page<Dish> p = (Page<Dish>) dishList;
        return new PageResult<Dish>(p.getTotal(),p.getResult());
    }


}
