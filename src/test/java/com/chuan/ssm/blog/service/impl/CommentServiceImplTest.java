package com.chuan.ssm.blog.service.impl;

import com.chuan.ssm.blog.entity.Comment;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void testListRecentComment() {
        List<Comment> comments = commentService.listRecentComment(null, 5);
        System.out.println(comments.size());
        for (Comment comment : comments)
            System.out.println(comment);
    }
}