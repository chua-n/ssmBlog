package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author chuan
 * @date 2021/4/13 20:07
 */
public interface ArticleMapper {
    Integer deleteById(Integer articleId);

    Integer deleteByUserId(Integer userId);

    Integer deleteBatch(@Param("ids") List<Integer> ids);

    Integer insert(Article article);

    Integer update(Article article);

    List<Article> findAll(HashMap<String, Object> criteria);

    List<Article> listAllNotWithContent();

    // @Param注解是啥意思？
    Integer countArticle(@Param("status") Integer status);

    Integer countArticleComment();

    Integer countArticleView();

    List<Article> listArticle();

    Article getArticleByStatusAndId(@Param("status") Integer status, @Param("id") Integer id);

    @Deprecated
    List<Article> pageArticle(@Param("status") Integer status,
                              @Param("pageIndex") Integer pageIndex,
                              @Param("pageSize") Integer pageSize);

    List<Article> listArticleByViewCount(Integer limit);

    /**
     * 获得上一篇文章。
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getAfterArticle(Integer id);

    /**
     * 获得下一篇文章。
     *
     * @param id 文章ID
     * @return 文章
     */
    Article getPreArticle(Integer id);

    List<Article> listRandomArticle(Integer limit);

    List<Article> listArticleByCommentCount(Integer limit);

    void updateCommentCount(Integer articleId);

    Article getLastUpdateArticle();

    Integer countArticleByUser(Integer id);

    List<Article> findArticleByCategoryId(@Param("categoryId") Integer categoryId,
                                          @Param("limit") Integer limit);

    List<Article> findArticleByCategoryIds(@Param("categoryIds") List<Integer> categoryIds,
                                           @Param("limit") Integer limit);

    List<Article> listArticleByLimit(@Param("userId") Integer userId,
                                     @Param("limit") Integer limit);

    List<Integer> listArticleIdsByUserId(Integer userId);
}
