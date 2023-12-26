package com.jerrylu.App.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jerrylu.App.mapper.OrderMapper;
import com.jerrylu.App.mapper.OrderItemMapper;
import com.jerrylu.App.mapper.ProductMapper;
import com.jerrylu.App.pojo.Order;
import com.jerrylu.App.pojo.OrderItem;
import com.jerrylu.App.pojo.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    public Order createOrder(Cart cart) {
        // 实现创建订单的逻辑
        Order order = new Order();
        order.setOrderStatus("created");

        // 日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String orderDate = LocalDate.now().format(formatter);
        order.setOrderDate(orderDate);

        // 总价
        Double totalPrice = 0.0;
        for (Long productId : cart.getItems().keySet()) {
            Double price = productMapper.selectById(productId).getPrice();
            Integer quantity = cart.getItems().get(productId);
            totalPrice += price * quantity;
        }
        order.setTotalPrice(totalPrice);

        // 商品项
        for (Long productId : cart.getItems().keySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(productId);
            orderItem.setQuantity(cart.getItems().get(productId));
            orderItem.setOrderId(order.getOrderId());
            orderItemMapper.insertOrderItem(orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity());
        }

        orderMapper.insert(order);

        return order;
    }

    public Order getOrderById(Long orderId) {
        // 实现获取订单详情的逻辑
        return orderMapper.selectById(orderId);
    }

    public List<Order> getAllOrders() {
        // 实现获取所有订单的逻辑
        return orderMapper.selectList(null);
    }

    public List<Order> getOrdersSortedByDate(String type) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, type.equalsIgnoreCase("asc"), "order_date");
        return orderMapper.selectList(queryWrapper);
    }

    public List<Order> getOrdersSortedByPrice(String type) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, type.equalsIgnoreCase("asc"), "total_price");
        return orderMapper.selectList(queryWrapper);
    }

    public List<Order> getSentOrders() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", "已发货");
        return orderMapper.selectList(queryWrapper);
    }

    public List<Order> getUnsentOrders() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", "待发货");
        return orderMapper.selectList(queryWrapper);
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        // 实现获取订单项列表的逻辑
        return orderItemMapper.selectByOrderId(orderId);
    }

    public void deleteOrderById(Long orderId) {
        // 实现删除订单的逻辑
        orderItemMapper.deleteByOrderId(orderId);
        orderMapper.deleteById(orderId);
    }

    public void deleteOrderItemsByOrderId(Long orderId) {
        // 实现删除订单项的逻辑
        orderItemMapper.deleteByOrderId(orderId);
    }

    public void deleteOrderItemByProductId(Long productId) {
        // 实现删除订单的逻辑
        orderItemMapper.deleteByProductId(productId);
    }
}
