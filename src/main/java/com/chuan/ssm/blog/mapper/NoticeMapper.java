package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.Notice;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:37
 */
public interface NoticeMapper {
    int deleteById(int noticeId);

    int insert(Notice notice);

    Notice getNoticeById(Integer noticeId);

    int update(Notice notice);

    int updateByPrimaryKey(Notice notice);

    int countNotice(Integer status);

    List<Notice> listNotice(Integer status);
}
