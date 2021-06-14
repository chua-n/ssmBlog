package com.chuan.ssm.blog.controller.admin;

import com.chuan.ssm.blog.entity.Tag;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/20 17:34
 */
@Controller
@RequestMapping("/admin/tag")
public class BackTagController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        List<Tag> tagList = tagService.listTagWithCount();
        modelAndView.addObject("tagList", tagList);

        modelAndView.setViewName("admin/tag/index");
        return modelAndView;
    }

    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertTagSubmit(Tag tag) {
        tagService.insertTag(tag);
        return "redirect:/admin/tag";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTag(@PathVariable("id") Integer id) {
        Integer count = articleService.countArticleByTagId(id);
        if (count == 0) {
            tagService.deleteTag(id);
        }
        return "redirect:/admin/tag";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editTagView(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Tag tag = tagService.getTagById(id);
        modelAndView.addObject("tag", tag);

        List<Tag> tagList = tagService.listTagWithCount();
        modelAndView.addObject("tagList", tagList);

        modelAndView.setViewName("admin/tag/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editTagSubmit(Tag tag) {
        tagService.updateTag(tag);
        return "redirect:/admin/tag";
    }
}
