package com.jerrylu.App.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jerrylu.App.mapper.ProductMapper;
import com.jerrylu.App.mapper.OrderItemMapper;
import com.jerrylu.App.pojo.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    public List<Product> getAllProducts() {
        // 实现获取所有商品列表的逻辑
        return productMapper.selectList(null);
    }

    public List<Product> getRandProducts() {
        List<Product> productList = productMapper.selectList(null);
        Collections.shuffle(productList);
        return productList;
    }

    public List<Product> getProductSortByPrice(String type) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, type.equalsIgnoreCase("asc"), "price");
        return productMapper.selectList(queryWrapper);
    }

    public List<Product> getProductSortBySold(String type) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, type.equalsIgnoreCase("asc"), "sold");
        return productMapper.selectList(queryWrapper);
    }

    public List<Product> getProductSortByCreateDate(String type) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, type.equalsIgnoreCase("asc"), "create_date");
        return productMapper.selectList(queryWrapper);
    }
    

    public Product getProductById(Long productId) {
        // 实现获取单个商品详情的逻辑
        return productMapper.selectById(productId);
    }

    public void insertProduct(Product product) {
        // 实现添加商品的逻辑
        productMapper.insert(product);
    }

    public void updateProduct(Product product) {
        // 实现更新商品信息的逻辑
        Long productId = product.getProductId();
        product.setProductId(productId);
        productMapper.updateById(product);
    }

    public void deleteProduct(Long productId) {
        // 实现删除商品的逻辑
        orderItemMapper.deleteByProductId(productId);
        productMapper.deleteById(productId);
    }
}
