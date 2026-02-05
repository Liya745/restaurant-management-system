package com.itheima.controller;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Result;
import com.itheima.service.RstUserCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RstUserCollectionController {

    @Autowired
    RstUserCollectionService rstUserCollectionService;

    @LogOperation
    @PostMapping("/addRstUserCollection")
    public Result addRstUserCollection(@RequestParam Integer rstuserId, @RequestParam Integer dishId){
        rstUserCollectionService.addRstUserCollection(rstuserId,dishId);
        return Result.success();
    }

    @PostMapping("/queryRstUserCollectionByRstuserIdAndDishId")
    public Result queryRstUserCollection(@RequestParam Integer rstuserId, @RequestParam Integer dishId){
        Result result = rstUserCollectionService.queryRstUserCollection(rstuserId,dishId);
        return Result.success(result.getData());
    }

    @PostMapping("/queryRstUserAllCollectionsByRstuserId")
    public Result queryRstUserAllCollectionsByRstuserId(@RequestParam Integer rstuserId){
        Result result = rstUserCollectionService.queryRstUserAllCollectionsByRstuserId(rstuserId);
        return Result.success(result.getData());
    }

    @PostMapping("/getRstUserAllCollectionsDishByRstuserId")
    public Result getRstUserAllCollectionsDishByRstuserId(@RequestParam Integer rstuserId){
        Result result = rstUserCollectionService.getRstUserAllCollectionsDishByRstuserId(rstuserId);
        return Result.success(result.getData());
    }

    @LogOperation
    @PostMapping("/deleteRstUserCollectionByRstuserId")
    public Result deleteRstUserCollectionByRstuserId(@RequestParam Integer rstuserId,@RequestParam Integer dishId){
        rstUserCollectionService.deleteRstUserCollectionByRstuserId(rstuserId,dishId);
        return Result.success();
    }


}
