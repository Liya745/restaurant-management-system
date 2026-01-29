package com.itheima.service;

import com.itheima.pojo.*;

import java.util.List;

public interface RstUserService {
    List<RstUser> findAll();


    /**
     * 根据id删除RstUser
     * @param id
     */
    void deleteRstUserById(Integer id);


    /**
     * 新增用户
     * @param rstUser
     */
    void addRstUser(RstUser rstUser);

    /**
     * 根据ID查询用户（单个）
     * @param id
     * @return
     */
    RstUser queryRstUserById(Integer id);

    /**
     * 修改用户
     * @param rstUser
     */
    void updateRstUserById(RstUser rstUser);

    /**
     * 分页查询的方法
     * @param page 页码
     * @param pageSize 每页的记录数
     * @return
     */
    PageResult<RstUser> paginationQueryRstUser(Integer page, Integer pageSize);

    /**
     * 条件查询用户
     * @param rstUserQueryPara
     * @return
     */
    PageResult<RstUser> conditionQueryRstUser(RstUserQueryPara rstUserQueryPara);

    /**
     * 处理用户登陆
     * @param rstUserName
     * @param password
     * @return
     */
    Result RstUserSignIn(String rstUserName, String password);

    /**
     * 测试动态sql语句
     * @param rstUserQueryPara
     * @return
     */
    PageResult<RstUser> dynamicSqlTest(RstUserQueryPara rstUserQueryPara);


    /**
     * 员工登陆
     * @param rstUser
     * @return
     */
    LoginInfo login(RstUser rstUser);
}
