package com.jerrylu.App.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerrylu.App.pojo.OrderItem;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Component
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    // 插入商品项
    @Insert("insert into order_items (order_id, product_id, quantity) values(#{orderId}, #{productId}, #{quantity})")
    void insertOrderItem(@Param("orderId") Long orderId, @Param("productId") Long productId,
            @Param("quantity") Integer quantity);

    // 根据订单ID查询
    @Select("select * from order_items where order_id = #{orderId}")
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    // 根据订单ID删除
    @Select("delete from order_items where order_id = #{orderId}")
    void deleteByOrderId(@Param("orderId") Long orderId);

    // 根据商品ID删除
    @Select("delete from order_items where product_id = #{productId}")
    void deleteByProductId(@Param("productId") Long productId);
}
