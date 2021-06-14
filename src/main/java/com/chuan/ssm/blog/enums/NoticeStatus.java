package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 19:02
 */
public enum NoticeStatus {
    /**
     * NORMAL
     * HIDDEN
     */
    NORMAL(1, "显示"), HIDDEN(0, "隐藏");
    private Integer value;
    private String message;

    NoticeStatus(Integer value, String message) {
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
