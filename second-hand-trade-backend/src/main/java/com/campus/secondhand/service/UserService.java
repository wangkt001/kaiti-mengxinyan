package com.campus.secondhand.service;

import com.campus.secondhand.model.User;

public interface UserService {
    User login(String username, String password);

    User register(User user);

    User findByUsername(String username);

    User findByIdNumber(String idNumber);

    User getById(Integer id);

    void updateById(User user);
}
