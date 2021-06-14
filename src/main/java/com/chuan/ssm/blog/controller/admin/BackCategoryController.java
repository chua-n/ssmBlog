package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.entity.Category;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/20 15:44
 */
@Controller
@RequestMapping("/admin/category")
public class BackCategoryController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 后台分类列表展示。
     *
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView categoryList() {
        // 试试把ModelAndView作为方法参数让SpringMVC自动注入？？？
        ModelAndView modelAndView = new ModelAndView();
        List<Category> categoryList = categoryService.listCategoryWithCount();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("admin/category/index");
        return modelAndView;
    }

    /**
     * 后台添加分类提交。
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertCategorySubmit(Category category) {
        categoryService.insertCategory(category);
        return "redirect:/admin/category";
    }

    /**
     * 删除分类。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        // 禁止删除所有文章的分类
        int count = articleService.countArticleByCategoryId(id);
        if (count == 0) {
            categoryService.deleteCategory(id);
        }
        return "redirect:/admin/category";
    }

    /**
     * 编辑分类页面显示。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editCategoryView(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Category category = categoryService.getCategoryById(id);
        modelAndView.addObject("category", category);

        List<Category> categoryList = categoryService.listCategoryWithCount();
        modelAndView.addObject("categoryList", categoryList);

        modelAndView.setViewName("admin/category/edit");
        return modelAndView;
    }

    /**
     * 编辑分类提交。
     *
     * @param category
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editCategorySubmit(Category category) {
        categoryService.updateCategory(category);
        return "redirect:/admin/category";
    }
}
