package com.itheima.controller;

import com.itheima.mapper.DishMapper;
import com.itheima.pojo.DishComments;
import com.itheima.pojo.MyComments;
import com.itheima.pojo.OneDishComments;
import com.itheima.pojo.Result;
import com.itheima.service.DishCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
public class DishCommentsCotroller {

    @Autowired
    DishCommentsService dishCommentsService;
    @Autowired
    private DishMapper dishMapper;

    @PostMapping("/addDishComments")
    public Result addDishComments(@RequestParam Integer rstuserId, @RequestParam Integer dishId, @RequestParam String userComment){
        dishCommentsService.addDishComments(rstuserId,dishId,userComment);
        return Result.success();
    }

    @PostMapping("/queryDishCommentsByRstuserId")
    public Result queryDishCommentsByRstuserId(@RequestParam Integer rstuserId){
        List<DishComments> dishCommentsList = dishCommentsService.queryDishCommentsByRstuserId(rstuserId);
        return Result.success(dishCommentsList);
    }

    @PostMapping("/getDishCommentsByRstuserId")
    public Result getDishCommentsByRstuserId(@RequestParam Integer rstuserId){
        List<MyComments> myCommentsList = dishCommentsService.getDishCommentsByRstuserId(rstuserId);
        return Result.success(myCommentsList);
    }

    /**
     * 开发初期不知道为什么脑抽了，这个接口一点也不好用，准备再弄一个接口
     * @param rstuserId
     * @param dishname
     * @param userComment
     * @return
     */
    @PostMapping("/deleteDishComments")
    public Result deleteDishComments(@RequestParam Integer rstuserId,@RequestParam String dishname, @RequestParam String userComment){
        dishCommentsService.deleteDishComments(rstuserId,dishname,userComment);
        return Result.success();
    }

    @PostMapping("/deleteDishCommentsByRstuserIds")
    public Result deleteDishCommentsByRstuserIds(@RequestParam List<Integer> rstuserIds){
        log.info("要删除的ids为：rstuserIds = {}", rstuserIds);
        dishCommentsService.deleteDishCommentsByRstuserIds(rstuserIds);
        return Result.success();
    }

    @PostMapping("/getDishCommentsByDishId")
    public Result getDishCommentsByDishId(@RequestParam Integer dishId){
        List<OneDishComments> oneDishCommentsList = dishCommentsService.getDishCommentsByDishId(dishId);
        return Result.success(oneDishCommentsList);
    }
}
