package com.application.restaurantwebapp.controller;

import com.application.restaurantwebapp.entity.Order;
import com.application.restaurantwebapp.entity.User;
import com.application.restaurantwebapp.model.Invoice;
import com.application.restaurantwebapp.service.ItemService;
import com.application.restaurantwebapp.service.OrderService;
import com.application.restaurantwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ItemService itemsService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/show-all-users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update-user")
    public User updateUsername(@RequestParam Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String password, @RequestParam(required = false) String address) {
        return userService.updateUser(id, name, password, address);
    }

    @GetMapping("/show-user")
    public User showUser(@RequestParam Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/delete-user")
    public void deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("show-all-orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("fetch-topday-bills")
    public List<Invoice> getTodaysBills() {
        return orderService.showTopDayBills();
    }

    @GetMapping("fetch-current-month-sales")
    public String getCurrentMonthSales() {
        double sales = orderService.showCurrentMonthSales();
        return "Current sale for this month is Rs. " + String.valueOf(sales) + " !";
    }


}
