package com.chuan.ssm.blog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chuan
 * @date 2021/4/20 21:42
 */
//@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这里可以根据session的用户来判断角色的权限，根据权限来转发不同的页面
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        } else {
            return true;
        }
    }
}
