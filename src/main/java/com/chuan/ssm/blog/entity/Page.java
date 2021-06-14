package com.chuan.ssm.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuan
 * @date 2021/4/13 17:31
 */
@Data
public class Page implements Serializable {
    private static final long serialVersionUID = 3927496662110298634L;
    private Integer pageId;
    private String pageKey;
    private String pageTitle;
    private String pageContent;
    private Date pageCreateTime;
    private Date pageUpdateTime;
    private Integer pageViewCount;
    private Integer pageCommentCount;
    private Integer pageStatus;
}
