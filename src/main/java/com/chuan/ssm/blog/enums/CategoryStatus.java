package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 18:55
 */
public enum CategoryStatus {
    /**
     * NORMAL：正常
     * HIDDEN：隐藏
     */
    NORMAL(1, "正常"), HIDDEN(0, "隐藏");

    private Integer value;
    private String message;

    CategoryStatus(Integer value, String message) {
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
