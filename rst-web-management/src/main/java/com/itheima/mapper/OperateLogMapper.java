package com.itheima.mapper;

import com.itheima.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_rstuser_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateRstuserId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

    // 查询所有操作日志
    @Select("SELECT operate_rstuser_id, operate_time, class_name, method_name, method_params, return_value, cost_time FROM operate_log ORDER BY operate_time DESC")
    List<OperateLog> findAll();

    // 根据用户ID查询操作日志
    @Select("SELECT operate_rstuser_id, operate_time, class_name, method_name, method_params, return_value, cost_time FROM operate_log WHERE operate_rstuser_id = #{rstuserId} ORDER BY operate_time DESC")
    List<OperateLog> findByRstuserId(Integer rstuserId);

    // 分页查询操作日志
    @Select("SELECT operate_rstuser_id, operate_time, class_name, method_name, method_params, return_value, cost_time FROM operate_log ORDER BY operate_time DESC LIMIT #{offset}, #{pageSize}")
    List<OperateLog> findByPage(int offset, int pageSize);

    // 查询总记录数
    @Select("SELECT COUNT(*) FROM operate_log")
    int countAll();
}
