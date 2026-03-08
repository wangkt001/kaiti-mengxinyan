package com.campus.secondhand.dao;

import com.campus.secondhand.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderDao {
    @Select("SELECT o.id, o.order_number AS orderNumber, o.goods_id AS goodsId, g.name AS goodsName, o.buyer_id AS buyerId, o.seller_id AS sellerId, b.username AS buyerName, s.username AS sellerName, o.price AS totalPrice, o.status, o.created_at AS createdAt, o.updated_at AS updatedAt FROM orders o LEFT JOIN goods g ON o.goods_id = g.id LEFT JOIN users b ON o.buyer_id = b.id LEFT JOIN users s ON o.seller_id = s.id WHERE o.buyer_id = #{buyerId}")
    List<Order> listByBuyer(Integer buyerId);

    @Select("SELECT o.id, o.order_number AS orderNumber, o.goods_id AS goodsId, g.name AS goodsName, o.buyer_id AS buyerId, o.seller_id AS sellerId, b.username AS buyerName, s.username AS sellerName, o.price AS totalPrice, o.status, o.created_at AS createdAt, o.updated_at AS updatedAt FROM orders o LEFT JOIN goods g ON o.goods_id = g.id LEFT JOIN users b ON o.buyer_id = b.id LEFT JOIN users s ON o.seller_id = s.id WHERE o.seller_id = #{sellerId}")
    List<Order> listBySeller(Integer sellerId);

    @Select("SELECT o.id, o.order_number AS orderNumber, o.goods_id AS goodsId, g.name AS goodsName, o.buyer_id AS buyerId, o.seller_id AS sellerId, b.username AS buyerName, s.username AS sellerName, o.price AS totalPrice, o.status, o.created_at AS createdAt, o.updated_at AS updatedAt FROM orders o LEFT JOIN goods g ON o.goods_id = g.id LEFT JOIN users b ON o.buyer_id = b.id LEFT JOIN users s ON o.seller_id = s.id WHERE o.id = #{id}")
    Order getById(Integer id);

    @Select("SELECT o.id, o.order_number AS orderNumber, o.goods_id AS goodsId, g.name AS goodsName, o.buyer_id AS buyerId, o.seller_id AS sellerId, b.username AS buyerName, s.username AS sellerName, o.price AS totalPrice, o.status, o.created_at AS createdAt, o.updated_at AS updatedAt FROM orders o LEFT JOIN goods g ON o.goods_id = g.id LEFT JOIN users b ON o.buyer_id = b.id LEFT JOIN users s ON o.seller_id = s.id")
    List<Order> listAll();

    @Insert("INSERT INTO orders (order_number, goods_id, buyer_id, seller_id, price, status, created_at, updated_at) VALUES (#{orderNumber}, #{goodsId}, #{buyerId}, #{sellerId}, #{totalPrice}, #{status}, #{createdAt}, #{updatedAt})")
    void insert(Order order);

    @Update("UPDATE orders SET status = #{status}, updated_at = #{updatedAt} WHERE id = #{id}")
    void update(Order order);
}
