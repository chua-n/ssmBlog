package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Page;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:22
 */
public interface PageService {
    List<Page> listPage(Integer status);

    Page getPageByKey(Integer status, String key);

    Page getPageById(Integer id);

    void insertPage(Page page);

    void deletePage(Integer id);

    void updatePage(Page page);
}
