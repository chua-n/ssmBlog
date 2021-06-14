package com.chuan.ssm.blog.dto;

import lombok.Data;

/**
 * VO表示Value Object，一般将数据库的操作封装在DAO内，把从数据库查询到的信息实例化为VO，然后进行各种操作。<p>
 *
 * @Author: chuan
 * @Date: 2021/4/13 15:59
 */
@Data
public class ResultVO<T> {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO() {
    }
}
