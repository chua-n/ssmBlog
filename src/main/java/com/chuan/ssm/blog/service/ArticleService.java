package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Article;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * 博客文章的相关服务。
 *
 * @author chuan
 * @date 2021/4/13 19:27
 */
public interface ArticleService {
    /**
     * 获取处于某状态的文章的总数？
     *
     * @param status 状态
     * @return 文章数量
     */
    Integer countArticle(Integer status);

    /**
     * 获取评论总数
     *
     * @return 数量
     */
    Integer countArticleComment();

    /**
     * 获取浏览量。
     *
     * @return 数量
     */
    Integer countArticleView();

    /**
     * 统计有这个分类的文章数。
     *
     * @param categoryId 分类ID
     * @return 数量
     */
    Integer countArticleByCategoryId(Integer categoryId);

    /**
     * 统计有这个标签的文章数
     *
     * @param tagId 标签ID
     * @return 数量
     */
    Integer countArticleByTagId(Integer tagId);

    /**
     * 获得所有文章不分页
     *
     * @param criteria 查询条件
     * @return 列表
     */
    List<Article> listArticle(HashMap<String, Object> criteria);

    /**
     * 获取某用户最新的文章？
     *
     * @param userId 用户ID
     * @param limit  查询数量
     * @return 文章列表
     */
    List<Article> listRecentArticle(Integer userId, Integer limit);

    /**
     * 修改文章的详细信息
     *
     * @param article
     */
    void updateArticleDetail(Article article);

    /**
     * 修改文章简单信息（WTF???）
     *
     * @param article
     */
    void updateArticle(Article article);

    /**
     * 批量删除文章
     *
     * @param ids 文章的ID列表
     */
    void deleteArticleBatch(List<Integer> ids);

    /**
     * 删除文章
     *
     * @param id 某文章ID
     */
    void deleteArticle(Integer id);

    /**
     * 分页显示（分毛页你倒是说清楚呀！）
     *
     * @param pageIndex 第几页开始
     * @param pageSize  一页显示多少
     * @param criteria  查询条件
     * @return 文章列表
     */
    PageInfo<Article> pageArticle(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria);

    /**
     * 文章详情页面展示。
     *
     * @param status 状态
     * @param id     文章ID
     * @return 文章
     */
    Article getArticleByStatusAndId(Integer status, Integer id);

    /**
     * 获取访问量较多的文章（这写得什么狗屁注释？）
     *
     * @param limit 查询数量
     * @return 文章的列表
     */
    List<Article> listArticleByViewCount(Integer limit);

    /**
     * 获取上一篇文章
     */
    Article getAfterArticle(Integer id);

    /**
     * 获取下一篇文章。
     *
     * @param id
     * @return
     */
    Article getPreArticle(Integer id);

    /**
     * 随机获取一些文章。
     *
     * @param limit
     * @return
     */
    List<Article> listRandomArticle(Integer limit);

    /**
     * 获取评论数较多的文章。
     *
     * @param limit
     * @return
     */
    List<Article> listArticleByCommentCount(Integer limit);

    /**
     * 添加文章。
     *
     * @param article
     */
    void insertArticle(Article article);

    /**
     * 更新文章的评论数。
     *
     * @param articleId
     */
    void updateCommentCount(Integer articleId);

    /**
     * 获取最后的更新记录。
     *
     * @return
     */
    Article getLastUpdateArticle();

    /**
     * 获得相关文章。
     *
     * @param categoryId
     * @param limit
     * @return
     */
    List<Article> listArticleByCategoryId(Integer categoryId, Integer limit);

    /**
     * 获得相关文章
     *
     * @param categoryIds
     * @param limit
     * @return
     */
    List<Article> listArticleByCategoryIds(List<Integer> categoryIds, Integer limit);

    /**
     * 根据文章ID获得分类ID列表。
     *
     * @param articleId
     * @return
     */
    List<Integer> listCategoryIdByArticleId(Integer articleId);

    /**
     * 获得所有的文章。
     *
     * @return
     */
    List<Article> listAllNotWithContent();
}
