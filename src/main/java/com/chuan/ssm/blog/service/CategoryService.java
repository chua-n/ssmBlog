package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Category;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 18:58
 */
public interface CategoryService {
    Integer countCategory();

    List<Category> listCategory();

    List<Category> listCategoryWithCount();

    void deleteCategory(Integer id);

    Category getCategoryById(Integer id);

    Category insertCategory(Category category);

    void updateCategory(Category category);

    Category getCategoryByName(String name);
}
