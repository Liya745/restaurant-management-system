package com.itheima.mapper;

import com.itheima.pojo.Dish;
import com.itheima.pojo.DishQueryPara;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select id, dishname, dishimage, price, remain, intro from menu")
    List<Dish> getDishes();

    @Delete("delete from menu where id = #{id}")
    void deleteDishById(Integer id);

    @Insert("insert into menu(dishname, dishimage, price, remain, intro) VALUES (#{dishname}, #{dishimage}, #{price}, #{remain}, #{intro})")
    void addDish(Dish dish);

    @Update("update menu set dishname = #{dishname}, price = #{price}, remain = #{remain}, intro = #{intro} where id = #{id}")
    void updateDishById(Dish dish);

    @Select("select id, dishname, dishimage, price, remain, intro from menu where id = #{id}")
    Dish queryDishById(Integer id);

    //在Controller层里暂时没有接口
    @Select("select id, dishname, dishimage, price, remain, intro from menu where dishname = #{dishname}")
    Dish queryDishByDishname(String dishname);


    List<Dish> conditionQueryDish(DishQueryPara dishQueryPara);
}
