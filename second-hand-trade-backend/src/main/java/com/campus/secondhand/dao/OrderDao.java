package com.campus.secondhand.dao;

import com.campus.secondhand.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderDao {
    @Select("SELECT * FROM orders WHERE buyer_id = #{buyerId}")
    List<Order> listByBuyer(Integer buyerId);

    @Select("SELECT * FROM orders WHERE seller_id = #{sellerId}")
    List<Order> listBySeller(Integer sellerId);

    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order getById(Integer id);

    @Insert("INSERT INTO orders (order_number, goods_id, buyer_id, seller_id, price, status, created_at, updated_at) VALUES (#{orderNumber}, #{goodsId}, #{buyerId}, #{sellerId}, #{price}, #{status}, #{createdAt}, #{updatedAt})")
    void insert(Order order);

    @Update("UPDATE orders SET status = #{status}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Order order);
}
