package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.RstUserMapper;
import com.itheima.pojo.*;
import com.itheima.service.RstUserService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class RstUserServiceImpl implements RstUserService {


    @Autowired
    private RstUserMapper rstUserMapper;

    @Override
    public List<RstUser> findAll() {
        return rstUserMapper.findAll();
    }

    @Override
    public void deleteRstUserById(Integer id) {
        rstUserMapper.deleteRstUserById(id);
    }

    @Override
    public void addRstUser(RstUser rstUser) {
        rstUserMapper.addRstUser(rstUser);
    }

    @Override
    public RstUser queryRstUserById(Integer id) {
        return rstUserMapper.queryRstUserById(id);
    }

    @Override
    public void updateRstUserById(RstUser rstUser) {
        rstUserMapper.updateRstUserById(rstUser);
    }

    /**
     * 基于PageHelper分页查询
     * @param page 页码
     * @param pageSize 每页的记录数
     * @return
     * 注意事项：
     *      1.定义的sql语句不能加分号
     *      2.PageHelper仅仅能对紧跟其后的第一个查询语句进行分页处理
     */
    @Override
    public PageResult<RstUser> paginationQueryRstUser(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<RstUser> rstUserList = rstUserMapper.findAll();
        Page<RstUser> p = (Page<RstUser>) rstUserList;
        return new PageResult<RstUser>(p.getTotal(),p.getResult());
    }

    /**
     * 条件查询用户表
     * @param rstUserQueryPara
     * @return
     */
    @Override
    public PageResult<RstUser> conditionQueryRstUser(RstUserQueryPara rstUserQueryPara) {
        if(rstUserQueryPara.getGender() == 0 && rstUserQueryPara.getPhone() == ""){
            System.out.println("------gender和phone为空------");
            PageHelper.startPage(rstUserQueryPara.getPage(), rstUserQueryPara.getPageSize());
            List<RstUser> rstUserList = rstUserMapper.conditionQueryRstUserGenderAndPhoneAreNull(rstUserQueryPara);
            Page<RstUser> p = (Page<RstUser>) rstUserList;
            return new PageResult<RstUser>(p.getTotal(),p.getResult());
        }else if (rstUserQueryPara.getPhone() == ""){
            System.out.println("------phone为空------");
            PageHelper.startPage(rstUserQueryPara.getPage(), rstUserQueryPara.getPageSize());
            List<RstUser> rstUserList = rstUserMapper.conditionQueryRstUserPhoneIsNull(rstUserQueryPara);
            Page<RstUser> p = (Page<RstUser>) rstUserList;
            return new PageResult<RstUser>(p.getTotal(),p.getResult());
        }else if (rstUserQueryPara.getGender() == 0){
            System.out.println("------gender为空------");
            PageHelper.startPage(rstUserQueryPara.getPage(), rstUserQueryPara.getPageSize());
            List<RstUser> rstUserList = rstUserMapper.conditionQueryRstUserGenderIsNull(rstUserQueryPara);
            Page<RstUser> p = (Page<RstUser>) rstUserList;
            return new PageResult<RstUser>(p.getTotal(),p.getResult());
        }else {
            PageHelper.startPage(rstUserQueryPara.getPage(), rstUserQueryPara.getPageSize());
            List<RstUser> rstUserList = rstUserMapper.conditionQueryRstUser(rstUserQueryPara);
            Page<RstUser> p = (Page<RstUser>) rstUserList;
            return new PageResult<RstUser>(p.getTotal(),p.getResult());
        }
    }

    @Override
    public Result RstUserSignIn(String rstUserName, String password) {
        RstUser rstUser = rstUserMapper.queryRstUserByRstUserName(rstUserName);
        Result result = new Result();
        if(rstUser == null){
            return Result.error("此用户不存在");
        }else if(!Objects.equals(password, rstUser.getPassword())){
            return Result.error("密码错误");
        }else {
            return Result.success(rstUser);
        }

    }

    @Override
    public PageResult<RstUser> dynamicSqlTest(RstUserQueryPara rstUserQueryPara) {
        PageHelper.startPage(rstUserQueryPara.getPage(),rstUserQueryPara.getPageSize());
        List<RstUser> rstUserList = rstUserMapper.dynamicSqlTest(rstUserQueryPara);
        Page<RstUser> p = (Page<RstUser>)rstUserList;
        return new PageResult<RstUser>(p.getTotal(),p.getResult());
    }

    @Override
    public LoginInfo login(RstUser rstUser) {
        //调用mapper接口，查询员工
        RstUser user = rstUserMapper.queryRstUserByUsernameAndPassword(rstUser);
        //判断是否存在用户，如果存在，组装成功信息
        if (user != null) {
            log.info("登陆成功，员工信息: {}" , user);

            //生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());

            String token = JwtUtils.generateToken(claims);
            //存在，组装成功信息
            return new LoginInfo(user.getId(), user.getUsername(), token);
        }
        //不存在返回null
        return null;
    }
}


