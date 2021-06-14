package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CommentMapperTest {
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void testListRecentComment(){
        List<Comment> comments = commentMapper.listRecentComment(1, 5);
        for (Comment comment: comments){
            System.out.println(comment);
        }
    }
}
