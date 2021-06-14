package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Tag;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:24
 */
public interface TagService {
    Integer countTag();

    List<Tag> listTag();

    List<Tag> listTagWithCount();

    Tag getTagById(Integer id);

    Tag insertTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTag(Integer id);

    Tag getTagByName(String name);

    List<Tag> listTagByArticleId(Integer articleId);
}
