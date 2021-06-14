package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:27
 */
public interface CommentMapper {
    int insert(Comment comment);

    int update(Comment comment);

    int deleteById(int commentId);

    int deleteByUserId(int userId);

    int deleteByArticleId(int articleId);

    Integer countComment();

    Comment getCommentById(int commentId);

    List<Comment> listCommentByArticleId(int id);

    List<Comment> listComment(HashMap<String, Object> criteria);

    List<Comment> getReceiveComment(List<Integer> articleIds);

    List<Comment> listRecentComment(@Param("userId") Integer userId,
                                    @Param("limit") Integer limit);

    List<Comment> listChildComment(int id);
}
