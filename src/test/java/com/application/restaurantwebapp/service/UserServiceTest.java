package com.application.restaurantwebapp.service;

import com.application.restaurantwebapp.RestaurantWebAppApplication;
import com.application.restaurantwebapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RestaurantWebAppApplication.class)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void createUser() {
        User user = userService.createUser("user1", "password", "mumbai");
        assertTrue(userService.getAllUsers().contains(user));
    }

    @Test
    void deleteUser() {
        User user = userService.createUser("user1", "password", "mumbai");
        Long userId = user.getUser_id();
        userService.deleteUser(userId);
        assertFalse(userService.getAllUsers().contains(user));
    }

    @Test
    void getAllUsers() {
        assertEquals(2, userService.getAllUsers().size());
    }
}