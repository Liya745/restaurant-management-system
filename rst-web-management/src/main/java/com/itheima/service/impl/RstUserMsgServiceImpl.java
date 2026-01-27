package com.itheima.service.impl;

import com.itheima.mapper.RstUserMapper;
import com.itheima.mapper.RstUserMsgMapper;
import com.itheima.pojo.Result;
import com.itheima.pojo.RstUser;
import com.itheima.service.RstUserMsgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RstUserMsgServiceImpl implements RstUserMsgService {

    @Autowired
    RstUserMsgMapper rstUserMsgMapper;
    @Autowired
    private RstUserMapper rstUserMapper;

    @Override
    public Result queryRstUserMsgBalanceById(Integer id) {
        Double balance = rstUserMsgMapper.queryRstUserMsgBalanceById(id);

        return Result.success(balance);
    }

    @Override
    public void updateRstUserMsgBalanceById(Integer id, double balance) {
        rstUserMsgMapper.updateRstUserMsgBalanceById(id,balance);
    }

    @Override
    public void RstUserRechargeBalance(Integer id, double rechargeAmount) {
        Double balance = rstUserMsgMapper.queryRstUserMsgBalanceById(id);
        rstUserMsgMapper.updateRstUserMsgBalanceById(id,balance + rechargeAmount);
    }

    @Override
    public void addRstUserMsgById(String username) {
        System.out.println("----------username：" + username);
        RstUser rstuser = rstUserMapper.queryRstUserByRstUserName(username);
        System.out.println("----------rstuserId：" + rstuser.getId());
        rstUserMsgMapper.addRstUserMsgById(rstuser.getId());
    }

}
