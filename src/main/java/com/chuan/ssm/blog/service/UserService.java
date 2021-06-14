package com.chuan.ssm.blog.service;

import com.chuan.ssm.blog.entity.User;

import java.util.List;

/**
 * @author chuan
 * @date 2021/4/19 19:26
 */
public interface UserService {
    List<User> listUser();

    User getUserById(Integer id);

    void updateUser(User user);

    void deleteUser(Integer id);

    User insertUser(User user);

    User getUserByNameOrEmail(String str);

    User getUserByName(String name);

    User getUserByEmail(String email);
}
