package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 17:34
 */
public enum ArticleCommentStatus {
    /**
     * ALLOW：允许
     * NOT_ALLOW：不允许
     */
    ALLOW(1, "允许"), NOT_ALLOW(0, "不允许");

    private ArticleCommentStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    private Integer value;
    private String message;


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

