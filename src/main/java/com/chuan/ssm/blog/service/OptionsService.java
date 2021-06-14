package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Options;

/**
 * @author chuan
 * @date 2021/4/19 19:20
 */
public interface OptionsService {
    Options getOptions();

    void insertOptions(Options options);

    void updateOptions(Options options);
}
