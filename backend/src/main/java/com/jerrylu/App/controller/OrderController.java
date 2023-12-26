package com.jerrylu.App.controller;

import com.jerrylu.App.pojo.Order;
import com.jerrylu.App.pojo.OrderItem;
import com.jerrylu.App.pojo.Cart;
import com.jerrylu.App.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 创建订单
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Cart cart) {
        System.out.println("OrderController createOrder" + cart.toString());

        Order createdOrder = orderService.createOrder(cart);
        return ResponseEntity.ok(createdOrder);
    }

    // 获取订单详情
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        System.out.println("OrderController getOrderById" + orderId.toString());

        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // 获取所有订单
    @GetMapping("/get")
    public ResponseEntity<List<Order>> getAllOrders() {
        System.out.println("OrderController getAllOrders");
        
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 根据日期排序获取订单
    @GetMapping("/get/date/{type}")
    public ResponseEntity<List<Order>> getOrdersSortedByDate(@PathVariable String type) {
        System.out.println("OrderController getOrdersSortedByDate");

        List<Order> orders = orderService.getOrdersSortedByDate(type);
        return ResponseEntity.ok(orders);
    }

    // 根据价格排序获取订单
    @GetMapping("/get/total/{type}")
    public ResponseEntity<List<Order>> getOrdersSortedByPrice(@PathVariable String type) {
        System.out.println("OrderController getOrdersSortedByPrice");

        List<Order> orders = orderService.getOrdersSortedByPrice(type);
        return ResponseEntity.ok(orders);
    }

    // 已发货订单
    @GetMapping("/get/sent")
    public ResponseEntity<List<Order>> getSentOrders() {
        System.out.println("OrderController getSentOrders");

        List<Order> orders = orderService.getSentOrders();
        return ResponseEntity.ok(orders);
    }

    // 待发货订单
    @GetMapping("/get/unsent")
    public ResponseEntity<List<Order>> getUnsentOrders() {
        System.out.println("OrderController getUnsentOrders");

        List<Order> orders = orderService.getUnsentOrders();
        return ResponseEntity.ok(orders);
    }

    // 根据订单ID获取订单项列表
    @GetMapping("/items/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        System.out.println("OrderController getOrderItemsByOrderId" + orderId.toString());

        List<OrderItem> order_items = orderService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(order_items);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId) {
        System.out.println("OrderController deleteOrderById" + orderId.toString());

        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok().build();
    }
}
