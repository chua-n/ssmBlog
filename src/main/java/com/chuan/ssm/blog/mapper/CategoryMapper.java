package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Category;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:24
 */
public interface CategoryMapper {
    int insert(Category category);

    int update(Category category);

    int deleteCategory(int id);

    int countCategory();

    Category getCategoryById(Integer id);

    List<Category> listCategory();

    List<Category> findChildCategory(int id);

    Category getCategoryByName(String name);
}
