package com.itheima.mapper;

import com.itheima.pojo.Result;
import com.itheima.pojo.RstUser;
import com.itheima.pojo.RstUserQueryPara;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface RstUserMapper {
    @Select("select id, username, age, gender, phone, password, avatar, level, signInCount from RstUser")
    List<RstUser> findAll();

    @Delete("delete from rstuser where id = #{id}")
    void deleteRstUserById(Integer id);

    @Insert("insert into rstuser(username, age, gender, phone, password, avatar, level, signInCount) values (#{username},#{age},#{gender},#{phone},#{password},#{avatar},#{level},#{signInCount}) ")
    void addRstUser(RstUser rstUser);

    @Select("select id, username, age, gender, phone, password, avatar, level, signInCount from rstuser where id = #{id}")
    RstUser queryRstUserById(Integer id);

    @Update("update rstuser set username = #{username}, phone = #{phone}, avatar = #{avatar} where id = #{id}")
    void updateRstUserById(RstUser rstUser);

    /**
     * 查询总记录数
     * @return
     */
    @Select("select count(*) from rstuser")
    public Long count();

    @Select("select id, username, age, gender, phone, password, avatar, level, signInCount from rstuser limit #{start},#{pageSize}")
    public List<RstUser> paginationQueryRstUser(Integer start, Integer pageSize);

    List<RstUser> conditionQueryRstUser(RstUserQueryPara rstUserQueryPara);

    List<RstUser> conditionQueryRstUserGenderIsNull(RstUserQueryPara rstUserQueryPara);

    List<RstUser> conditionQueryRstUserPhoneIsNull(RstUserQueryPara rstUserQueryPara);

    List<RstUser> conditionQueryRstUserGenderAndPhoneAreNull(RstUserQueryPara rstUserQueryPara);

    RstUser queryRstUserByRstUserName(String rstUserName);

    List<RstUser> dynamicSqlTest(RstUserQueryPara rstUserQueryPara);

    /**
     * 根据用户名和密码查询员工信息
     * @param rstUser
     * @return
     */
    @Select("select id, username from rstuser where username = #{username} and password = #{password}")
    RstUser queryRstUserByUsernameAndPassword(RstUser rstUser);
}
