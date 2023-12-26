package com.jerrylu.App.pojo;

import lombok.Data;

@Data
public class CartRequest {
    public CartRequest() {
        cart = new Cart();
    }

    public CartRequest(Cart cart) {
        this.cart = new Cart(cart);
    }
    public CartRequest(Cart cart, Long productId, Integer quantity) {
        this.cart = new Cart(cart);
        this.productId = productId;
        this.quantity = quantity;
    }
    private Cart cart;
    private Long productId;
    private Integer quantity;
}