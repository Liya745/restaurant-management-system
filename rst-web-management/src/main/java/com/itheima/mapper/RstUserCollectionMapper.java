package com.itheima.mapper;


import com.itheima.pojo.RstUserCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RstUserCollectionMapper {

    @Insert("insert into rstusercollection (rstuserId, dishId) values (#{rstuserId}, #{dishId});")
    void addRstUserCollection(Integer rstuserId, Integer dishId);

    @Select("select rstuserId, dishId from rstusercollection where rstuserId = #{rstuserId} and dishId = #{dishId}")
    RstUserCollection queryRstUserCollection(Integer rstuserId, Integer dishId);

    @Select("select rstuserId, dishId from rstusercollection where rstuserId = #{rstuserId}")
    List<RstUserCollection> queryRstUserAllCollectionsByRstuserId(Integer rstuserId);

    @Delete("delete from rstusercollection where rstuserId = #{rstuserId} and dishId = #{dishId}")
    void deleteRstUserCollectionByRstuserId(Integer rstuserId, Integer dishId);
}
