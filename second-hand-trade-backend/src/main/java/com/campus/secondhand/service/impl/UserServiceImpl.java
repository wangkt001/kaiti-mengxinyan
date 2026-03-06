package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.UserDao;
import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        baseMapper.insert(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public User findByIdNumber(String idNumber) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id_number", idNumber);
        return baseMapper.selectOne(wrapper);
    }
}
