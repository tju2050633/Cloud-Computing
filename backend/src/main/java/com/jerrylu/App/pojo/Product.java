package com.jerrylu.App.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("products")
public class Product {
    @TableId
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Integer space;
    private Integer sold;
    private Date createDate;
    private Date modifyDate;
}
