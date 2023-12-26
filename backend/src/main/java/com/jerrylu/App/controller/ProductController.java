package com.jerrylu.App.controller;

import com.jerrylu.App.pojo.Product;
import com.jerrylu.App.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 获取所有商品
    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("ProductController getAllProducts");

        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // 获取随机顺序商品
    @GetMapping("/rand")
    public ResponseEntity<List<Product>> getRandProducts() {
        System.out.println("ProductController getRandProducts");

        List<Product> products = productService.getRandProducts();
        return ResponseEntity.ok(products);
    }

    // 根据价格排序获取产品
    @GetMapping("/get/price/{type}")
    public ResponseEntity<List<Product>> getProductSortByPrice(@PathVariable String type) {
        System.out.println("ProductController getProductSortByPrice");

        List<Product> products = productService.getProductSortByPrice(type);
        return ResponseEntity.ok(products);
    }

    // 根据销量排序获取产品
    @GetMapping("/get/sold/{type}")
    public ResponseEntity<List<Product>> getProductSortBySold(@PathVariable String type) {
        System.out.println("ProductController getProductSortBySold");

        List<Product> products = productService.getProductSortBySold(type);
        return ResponseEntity.ok(products);
    }

    // 根据创建日期排序获取产品
    @GetMapping("/get/create/{type}")
    public ResponseEntity<List<Product>> getProductSortByCreateDate(@PathVariable String type) {
        System.out.println("ProductController getProductSortByCreateDate");

        List<Product> products = productService.getProductSortByCreateDate(type);
        return ResponseEntity.ok(products);
    }


    // 获取单个商品
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        System.out.println("ProductController getProductById" + productId.toString());

        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // 插入商品
    @PostMapping("/add")
    public ResponseEntity<Void> insertProduct(@RequestBody Product product) {
        System.out.println("ProductController insertProduct" + product.toString());

        productService.insertProduct(product);
        return ResponseEntity.ok().build();
    }

    // 更新商品
    @PutMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
        System.out.println("ProductController updateProduct" + product.toString());

        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    // 删除商品
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        System.out.println("ProductController deleteProduct" + productId.toString());
        
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
