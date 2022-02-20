package com.application.restaurantwebapp.controller;

import com.application.restaurantwebapp.entity.Item;
import com.application.restaurantwebapp.entity.Order;
import com.application.restaurantwebapp.service.ItemService;
import com.application.restaurantwebapp.service.OrderService;
import com.application.restaurantwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class UserController {
    @Autowired
    ItemService itemsService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/hello")
    public String sayHello() {
        return "HELLO";
    }

    @GetMapping("/showMenu")
    public List<Item> showMenu() {
        return itemsService.getMenu();
    }


    @PostMapping("/add_order")
    public Order createOrder(@RequestParam Long itemId, @RequestParam int quantity) {
        return orderService.createOrder(itemId, quantity);
    }

    @PutMapping("/update_order")
    public Order updateOrder(@RequestParam Long orderId, @RequestParam Long itemId, @RequestParam int quantity) {
        return orderService.updateOrder(orderId, itemId, quantity);
    }

    @GetMapping("show_bill")
    public Order getOrder(@RequestParam long orderId) {
        return orderService.getOrderById(orderId);
    }


    @PostMapping("remove_order")
    public void deleteOrder(Long orderId) {
        orderService.deleteOrder(orderId);
    }


}
