package com.chuan.ssm.blog.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.chuan.ssm.blog.dto.ArticleParam;
import com.chuan.ssm.blog.entity.Article;
import com.chuan.ssm.blog.entity.Category;
import com.chuan.ssm.blog.entity.Tag;
import com.chuan.ssm.blog.entity.User;
import com.chuan.ssm.blog.enums.UserRole;
import com.chuan.ssm.blog.service.ArticleService;
import com.chuan.ssm.blog.service.CategoryService;
import com.chuan.ssm.blog.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author chuan
 * @date 2021/4/20 14:05
 */
@Controller
@RequestMapping("admin/article")
public class BackArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 后台文章列表显示。
     *
     * @param pageIndex
     * @param pageSize
     * @param status
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String status,
                        Model model, HttpSession session) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        if (status == null) {
            model.addAttribute("pageUrlPrefix", "/admin/article?pageIndex");
        } else {
            criteria.put("status", status);
            model.addAttribute("pageUrlPrefix", "/admin/article?status=" + status + "&pageIndex");
        }
        User user = (User) session.getAttribute("user");
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
            criteria.put("userId", user.getUserId());
        }
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", articlePageInfo);
        return "admin/article/index";
    }

    /**
     * 后台添加文章页面展示。
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert")
    public String insertArticleView(Model model) {
        List<Category> categoryList = categoryService.listCategory();
        List<Tag> tagList = tagService.listTag();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);
        return "admin/article/insert";
    }

    /**
     * 后台添加文章提交操作。
     *
     * @param session
     * @param articleParam
     * @return
     */
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertArticleSubmit(HttpSession session, ArticleParam articleParam) {
        Article article = new Article();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            article.setArticleUserId(user.getUserId());
        }
        article.setArticleTitle(articleParam.getArticleTitle());

        // 文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(articleParam.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());

        // 填充分类
        List<Category> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);

        // 填充标签
        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); ++i) {
                Tag tag = new Tag(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);

        articleService.insertArticle(article);
        return "redirect:/admin/article";
    }

    /**
     * 删除文章。
     *
     * @param id
     * @param session
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void deleteArticle(@PathVariable("id") Integer id, HttpSession session) {
        Article dbArticle = articleService.getArticleByStatusAndId(null, id);
        if (dbArticle == null) return;
        User user = (User) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，则跳转403
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getUserId())
                && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return;
        }
        articleService.deleteArticle(id);
    }

    /**
     * 编辑文章页面显示。
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "edit/{id}")
    public String editArticleView(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Article article = articleService.getArticleByStatusAndId(null, id);
        if (article == null) {
            return "redirect:/404";
        }
        User user = (User) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，则跳转403
        if (!Objects.equals(article.getArticleUserId(), user.getUserId())
                && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        model.addAttribute("article", article);
        List<Category> categoryList = categoryService.listCategory();
        model.addAttribute("categoryList", categoryList);

        List<Tag> tagList = tagService.listTag();
        model.addAttribute("tagList", tagList);

        return "admin/article/edit";
    }

    /**
     * 编辑文章提交。
     *
     * @param articleParam
     * @param session
     * @return
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editArticleSubmit(ArticleParam articleParam, HttpSession session) {
        Article dbArticle = articleService.getArticleByStatusAndId(null, articleParam.getArticleId());
        if (dbArticle == null) {
            return "redirect:/404";
        }
        User user = (User) session.getAttribute("user");
        // 如果不是管理员，访问其他用户的数据，则跳转403
        if (!Objects.equals(dbArticle.getArticleUserId(), user.getUserId()) && !Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        Article article = new Article();
        article.setArticleId(articleParam.getArticleId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());

        //文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }

        // 填充分类
        List<Category> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);

        // 填充标签
        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); ++i) {
                Tag tag = new Tag(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);

        articleService.updateArticleDetail(article);
        return "redirect:/admin/article";
    }

    /**
     * 后台添加文章提交操作。
     *
     * @param session
     * @param articleParam
     * @return
     */
    @RequestMapping(value = "/insertDraftSubmit", method = RequestMethod.POST)
    public String insertDraftSubmit(HttpSession session, ArticleParam articleParam) {
        Article article = new Article();
        // 用户ID
        User user = (User) session.getAttribute("user");
        if (user != null) {
            article.setArticleUserId(user.getUserId());
        }
        article.setArticleTitle(articleParam.getArticleTitle());

        // 文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(articleParam.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());

        // 填充分类
        List<Category> categoryList = new ArrayList<>();
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if (articleParam.getArticleChildCategoryId() != null) {
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);

        // 填充标签
        List<Tag> tagList = new ArrayList<>();
        if (articleParam.getArticleTagIds() != null) {
            for (int i = 0; i < articleParam.getArticleTagIds().size(); ++i) {
                Tag tag = new Tag(articleParam.getArticleTagIds().get(i));
                tagList.add(tag);
            }
        }
        article.setTagList(tagList);

        articleService.insertArticle(article);
        return "redirect:/admin";
    }
}