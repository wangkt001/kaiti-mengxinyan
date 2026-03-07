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
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
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
    public void updateById(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.update(user);
    }
}
