package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Options;

/**
 * @author chuan
 * @date 2021/4/19 11:39
 */
public interface OptionsMapper {
    int deleteById(int optionId);

    int insert(Options options);

    Options getOptionsById(int optionId);

    int update(Options options);

    Options getOptions();
}
