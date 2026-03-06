package com.campus.secondhand.controller;

import com.campus.secondhand.model.User;
import com.campus.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
        }
        return user;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/info")
    public User getInfo(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @PutMapping("/update")
    public User update(@RequestBody User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null) {
            user.setId(currentUser.getId());
            userService.updateById(user);
            return user;
        }
        return null;
    }
}
