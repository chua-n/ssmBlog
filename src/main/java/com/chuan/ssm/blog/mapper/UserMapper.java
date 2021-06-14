package com.chuan.ssm.blog.mapper;

import com.chuan.ssm.blog.entity.User;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 11:43
 */
public interface UserMapper {
    int deleteById(int userId);

    int insert(User user);

    User getUserById(int userId);

    int update(User user);

    List<User> listUser();

    User getUserByNameOrEmail(String str);

    User getUserByName(String name);

    User getUserByEmail(String email);
}
