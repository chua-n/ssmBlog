package com.chuan.ssm.blog.service.impl;

import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.enums.ArticleStatus;

import com.chuan.ssm.blog.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

/**
 * @author chuan
 * @date 2021/4/20 10:39
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void testCountArticle() {
        int count = articleService.countArticle(ArticleStatus.PUBLISH.getValue());
        System.out.println(count);
    }

    @Test
    public void testCountArticleComment() {
        int count = articleService.countArticleComment();
        System.out.println(count);
    }

    @Test
    public void testCountArticleView() {
        int count = articleService.countArticleView();
        System.out.println(count);
    }

    @Test
    public void testCountArticleByCategoryId() {
        int count = articleService.countArticleByCategoryId(1);
        System.out.println(count);
    }

    @Test
    public void testCountArticleByTagId() {
        int count = articleService.countArticleByTagId(1);
        System.out.println(count);
    }

    @Test
    public void testListArticle() {
        HashMap<String, Object> criteria = new HashMap<>();
        criteria.put("status", 1);
        // criteria.put("userId", 1);
        criteria.put("keywords", "docker");
        List<Article> articles = articleService.listArticle(criteria);
        for (Article article : articles)
            System.out.println(article);
        System.out.println(articles.size());
    }

    @Test
    public void testListRecentArticle() {
        List<Article> articles = articleService.listRecentArticle(null, 3);
        System.out.println(articles.size());
        for (Article article : articles)
            System.out.println(article);
    }

    @Test
    public void testUpdateArticleDetail() {

    }
}
