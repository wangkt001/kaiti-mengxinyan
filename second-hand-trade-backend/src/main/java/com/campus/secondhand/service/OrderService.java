package com.campus.secondhand.service;

import com.campus.secondhand.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> listByBuyer(Integer buyerId);

    List<Order> listBySeller(Integer sellerId);

    Order getById(Integer id);

    List<Order> findByBuyerId(Integer buyerId);

    List<Order> findBySellerId(Integer sellerId);

    Order createOrder(Order order);

    void updateOrderStatus(Integer orderId, String status);

    List<Order> listAll();

    void deleteByGoodsId(Integer goodsId);
}
