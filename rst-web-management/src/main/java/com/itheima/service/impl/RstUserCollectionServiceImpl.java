package com.itheima.service.impl;

import com.itheima.mapper.DishMapper;
import com.itheima.mapper.RstUserCollectionMapper;
import com.itheima.pojo.Dish;
import com.itheima.pojo.Result;
import com.itheima.pojo.RstUserCollection;
import com.itheima.service.RstUserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RstUserCollectionServiceImpl implements RstUserCollectionService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    RstUserCollectionMapper rstUserCollectionMapper;

    @Override
    public void addRstUserCollection(Integer rstuserId, Integer dishId) {
        RstUserCollection rstUserCollection = rstUserCollectionMapper.queryRstUserCollection(rstuserId,dishId);
        if(rstUserCollection == null){
            rstUserCollectionMapper.addRstUserCollection(rstuserId,dishId);
        }else {
            return;
        }
    }

    @Override
    public Result queryRstUserCollection(Integer rstuserId, Integer dishId) {
        RstUserCollection rstUserCollection = rstUserCollectionMapper.queryRstUserCollection(rstuserId,dishId);
        return Result.success(rstUserCollection);
    }

    @Override
    public Result queryRstUserAllCollectionsByRstuserId(Integer rstuserId) {
        List<RstUserCollection> rstUserCollectionList = rstUserCollectionMapper.queryRstUserAllCollectionsByRstuserId(rstuserId);
        return Result.success(rstUserCollectionList);
    }

    @Override
    public Result getRstUserAllCollectionsDishByRstuserId(Integer rstuserId) {
        // 获取用户收藏列表
        List<RstUserCollection> rstUserCollectionList = rstUserCollectionMapper.queryRstUserAllCollectionsByRstuserId(rstuserId);

        // 创建菜品列表
        List<Dish> dishList = new ArrayList<>();

        // 通过每个收藏记录的dishId获取对应菜品信息
        for (RstUserCollection collection : rstUserCollectionList) {
            Dish dish = dishMapper.queryDishById(collection.getDishId());
            if (dish != null) {
                dishList.add(dish);
            }
        }

        System.out.println("---------" + dishList);
        return Result.success(dishList);
    }

    @Override
    public void deleteRstUserCollectionByRstuserId(Integer rstuserId, Integer dishId) {
        rstUserCollectionMapper.deleteRstUserCollectionByRstuserId(rstuserId,dishId);
    }


}
