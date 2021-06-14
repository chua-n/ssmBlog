package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Link;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:15
 */
public interface LinkService {
    Integer countLink(Integer status);

    List<Link> listLink(Integer status);

    void insertLink(Link link);

    void deleteLink(Integer id);

    void updateLink(Link link);

    Link getLinkById(Integer id);
}
