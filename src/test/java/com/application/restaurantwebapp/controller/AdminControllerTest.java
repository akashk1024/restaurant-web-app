package com.application.restaurantwebapp.controller;

import com.application.restaurantwebapp.RestaurantWebAppApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = RestaurantWebAppApplication.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "1")
    @BeforeEach
    void initializeOrder() throws Exception {
        String orderUrl = "/app/add-order?itemId=1&quantity=1";
        mockMvc.perform(MockMvcRequestBuilders.post(orderUrl)).andReturn().getResponse();
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void test_getTodaysBills() throws Exception {
        String billUrl = "/admin/fetch-topday-bills";
        MockHttpServletResponse billResponse = mockMvc.perform(MockMvcRequestBuilders.get(billUrl)).andReturn().getResponse();
        assertEquals(200, billResponse.getStatus());
        assertTrue(billResponse.getContentAsString().contains("totalAmount"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void getCurrentMonthSales() throws Exception {
        String saleUrl = "/admin/fetch-current-month-sales";
        MockHttpServletResponse saleResponse = mockMvc.perform(MockMvcRequestBuilders.get(saleUrl)).andReturn().getResponse();
        assertEquals(200, saleResponse.getStatus());
        assertTrue(saleResponse.getContentAsString().contains("Current sale"));
    }
}