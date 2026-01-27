package com.itheima.mapper;

import com.itheima.pojo.DishComments;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishCommentsMapper {

    @Insert("insert into dishcomments (rstuserId, dishId, userComment) values (#{rstuserId}, #{dishId}, #{userComment});")
    void addDishComments(Integer rstuserId, Integer dishId, String userComment);

    @Select("select rstuserId, dishId, userComment from dishcomments where rstuserId = #{rstuserId}")
    List<DishComments> queryDishCommentsByRstuserId(Integer rstuserId);

    @Delete("delete from dishcomments where rstuserId = #{rstuserId} and dishId = #{dishId} and userComment = #{userComment}")
    void deleteDishComments(Integer rstuserId, Integer dishId, String userComment);

    //暂时没有接口
    @Select("select rstuserId, dishId, userComment from dishcomments where dishId = #{dishId}")
    List<DishComments> queryDishCommentsByDishId(Integer dishId);

    void deleteDishCommentsByRstuserIds(List<Integer> rstuserIds);
}
