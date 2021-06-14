package com.chuan.ssm.blog.service.impl;


import com.chuan.ssm.blog.entity.Page;
import com.chuan.ssm.blog.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PageServiceImplTest {

    @Autowired
    private PageService pageService;

    @Test
    public void testListPage() {
        List<Page> pages = pageService.listPage(1);
        System.out.println(pages);
    }

    public void testGetPageByKey() {
    }

    public void testGetPageById() {
    }

    public void testInsertPage() {
    }

    public void testDeletePage() {
    }

    public void testUpdatePage() {
    }
}