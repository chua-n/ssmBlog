package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Link;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:34
 */
public interface LinkMapper {
    int deleteById(int linkId);

    int insert(Link link);

    int update(Link link);

    int countLink(Integer status);

    Link getLinkById(Integer linkId);

    List<Link> listLink(Integer status);
}
