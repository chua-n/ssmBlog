package com.chuan.ssm.blog.mapper;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ArticleCategoryRefMapperTest {
    @Autowired
    private ArticleCategoryRefMapper mapper;

    public void testInsert(){

    }
}
