package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Tag;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:42
 */
public interface TagMapper {
    int deleteById(int tagId);

    int insert(Tag tag);

    Tag getTagById(int tagId);

    int update(Tag tag);

    int countTag();

    List<Tag> listTag();

    Tag getTagByName(String name);
}
