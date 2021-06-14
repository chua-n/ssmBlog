package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:11
 */
public interface CommentService {
    void insertComment(Comment comment);

    List<Comment> listCommentByArticleId(Integer articleId);

    Comment getCommentById(Integer id);

    PageInfo<Comment> listCommentByPage(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria);

    PageInfo<Comment> listReceiveCommentByPage(Integer pageIndex, Integer pageSize, Integer userId);

    void deleteComment(Integer id);

    void updateComment(Comment comment);

    Integer countComment();

    List<Comment> listRecentComment(Integer userId, Integer limit);

    List<Comment> listChildComment(Integer id);
}
