package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.entity.ArticleCategoryRef;
import com.chuan.ssm.blog.entity.Category;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 10:03
 */
public interface ArticleCategoryRefMapper {
    int insert(ArticleCategoryRef record);

    int deleteByCategoryId(Integer categoryId);

    int deleteByArticleId(int articleId);

    int countArticleByCategoryId(int categoryId);

    List<Integer> selectCategoryIdByArticleId(Integer articleId);

    List<Integer> selectArticleIdByCategoryId(Integer categoryId);

    List<Category> listCategoryByArticleId(Integer articleId);
}
