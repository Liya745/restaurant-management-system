package com.itheima.controller;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.RstUser;
import com.itheima.pojo.RstUserQueryPara;
import com.itheima.service.RstUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RstUserController {

    @Autowired
    private RstUserService rstUserService;
    @RequestMapping("/RstUsers")
    public Result list(){
        System.out.println("查询全部用户数据...");
        List<RstUser> rstUserList = rstUserService.findAll();
        return Result.success(rstUserList);
    }

    /**
     * 删除部门，可以省略@RequestParam(前端传递的请求参数名与服务端方法形参名一致时可以省略)
     * @param id
     * @return
     */
    @LogOperation
    @DeleteMapping("/deleteRstUserById")
    public Result delete(@RequestParam(value = "id",required = false) Integer id){
        System.out.println("根据id删除用户会员：" + id);
        rstUserService.deleteRstUserById(id);
        return Result.success();
    }

    @LogOperation
    @PostMapping("/addRstUser")
    public Result addRstUser(@RequestBody RstUser rstUser){
        System.out.println("新增用户：" + rstUser);
        rstUserService.addRstUser(rstUser);
        return Result.success();
    }

    @PostMapping("/queryRstUserById")
    public Result queryRstUserById(@RequestParam("id") Integer id){
        System.out.println("根据ID查询用户：" + id);
        RstUser rstUser =  rstUserService.queryRstUserById(id);
        return Result.success(rstUser);
    }

    @LogOperation
    @PostMapping("/updateRstUserById")
    public Result updateRstUserById(@RequestBody RstUser rstUser){
        System.out.println("修改用户：" + rstUser);
        rstUserService.updateRstUserById(rstUser);
        return Result.success();
    }

    /**
     * 分页查询用户表
     * @return
     */
    @PostMapping("/paginationQueryRstUser")
    public Result paginationQueryRstUser(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5") Integer pageSize){
        PageResult<RstUser> pageResult = rstUserService.paginationQueryRstUser(page,pageSize);
        return Result.success(pageResult);
    }

    /**
     * 条件查询用户表
     */
    @PostMapping("/conditionQueryRstUser")
    public Result conditionQueryRstUser(@RequestBody RstUserQueryPara rstUserQueryPara){
        PageResult<RstUser> pageResult = rstUserService.conditionQueryRstUser(rstUserQueryPara);
        return Result.success(pageResult);
    }

    @PostMapping("/RstUserSignIn")
    public Result RstUserSignIn(@RequestParam String rstUserName, @RequestParam String password){
        Result result = rstUserService.RstUserSignIn(rstUserName, password);
        return result;
    }

    /**
     * 测试动态sql语句
     */
    @PostMapping("/dynamicSqlTest")
    public Result dynamicSqlTest(@RequestBody RstUserQueryPara rstUserQueryPara){
        PageResult<RstUser> pageResult = rstUserService.dynamicSqlTest(rstUserQueryPara);
        return Result.success(pageResult);
    }
}
