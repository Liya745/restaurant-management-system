package com.itheima.service;

import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface OperateLogService {
    /**
     * 查询所有操作日志
     */
    List<OperateLog> findAll();

    /**
     * 根据用户ID查询操作日志
     */
    List<OperateLog> findByRstuserId(Integer rstuserId);

    /**
     * 分页查询操作日志
     */
    PageResult<OperateLog> findByPage(int page, int pageSize);

    /**
     * 根据用户ID分页查询操作日志
     */
    PageResult<OperateLog> findByRstuserIdAndPage(int rstuserId, int page, int pageSize);
}