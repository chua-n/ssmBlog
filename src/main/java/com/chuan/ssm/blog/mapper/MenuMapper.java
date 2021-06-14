package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Menu;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:36
 */
public interface MenuMapper {
    int insert(Menu menu);

    int update(Menu menu);

    int deleteById(int menuId);

    Menu getMenuById(int menuId);

    List<Menu> listMenu();
}
