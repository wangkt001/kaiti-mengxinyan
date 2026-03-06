package com.campus.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.secondhand.model.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
    User register(User user);
    User findByUsername(String username);
    User findByIdNumber(String idNumber);
}
