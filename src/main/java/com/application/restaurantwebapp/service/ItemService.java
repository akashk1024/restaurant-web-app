package com.application.restaurantwebapp.service;

import com.application.restaurantwebapp.entity.Item;
import com.application.restaurantwebapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemsRepository;

    public List<Item> getMenu() {
        return itemsRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemsRepository.findById(id).orElseThrow();
    }

}
