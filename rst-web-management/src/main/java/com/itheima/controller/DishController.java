package com.itheima.controller;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Dish;
import com.itheima.pojo.DishQueryPara;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.DishService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping("/getDishes")
    public Result getDishes(){
        List<Dish> dishList = dishService.getDishes();
        return Result.success(dishList);
    }

    @LogOperation
    @PostMapping("/addDish")
    public Result addDish(@RequestBody Dish dish){
        dishService.addDish(dish);
        return Result.success();
    }

    @LogOperation
    @PostMapping("/deleteDishById")
    public Result deleteDishById(@RequestParam(value = "id", required = true) Integer id){
        System.out.println("根据ID删除菜品：" +id);
        dishService.deleteDishById(id);
        return Result.success();
    }

    @LogOperation
    @PostMapping("/updateDishById")
    public Result updateDishById(@RequestBody Dish dish){
        System.out.println("根据ID更改菜品：");
        dishService.updateDishById(dish);
        return Result.success();
    }

    @PostMapping("/queryDishById")
    public Result queryDishById(@Param("id") Integer id){
        System.out.println("根据ID查询菜品信息");
        Dish dish = dishService.queryDishById(id);
        return Result.success(dish);
    }

    /**
     * 分页查询菜品表
     */
    @PostMapping("/paginationQueryDishes")
    public Result paginationQueryDishes(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageResult<Dish> pageResult = dishService.paginationQueryDishes(page,pageSize);
        return Result.success(pageResult);
    }

    /**
     * 条件查询菜品
     * @param dishQueryPara
     * @return
     */
    @PostMapping("/conditionQueryDish")
    public Result conditionQueryDish(@RequestBody DishQueryPara dishQueryPara){
        PageResult<Dish> pageResult = dishService.conditionQueryDish(dishQueryPara);
        return Result.success(pageResult);
    }

}
