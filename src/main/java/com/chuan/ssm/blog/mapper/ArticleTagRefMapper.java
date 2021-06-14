package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.ArticleTagRef;
import com.chuan.ssm.blog.entity.Tag;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:21
 */
public interface ArticleTagRefMapper {
    int insert(ArticleTagRef record);

    int deleteByTagId(Integer tagId);

    int deleteByArticleId(Integer articleId);

    int countArticleByTagId(Integer tagId);

    List<Tag> listTagByArticleId(Integer articleId);
}
