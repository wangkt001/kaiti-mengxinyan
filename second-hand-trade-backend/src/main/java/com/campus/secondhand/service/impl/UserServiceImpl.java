package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.UserDao;
import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (userDao.getByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (userDao.getByIdNumber(user.getIdNumber()) != null) {
            throw new RuntimeException("学号/工号已存在");
        }
        List<User> users = userDao.listAll();
        boolean emailExists = users.stream()
                .anyMatch(existing -> existing.getEmail() != null && existing.getEmail().equals(user.getEmail()));
        if (emailExists) {
            throw new RuntimeException("邮箱已被注册");
        }
        boolean phoneExists = users.stream()
                .anyMatch(existing -> existing.getPhone() != null && existing.getPhone().equals(user.getPhone()));
        if (phoneExists) {
            throw new RuntimeException("手机号已被注册");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("student");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置realName，使用username作为默认值
        if (user.getRealName() == null || user.getRealName().isEmpty()) {
            user.setRealName(user.getUsername());
        }
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userDao.insert(user);
        user.setPassword(null);
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
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userDao.update(user);
    }

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public void updateUser(Integer id, User user) {
        User existingUser = userDao.getById(id);
        if (existingUser != null) {
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getIdNumber() != null) {
                existingUser.setIdNumber(user.getIdNumber());
            }
            if (user.getPhone() != null) {
                existingUser.setPhone(user.getPhone());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }
            if (user.getAvatar() != null) {
                existingUser.setAvatar(user.getAvatar());
            }
            existingUser.setUpdatedAt(java.time.LocalDateTime.now());
            userDao.update(existingUser);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        userDao.delete(id);
    }
}
