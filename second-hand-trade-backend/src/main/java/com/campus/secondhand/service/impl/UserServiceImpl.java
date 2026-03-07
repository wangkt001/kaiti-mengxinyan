package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.UserDao;
import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User login(String username, String password) {
        User user = userDao.getByUsername(username);
        if (user != null) {
            // 检查密码是否是明文（用于初始化的管理员账号）
            if (user.getPassword().equals(password)) {
                // 如果是明文密码，更新为加密密码
                user.setPassword(passwordEncoder.encode(password));
                userDao.update(user);
                return user;
            } else if (passwordEncoder.matches(password, user.getPassword())) {
                // 正常的加密密码验证
                return user;
            }
        }
        return null;
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.insert(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User findByIdNumber(String idNumber) {
        return userDao.getByIdNumber(idNumber);
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public void updateById(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.update(user);
    }
}
