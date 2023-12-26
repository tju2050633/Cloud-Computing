package com.jerrylu.App.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Order {
    @TableId
    private Long orderId;
    private Double totalPrice;
    private String orderDate;
    private String orderStatus;
}
