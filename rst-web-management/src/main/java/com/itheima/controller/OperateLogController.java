package com.itheima.controller;

import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 查询所有操作日志
     */
    @GetMapping("/all")
    public Result getAllLogs() {
        try {
            List<OperateLog> logs = operateLogService.findAll();
            return Result.success(logs);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据用户ID查询操作日志
     */
    @GetMapping("/user/{rstuserId}")
    public Result getLogsByUserId(@PathVariable Integer rstuserId) {
        try {
            List<OperateLog> logs = operateLogService.findByRstuserId(rstuserId);
            return Result.success(logs);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询操作日志
     */
    @GetMapping("/page")
    public Result getLogsByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            PageResult<OperateLog> pageResult = operateLogService.findByPage(page, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据用户ID分页查询操作日志
     */
    @GetMapping("/user/{rstuserId}/page")
    public Result getLogsByUserIdAndPage(
            @PathVariable Integer rstuserId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            PageResult<OperateLog> pageResult = operateLogService.findByRstuserIdAndPage(rstuserId, page, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
}