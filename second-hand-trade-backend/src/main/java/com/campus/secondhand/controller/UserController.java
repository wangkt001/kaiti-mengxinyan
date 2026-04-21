package com.campus.secondhand.controller;

import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/upload-avatar")
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile file,
            @RequestHeader("X-User-Id") Integer userId)
            throws IOException {
        System.out.println("接收到头像上传请求");
        System.out.println("用户ID: " + userId);
        System.out.println("文件名: " + file.getOriginalFilename());
        System.out.println("文件大小: " + file.getSize());

        byte[] bytes = file.getBytes();
        String base64Data = java.util.Base64.getEncoder().encodeToString(bytes);
        String avatarData = "data:image/" + file.getContentType().split("/")[1] + ";base64," + base64Data;

        User user = userService.getById(userId);
        if (user != null) {
            user.setAvatar(avatarData);
            userService.updateById(user);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("avatar", avatarData);
        return result;
    }
}
