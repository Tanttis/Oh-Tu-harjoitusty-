package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return repository.findAll();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return repository.save(item);
    }
}