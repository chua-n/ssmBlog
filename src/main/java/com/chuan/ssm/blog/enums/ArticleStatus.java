package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 18:52
 */
public enum ArticleStatus {
    /**
     * PUBLISH：已发布
     * DRAFT：草稿
     */
    PUBLISH(1, "已发布"), DRAFT(0, "草稿");
    private Integer value;
    private String message;

    private ArticleStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

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
