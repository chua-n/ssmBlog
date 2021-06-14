package com.chuan.ssm.blog.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.entity.Comment;
import com.chuan.ssm.blog.entity.User;
import com.chuan.ssm.blog.enums.ArticleStatus;
import com.chuan.ssm.blog.enums.Role;
import com.chuan.ssm.blog.enums.UserRole;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.CommentService;
import com.chuan.ssm.blog.util.IpAddrUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author chuan
 * @date 2021/4/20 15:57
 */
@Controller
@RequestMapping("/admin/comment")
public class BackCommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    /**
     * 评论页面——我发送的评论。
     *
     * @param pageIndex
     * @param pageSize
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "")
    public String commentList(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              HttpSession session,
                              Model model) {
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> criteria = new HashMap<>();
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
            // 用户查询自己的文章, 管理员查询所有的
            criteria.put("userId", user.getUserId());
        }
        PageInfo<Comment> commentPageInfo = commentService.listCommentByPage(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", commentPageInfo);
        model.addAttribute("pageUrlPrefix", "/admin/comment?pageIndex");
        return "admin/comment/index";
    }

    /**
     * 评论页面——我收到的评论。
     *
     * @param pageIndex
     * @param pageSize
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/receive")
    public String myReceiveComment(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   HttpSession session,
                                   Model model) {
        User user = (User) session.getAttribute("user");
        PageInfo<Comment> commentPageInfo = commentService.listReceiveCommentByPage(pageIndex, pageSize, user.getUserId());
        model.addAttribute("pageInfo", commentPageInfo);
        model.addAttribute("pageUrlPrefix", "/admin/comment?pageIndex");
        return "admin/comment/index";
    }

    /**
     * 添加评论。
     *
     * @param request
     * @param comment
     * @param session
     */
    @RequestMapping(value = "/insert", method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    //@ResponseBody
    public void insertComment(HttpServletRequest request, Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        if (article == null) {
            return;
        }

        // 添加评论
        comment.setCommentUserId(user.getUserId());
        comment.setCommentIp(IpAddrUtil.getIpAddr(request));
        comment.setCommentCreateTime(new Date());
        commentService.insertComment(comment);
        // 更新文章的评论数
        articleService.updateCommentCount(article.getArticleId());
    }

    /**
     * 删除评论。
     *
     * @param id
     * @param session
     */
    @RequestMapping(value = "/delete/{id}")
    public void deleteComment(@PathVariable("id") Integer id, HttpSession session) {
        Comment comment = commentService.getCommentById(id);
        User user = (User) session.getAttribute("user");

        // 如果不是管理员，访问其他用户的数据，没有权限
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue()) && !Objects.equals(comment.getCommentUserId(), user.getUserId())) {
            return;
        }

        //删除评论
        commentService.deleteComment(id);

        //删除其子评论
        List<Comment> childCommentList = commentService.listChildComment(id);
        for (int i = 0; i < childCommentList.size(); ++i) {
            commentService.deleteComment(childCommentList.get(i).getCommentId());
        }

        //更新文章的评论数
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        articleService.updateCommentCount(article.getArticleId());
    }

    /**
     * 编辑评论页面显示。
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String editCommentView(@PathVariable("id") Integer id, Model model, HttpSession session) {
        // 没有权限操作,只有管理员可以操作
        User user = (User) session.getAttribute("user");
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "admin/comment/edit";
    }

    /**
     * 编辑评论提交。
     *
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editCommentSubmit(Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // 没有权限操作,只有管理员可以操作
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        commentService.updateComment(comment);
        return "redirect:/admin/comment";
    }

    /**
     * 回复评论页面显示。
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/reply/{id}")
    public String replyCommentView(@PathVariable("id") Integer id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "admin/comment/reply";
    }

    /**
     * 回复评论提交。
     *
     * @param request
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping(value = "/replySubmit", method = RequestMethod.POST)
    public String replyCommentSubmit(HttpServletRequest request, Comment comment, HttpSession session) {
        // 文章评论数+1
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
        if (article == null) {
            return "redirect:/404";
        }
        User user = (User) session.getAttribute("user");
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);
        articleService.updateArticle(article);

        // 添加评论
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(IpAddrUtil.getIpAddr(request));
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(Role.OWNER.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }
        commentService.insertComment(comment);
        return "redirect:/admin/comment";
    }
}