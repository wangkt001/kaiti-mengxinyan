package com.campus.secondhand.controller;

import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        try {
            return userService.register(user);
        } catch (Exception e) {
            System.out.println("Error in register: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        try {
            User loggedInUser = userService.login(user.getUsername(), user.getPassword());
            return loggedInUser;
        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/logout")
    public void logout() {
        // 前端处理注销逻辑
    }

    @GetMapping("/info")
    public Object getInfo(@RequestHeader("X-User-Id") Integer userId) {
        try {
            User user = userService.getById(userId);
            if (user == null) {
                return "User not found";
            }
            return user;
        } catch (Exception e) {
            System.out.println("Error in getInfo: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PutMapping("/update")
    public User update(@RequestBody User user, @RequestHeader("X-User-Id") Integer userId) {
        try {
            user.setId(userId);
            userService.updateById(user);
            return user;
        } catch (Exception e) {
            System.out.println("Error in update: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getById/{id}")
    public User getById(@PathVariable Integer id) {
        try {
            User user = userService.getById(id);
            return user;
        } catch (Exception e) {
            System.out.println("Error in getById: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
