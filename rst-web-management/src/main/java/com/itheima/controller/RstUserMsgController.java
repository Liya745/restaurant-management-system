package com.itheima.controller;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Result;
import com.itheima.service.RstUserMsgService;
import com.itheima.service.RstUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RstUserMsgController {

    @Autowired
    private RstUserMsgService rstUserMsgService;
    @PostMapping("/queryRstUserMsgBalanceById")
    public Result queryRstUserMsgBalanceById(@RequestParam Integer id){
        Result result = rstUserMsgService.queryRstUserMsgBalanceById(id);
        return Result.success(result.getData());
    }

    @PostMapping("/updateRstUserMsgBalanceById")
    public Result updateRstUserMsgBalanceById(@RequestParam Integer id, @RequestParam double balance){
        rstUserMsgService.updateRstUserMsgBalanceById(id,balance);
        return Result.success();
    }

    @LogOperation
    @PostMapping("/RstUserRechargeBalance")
    public Result RstUserRechargeBalance(@RequestParam Integer id, @RequestParam double rechargeAmount){
        rstUserMsgService.RstUserRechargeBalance(id,rechargeAmount);
        return Result.success();
    }
    @PostMapping("/addRstUserMsgById")
    public Result addRstUserMsgById(@RequestParam String username){
        rstUserMsgService.addRstUserMsgById(username);
        return Result.success();
    }
}
