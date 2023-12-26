package com.jerrylu.App.controller;

import com.jerrylu.App.pojo.Cart;
import com.jerrylu.App.pojo.CartRequest;
import com.jerrylu.App.pojo.Product;
import com.jerrylu.App.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // 获取一个空的购物车
    @GetMapping("/init")
    public ResponseEntity<Cart> getEmptyCart() {
        System.out.println("CartController getEmptyCart");

        Cart cart = new Cart();
        return ResponseEntity.ok(cart);
    }

    // 添加商品到购物车
    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartRequest request) {
        System.out.println("CartController addProductToCart" + request.toString());
        
        Cart updatedCart = cartService.addProductToCart(request.getCart(), request.getProductId(),request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    // 获取购物车内容
    @GetMapping("/get")
    public ResponseEntity<Map<Product, Integer>> getCartContents(@RequestBody Cart cart) {
        System.out.println("CartController getCartContents" + cart.toString());

        Map<Product, Integer> cartItems = cartService.getCartContents(cart);
        return ResponseEntity.ok(cartItems);
    }

    // 从购物车中减少商品数量
    @PostMapping("/decrease")
    public ResponseEntity<Cart> decreaseProductQuantity(@RequestBody CartRequest request) {
        System.out.println("CartController decreaseProductQuantity" + request.toString());

        Cart updatedCart = cartService.decreaseProductQuantity(request.getCart(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    // 从购物车中删除商品
    @PostMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestBody Cart cart, @PathVariable Long productId) {
        System.out.println("CartController removeProductFromCart" + cart.toString() + productId.toString());

        Cart updatedCart = cartService.removeProductFromCart(cart, productId);
        return ResponseEntity.ok(updatedCart);
    }

    // 计算购物车中商品的总价
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalPrice(@RequestBody Cart cart) {
        System.out.println("CartController getTotalPrice" + cart.toString());

        Double totalPrice = cartService.getTotalPrice(cart);
        return ResponseEntity.ok(totalPrice);
    }
}
