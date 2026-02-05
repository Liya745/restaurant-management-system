package com.itheima.service.impl;

import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;
import com.itheima.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements com.itheima.service.OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public List<OperateLog> findAll() {
        return operateLogMapper.findAll();
    }

    @Override
    public List<OperateLog> findByRstuserId(Integer rstuserId) {
        return operateLogMapper.findByRstuserId(rstuserId);
    }

    @Override
    public PageResult<OperateLog> findByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<OperateLog> logs = operateLogMapper.findByPage(offset, pageSize);
        int total = operateLogMapper.countAll();
        return new PageResult<>((long) total, logs);
    }

    @Override
    public PageResult<OperateLog> findByRstuserIdAndPage(int rstuserId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        // 由于原Mapper没有提供按用户ID分页查询的方法，我们先查询所有该用户的日志，再手动分页
        // 更好的做法是添加一个新的Mapper方法，这里为了简化先这样做
        List<OperateLog> allLogs = operateLogMapper.findByRstuserId(rstuserId);
        int total = allLogs.size();

        // 手动分页
        int startIndex = Math.min(offset, total);
        int endIndex = Math.min(startIndex + pageSize, total);

        List<OperateLog> pagedLogs = allLogs.subList(startIndex, endIndex);

        return new PageResult<>((long) total, pagedLogs);
    }
}