package com.campus.secondhand.controller;

import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

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
    public User login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        try {
            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
            }
            return user;
        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        try {
            session.invalidate();
        } catch (Exception e) {
            System.out.println("Error in logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/info")
    public Object getInfo(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "User not logged in";
            }
            return user;
        } catch (Exception e) {
            System.out.println("Error in getInfo: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PutMapping("/update")
    public User update(@RequestBody User user, HttpSession session) {
        try {
            User currentUser = (User) session.getAttribute("user");
            if (currentUser != null) {
                user.setId(currentUser.getId());
                userService.updateById(user);
                return user;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error in update: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
