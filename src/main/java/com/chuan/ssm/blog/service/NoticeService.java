package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.Notice;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:18
 */
public interface NoticeService {
    List<Notice> listNotice(Integer status);

    void insertNotice(Notice notice);

    void deleteNotice(Integer id);

    void updateNotice(Notice notice);

    Notice getNoticeById(Integer id);
}
