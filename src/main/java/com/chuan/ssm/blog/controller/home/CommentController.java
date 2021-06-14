package com.chuan.ssm.blog.controller.home;

import cn.hutool.http.HtmlUtil;
import com.chuan.ssm.blog.dto.JsonResult;
import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.entity.Comment;
import com.chuan.ssm.blog.entity.User;
import com.chuan.ssm.blog.enums.ArticleStatus;
import com.chuan.ssm.blog.enums.Role;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.CommentService;
import com.chuan.ssm.blog.util.Gravatar;
import com.chuan.ssm.blog.util.IpAddrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @author chuan
 * @date 2021/4/20 21:02
 */
@Controller
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/comment", method = {RequestMethod.POST})
    public JsonResult insertComment(HttpServletRequest request, Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new JsonResult().fail("请先登录");
        }
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
        if (article == null) {
            return new JsonResult().fail("文章不存在");
        }

        // 添加评论
        comment.setCommentUserId(user.getUserId());
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(IpAddrUtil.getIpAddr(request));
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(Role.OWNER.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }
        comment.setCommentAuthorAvatar(Gravatar.getGravatar(comment.getCommentAuthorEmail()));

        // 过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());
        try {
            commentService.insertComment(comment);
            // 更新文章的评论数
            articleService.updateCommentCount(article.getArticleId());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail();
        }
        return new JsonResult().ok();
    }
}
