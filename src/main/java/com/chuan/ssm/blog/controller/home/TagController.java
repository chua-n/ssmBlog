package com.chuan.ssm.blog.controller.home;

import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.entity.Tag;
import com.chuan.ssm.blog.enums.ArticleStatus;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author chuan
 * @date 2021/4/20 21:30
 */
@Controller
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    /**
     * 根据标签查询文章。
     *
     * @param tagId
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/tag/{tagId}")
    public String getArticleListByTag(@PathVariable("tagId") Integer tagId,
                                      @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      Model model) {
        // 该标签信息
        Tag tag = tagService.getTagById(tagId);
        if (tag == null) {
            return "redirect:/404";
        }
        model.addAttribute("tag", tag);

        // 文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("tagId", tagId);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);

        // 侧边栏
        // 标签列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList", allTagList);
        // 获得随机文章
        List<Article> randomArticleList = articleService.listRandomArticle(8);
        model.addAttribute("randomArticleList", randomArticleList);
        // 获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        model.addAttribute("pageUrlPrefix", "/tag?pageIndex");
        return "home/page/articleListByTag";
    }
}
