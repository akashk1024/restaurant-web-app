package com.application.restaurantwebapp.controller;

import com.application.restaurantwebapp.entity.User;
import com.application.restaurantwebapp.service.SecurityService;
import com.application.restaurantwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @GetMapping("/")
    public String loginStatus() {
        if (securityService.getUserId() == null) {
            return "Not logged in. Please login first to use the app. Register if not already a user";
        }
        else
            return "Login successful";
    }

    @PostMapping("/register")
    public User createUser(String name, String password, String address) {
        return userService.createUser(name, password, address);
    }
}
