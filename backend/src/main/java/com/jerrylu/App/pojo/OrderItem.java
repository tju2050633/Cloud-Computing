package com.jerrylu.App.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.baomidou.mybatisplus.annotation.TableName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_items")
public class OrderItem {
    private Long orderId;  // 对应订单ID
    private Long productId;  // 对应产品ID
    private Integer quantity;  // 订单中产品的数量
}
