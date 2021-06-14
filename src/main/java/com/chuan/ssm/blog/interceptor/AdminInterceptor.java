package com.chuan.ssm.blog.interceptor;

import com.chuan.ssm.blog.entity.User;
import com.chuan.ssm.blog.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author chuan
 * @date 2021/4/20 21:32
 */
//@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这里可以根据session的用户来判断角色的权限，根据权限来转发不同的页面
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        } else {
            return Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue());
        }
    }
}
