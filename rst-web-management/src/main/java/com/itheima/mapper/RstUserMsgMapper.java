package com.itheima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RstUserMsgMapper {

    @Select("select balance from rstusermsg where id = #{id}")
    Double queryRstUserMsgBalanceById(Integer id);

    @Update("update rstusermsg set balance = #{balance} where id = #{id}")
    void updateRstUserMsgBalanceById(Integer id, double balance);

    @Insert("insert into rstusermsg(id, balance) VALUES (#{id},0)")
    void addRstUserMsgById(Integer id);
}
