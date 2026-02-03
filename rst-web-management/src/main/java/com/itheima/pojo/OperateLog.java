package com.itheima.pojo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperateLog {
    private Integer id; // ID
    private Integer operateRstuserId; // 操作人ID
    private LocalDateTime operateTime; // 操作时间
    private String className; // 类名
    private String methodName; // 方法名
    private String methodParams; // 方法参数
    private String returnValue; // 返回值
    private Long costTime; // 耗时
}
