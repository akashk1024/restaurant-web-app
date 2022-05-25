package com.application.restaurantwebapp.service;

import com.application.restaurantwebapp.model.Invoice;
import com.application.restaurantwebapp.entity.Item;
import com.application.restaurantwebapp.entity.Order;
import com.application.restaurantwebapp.repository.OrderRepository;
import com.application.restaurantwebapp.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    public static final String SEPERATOR = "-";
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemService itemsService;

    @Autowired
    SecurityService securityService;

    public Order createOrder(long itemId, int quantity) {
        Long userId = securityService.getUserId();
        Item item = itemsService.getItemById(itemId);
        String commaSeperatedItems = getCommaSeperatedItems(null, quantity, item);
        double amount = item.getPrice() * quantity;
        userId = userId == null ? 1L : userId;
        Order order = Order.builder().userId(userId).amount(amount).items(commaSeperatedItems).orderDate(new Date()).build();
        return orderRepository.save(order);
    }

    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrder(Long orderId, long itemId, int newQuantity) {
        Order order = orderRepository.getById(orderId);
        String commaSeperatedItems = order.getItems();
        Item item = itemsService.getItemById(itemId);
        String itemName = item.getName();
        double updatedAmount = getUpdatedAmount(newQuantity, order, commaSeperatedItems, item, itemName);

        String newCommaSeperatedItems = getNewCommaSeperatedItems(newQuantity, commaSeperatedItems, item);
        order.setItems(newCommaSeperatedItems);
        order.setAmount(updatedAmount);
        return orderRepository.saveAndFlush(order);
    }

    private double getUpdatedAmount(int newQuantity, Order order, String commaSeperatedItems, Item item, String itemName) {
        double updatedAmount = order.getAmount();
        if(commaSeperatedItems.contains(itemName)) {
            int oldQuantity;
            String[] entries = commaSeperatedItems.split(",");
            for(String entry: entries) {
                if (entry.split("-")[0].equals(itemName)) {
                    oldQuantity = Integer.parseInt(entry.split("-")[1]);
                    updatedAmount = updatedAmount + item.getPrice() * (newQuantity - oldQuantity);
                }
            }
        } else {
            updatedAmount += item.getPrice() * newQuantity;
        }
        return updatedAmount;
    }

    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    public List<Invoice> showAllBills(){
        return orderRepository.findAll().stream().map(o -> new Invoice(o.getO_id(), o.getItems(), o.getAmount())).collect(Collectors.toList());
    }

    public List<Invoice> showTopDayBills() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderDate().after(CommonUtils.removeTime(new Date())))
                .map(o -> new Invoice(o.getO_id(), o.getItems(), o.getAmount())).collect(Collectors.toList());
    }

    public double showCurrentMonthSales() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderDate().after(CommonUtils.firstDateOfMonth()))
                .mapToDouble(Order::getAmount).sum();
    }

    private String getCommaSeperatedItems(String oldName, int quantity, Item item) {
        if (oldName == null) {
            return item.getName() + SEPERATOR + quantity;
        }
        else
            return oldName + "," + item.getName() + SEPERATOR + quantity;
    }

    private String getNewCommaSeperatedItems(int quantity, String commaSeperatedItems, Item item) {
        String newCommaSeperatedItems;
        if (commaSeperatedItems.contains(item.getName())) {
            StringBuilder sb = new StringBuilder();
            String[] entries = commaSeperatedItems.split(",");
            for (int i = 0; i < entries.length; i++) {
                if (entries[i].contains(item.getName())) {
                    sb.append(item.getName()).append("-").append(quantity);
                } else {
                    sb.append(entries[i]);
                }
                if (i != entries.length - 1) {
                    sb.append(",");
                }
            }
            newCommaSeperatedItems = sb.toString();
        } else {
            newCommaSeperatedItems = getCommaSeperatedItems(commaSeperatedItems, quantity, item);
        }
        return newCommaSeperatedItems;
    }


}
