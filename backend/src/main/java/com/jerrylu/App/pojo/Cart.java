package com.jerrylu.App.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.HashMap;

@Data
@AllArgsConstructor
public class Cart {
    public Cart() {
        items = new HashMap<>();
    }
    public Cart(Cart cart) {
        items = new HashMap<>(cart.getItems());
    }
    private Map<Long, Integer> items;
}
