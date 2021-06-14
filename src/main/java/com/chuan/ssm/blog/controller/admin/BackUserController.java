package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.entity.User;
import com.chuan.ssm.blog.enums.UserRole;
import com.chuan.ssm.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chuan
 * @date 2021/4/20 17:41
 */
@Controller
@RequestMapping("/admin/user")
public class BackUserController {
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public ModelAndView userList() {
        ModelAndView modelAndView = new ModelAndView();

        List<User> userList = userService.listUser();
        modelAndView.addObject("userList", userList);

        modelAndView.setViewName("admin/user/index");
        return modelAndView;
    }

    @RequestMapping("/insert")
    public ModelAndView insertUserView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/user/insert");
        return modelAndView;
    }

    @RequestMapping(value = "/checkUserName", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> checkUserName(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String username = request.getParameter("username");
        User user = userService.getUserByName(username);
        int id = Integer.valueOf(request.getParameter("id"));
        // 用户名已存在，但不是当前用户（编辑用户的时候，不提示）
        if (user != null) {
            if (user.getUserId() != id) {
                map.put("code", 1);
                map.put("msg", "用户名已存在！");
            }
        } else {
            map.put("code", 0);
            map.put("msg", 1);
        }
        return map;
    }

    @RequestMapping(value = "/checkUserEmail", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> checkUserEmail(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String email = request.getParameter("email");
        User user = userService.getUserByEmail(email);
        int id = Integer.valueOf(request.getParameter("id"));
        // 用户名已存在，但不是当前用户（编辑用户的时候，不提示）
        if (user != null) {
            if (user.getUserId() != id) {
                map.put("code", 1);
                map.put("msg", "电子邮箱已存在");
            }
        } else {
            map.put("code", 0);
            map.put("msg", "");
        }
        return map;
    }

    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertUserSubmit(User user) {
        User user1 = userService.getUserByName(user.getUserName());
        User user2 = userService.getUserByEmail(user.getUserEmail());
        if (user1 == null && user2 == null) {
            user.setUserRegisterTime(new Date());
            user.setUserStatus(1);
            user.setUserRole(UserRole.USER.getValue());
            userService.insertUser(user);
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editUserView(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getUserById(id);
        modelAndView.addObject("user", user);

        modelAndView.setViewName("admin/user/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editUserSubmit(User user) {
        userService.updateUser(user);
        return "redirect:/admin/user";
    }
}
