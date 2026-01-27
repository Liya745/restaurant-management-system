package com.itheima.service;

import com.itheima.pojo.Result;

public interface RstUserCollectionService {

    void addRstUserCollection(Integer rstuserId, Integer dishId);

    Result queryRstUserCollection(Integer rstuserId, Integer dishId);

    Result queryRstUserAllCollectionsByRstuserId(Integer rstuserId);

    Result getRstUserAllCollectionsDishByRstuserId(Integer rstuserId);

    void deleteRstUserCollectionByRstuserId(Integer rstuserId, Integer dishId);
}
