package com.chuan.ssm.blog.enums;

/**
 * @author chuan
 * @date 2021/4/13 18:57
 */
public enum LinkStatus {
    /**
     * 同CategoryStatus，可考虑怎么更好地与这些类似的代码整合。
     * <p>
     * NORMAL - 显示
     * HIDDEN - 隐藏
     */
    NORMAL(1, "显示"), HIDDEN(0, "隐藏");
    private Integer value;
    private String message;

    LinkStatus(Integer value, String message) {
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
