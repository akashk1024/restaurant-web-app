package com.application.restaurantwebapp.controller;

import com.application.restaurantwebapp.RestaurantWebAppApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(classes = RestaurantWebAppApplication.class)
class BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_login_status() throws Exception {
        String url = "/";
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn().getResponse();
        assertEquals("Not logged in. Please login first to use the app. Register if not already a user",
                response.getContentAsString());

    }
}