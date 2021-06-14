package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:40
 */
public interface PageMapper {
    int deleteById(int pageId);

    int insert(Page page);

    Page getPageById(int pageId);

    int update(Page page);

    // 当这里`status`为int类型时，将listPage(null)将报错！而listPage(1)不报错！
    List<Page> listPage(Integer status);

    Page getPageByKey(@Param("status") Integer status,
                      @Param("key") String key);
}
