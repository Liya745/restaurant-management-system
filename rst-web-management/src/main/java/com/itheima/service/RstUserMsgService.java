package com.itheima.service;

import com.itheima.pojo.Result;

public interface RstUserMsgService {
    Result queryRstUserMsgBalanceById(Integer id);

    void updateRstUserMsgBalanceById(Integer id, double balance);

    void RstUserRechargeBalance(Integer id, double rechargeAmount);

    void addRstUserMsgById(String username);
}
