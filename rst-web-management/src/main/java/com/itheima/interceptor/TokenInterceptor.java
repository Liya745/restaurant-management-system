package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //1. 获取请求路径
        String requestURI = request.getRequestURI();
        //2.判断是否是登陆请求，如果路径中包含/login 说明是登陆操作，放行
        if (requestURI.contains("/login")) {
            log.info("登陆请求，放行");
            return true;
        }
        //3.获取请求头中的token
        String token = request.getHeader("token");
        //4.判断token是否存在，如果不存在，返回错误信息401状态码
        if (token == null || token.isEmpty()) {
            log.info("token不存在，令牌为空，未登录，返回错误信息401状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        //5.如果token存在，校验令牌，如果失败 -》返回错误信息401状态码
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌校验失败，返回错误信息401状态码");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        //6.如果校验通过，放行
        log.info("令牌合法，放行");
        return true;

    }
}
