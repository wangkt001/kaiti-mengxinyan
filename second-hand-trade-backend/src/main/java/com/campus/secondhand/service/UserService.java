package com.campus.secondhand.service;

import com.campus.secondhand.model.User;

public interface UserService {
    User login(String username, String password);

    User register(User user);

    User findByUsername(String username);

    User findByIdNumber(String idNumber);

    void updateById(User user);
}
