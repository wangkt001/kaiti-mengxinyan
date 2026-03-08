package com.campus.secondhand.service;

import com.campus.secondhand.model.User;

import java.util.List;

public interface UserService {
    User login(String username, String password);

    User register(User user);

    User findByUsername(String username);

    User findByIdNumber(String idNumber);

    User getById(Integer id);

    void updateById(User user);

    List<User> listAll();

    void updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
