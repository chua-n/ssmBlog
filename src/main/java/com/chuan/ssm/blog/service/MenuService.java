package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Menu;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:17
 */
public interface MenuService {
    List<Menu> listMenu();

    Menu insertMenu(Menu menu);

    void deleteMenu(Integer id);

    void updateMenu(Menu menu);

    Menu getMenuById(Integer id);
}
