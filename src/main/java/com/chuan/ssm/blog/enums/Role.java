package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 19:06
 */
public enum Role {
    /**
     * OWNER: 博主
     * VISITOR: 访客
     */
    OWNER(1, "博主"), VISITOR(0, "访客");
    private Integer value;
    private String message;

    Role(Integer value, String message) {
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
