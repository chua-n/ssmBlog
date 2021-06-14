package com.chuan.ssm.blog.controller.admin;

import cn.hutool.json.JSONObject;
import com.chuan.ssm.blog.dto.JsonResult;
import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.entity.Comment;
import com.chuan.ssm.blog.entity.User;
import com.chuan.ssm.blog.enums.UserRole;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.CommentService;
import com.chuan.ssm.blog.service.UserService;
import com.chuan.ssm.blog.util.IpAddrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chuan
 * @date 2021/4/20 9:08
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    /**
     * 后台首页。
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/admin")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer userId = null;
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
            // 用户查询自己的文章，管理员查询所有的
            userId = user.getUserId();
        }
        // 文章列表
        List<Article> articleList = articleService.listRecentArticle(userId, 5);
        model.addAttribute("articleList", articleList);
        // 评论列表
        List<Comment> commentList = commentService.listRecentComment(userId, 5);
        model.addAttribute("commentList", commentList);
        return "admin/index";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping("admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping("/register")
    public String registerPage() {
        return "admin/register";
    }

    /**
     * 登录验证。
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginVerify", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    // public Map<String, Object> loginVerify(HttpServletRequest request, HttpServletResponse response) {
    public String loginVerify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        User user = userService.getUserByNameOrEmail(username);
        if (user == null) {
            map.put("code", 0);
            map.put("msg", "用户名无效！");
        } else if (!user.getUserPass().equals(password)) {
            map.put("code", 0);
            map.put("msg", "密码错误！");
        } else {
            // 登录成功
            map.put("code", 1);
            map.put("msg", "");
            // 添加session
            request.getSession().setAttribute("user", user);
            // 添加cookie
            if (rememberMe != null) {
                // 创建两个Cookie对象
                Cookie nameCookie = new Cookie("username", username);
                Cookie pwdCookie = new Cookie("password", password);
                // 设置Cookie的有效期为3天
                nameCookie.setMaxAge(60 * 60 * 24 * 3);
                pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
            }
            user.setUserLastLoginTime(new Date());
            user.setUserLastLoginIp(IpAddrUtil.getIpAddr(request));
            userService.updateUser(user);
        }
        // return map;
        String result = new JSONObject(map).toString();
        return result;
    }

    /**
     * 登录验证。
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult registerSubmit(HttpServletRequest request) {
        String username = request.getParameter("username"), nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User checkUsername = userService.getUserByName(username);
        if (checkUsername != null) {
            return new JsonResult().fail("用户名已存在");
        }
        User checkEmail = userService.getUserByEmail(username);
        if (checkEmail != null) {
            return new JsonResult().fail("电子邮箱已存在");
        }
        // 添加用户
        User user = new User();
        user.setUserAvatar("/img/avatar/avatar.png");
        user.setUserName(username);
        user.setUserNickname(nickname);
        user.setUserPass(password);
        user.setUserEmail(email);
        user.setUserStatus(1);
        user.setArticleCount(0);
        user.setUserRole(UserRole.USER.getValue());
        try {
            userService.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail("系统异常");
        }
        return new JsonResult().ok("注册成功");
    }

    /**
     * 基本信息页面展示。
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/profile")
    public ModelAndView userProfileView(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserById(sessionUser.getUserId());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/user/profile");
        return modelAndView;
    }

    /**
     * 编辑个人信息页面展示。
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/profile/edit")
    public ModelAndView editUserView(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User loginUser = (User) session.getAttribute("user");
        User user = userService.getUserById(loginUser.getUserId());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/user/editProfile");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/profile/save", method = RequestMethod.POST)
    public String saveProfile(User user, HttpSession session) {
        User dbUser = (User) session.getAttribute("user");
        user.setUserId(dbUser.getUserId());
        userService.updateUser(user);
        return "redirect:/admin/profile";
    }
}
