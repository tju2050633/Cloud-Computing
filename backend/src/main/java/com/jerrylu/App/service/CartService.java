package com.jerrylu.App.service;

import com.jerrylu.App.pojo.Cart;
import com.jerrylu.App.pojo.Product;
import com.jerrylu.App.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private ProductMapper productMapper;

    public Cart addProductToCart(Cart cart, Long productId, Integer quantity) {
        // 实现添加商品到购物车的逻辑
        Map<Long, Integer> items = cart.getItems();
        if (items.containsKey(productId)) {
            items.put(productId, items.get(productId) + quantity);
        } else {
            items.put(productId, quantity);
        }
        cart.setItems(items);
        return cart;
    }

    public Map<Product, Integer> getCartContents(Cart cart) {
        // 实现获取购物车内容的逻辑
        Map<Product, Integer> items = new HashMap<>();
        Map<Long, Integer> cartItems = cart.getItems();
        for (Long productId : cartItems.keySet()) {
            Product product = productMapper.selectById(productId);
            items.put(product, cartItems.get(productId));
        }
        return items;
    }

    public Cart decreaseProductQuantity(Cart cart, Long productId, Integer quantity) {
        // 实现更新购物车中的商品数量的逻辑
        Map<Long, Integer> items = cart.getItems();
        if (items.get(productId) <= quantity) {
            items.remove(productId);
        } else {
            items.put(productId, items.get(productId) - quantity);
        }
        cart.setItems(items);
        return cart;
    }

    public Cart removeProductFromCart(Cart cart, Long productId) {
        // 实现从购物车中删除商品的逻辑
        Map<Long, Integer> items = cart.getItems();
        items.remove(productId);
        cart.setItems(items);
        return cart;
    }

    public Double getTotalPrice(Cart cart) {
        // 实现计算购物车中商品总价的逻辑
        Double totalPrice = 0.0;
        Map<Long, Integer> items = cart.getItems();
        for (Long productId : items.keySet()) {
            Double price = productMapper.selectById(productId).getPrice();
            Integer quantity = items.get(productId);
            totalPrice += price * quantity;
        }
        return totalPrice;
    }
}
