package com.application.restaurantwebapp.controller;

import com.application.restaurantwebapp.RestaurantWebAppApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest(classes = RestaurantWebAppApplication.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @WithMockUser(username = "admin")
    @Test
    void showMenu() throws Exception {
        String url = "/app/showMenu";
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertTrue(response.getContentAsString().contains("item_id"));
    }

    @Test
    @WithMockUser(username = "1")
    void createOrder() throws Exception {
        String url = "/app/add-order?itemId=1&quantity=1";
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post(url)).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertTrue(response.getContentAsString().contains("o_id"));
    }

    @Test
    @WithMockUser(username = "1")
    void test_showBill() throws Exception {
        String orderUrl = "/app/add-order?itemId=1&quantity=1";
        String billUrl = "/app/show-bill?orderId=1";
        MockHttpServletResponse orderResponse = mockMvc.perform(MockMvcRequestBuilders.post(orderUrl)).andReturn().getResponse();
        MockHttpServletResponse billResponse = mockMvc.perform(MockMvcRequestBuilders.get(billUrl)).andReturn().getResponse();
        assertEquals(200, orderResponse.getStatus());
        assertEquals(200, billResponse.getStatus());
        assertTrue(billResponse.getContentAsString().contains("amount"));
    }
}