package com.jerrylu.App.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerrylu.App.pojo.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductMapper extends BaseMapper<Product> {
    
}
