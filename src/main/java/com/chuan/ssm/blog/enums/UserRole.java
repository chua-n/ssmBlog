package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 19:09
 */
public enum UserRole {
    /**
     * ADMIN: 管理员
     * USER: 普通用户
     */
    ADMIN("admin", "管理员"), USER("user", "用户");
    private String value;
    private String message;

    UserRole(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
