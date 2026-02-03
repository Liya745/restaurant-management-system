package com.itheima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")//拦截所有请求
@Slf4j
public class DemoFilter implements Filter {
    /**
     * 初始化方法，web服务器启动的时候执行，只执行一次
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化运行了");
    }

    //拦截到请求之后，执行，执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到请求");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //销毁的方法，web服务器关闭的时候执行，只执行一次
    @Override
    public void destroy() {
        log.info("destroy销毁运行了");
    }
}
