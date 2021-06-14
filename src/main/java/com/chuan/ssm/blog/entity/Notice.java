package com.chuan.ssm.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuan
 * @date 2021/4/13 17:24
 */
@Data
public class Notice implements Serializable {
    private static final long serialVersionUID = -4901560494243593100L;
    private Integer noticeId;
    private String noticeTitle;
    private String noticeContent;
    private Date noticeCreateTime;
    private Date noticeUpdateTime;
    private Integer noticeStatus;
    private Integer noticeOrder;
}
