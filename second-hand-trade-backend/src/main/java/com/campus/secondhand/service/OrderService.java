package com.campus.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.secondhand.model.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    List<Order> findByBuyerId(Integer buyerId);
    List<Order> findBySellerId(Integer sellerId);
    Order createOrder(Order order);
    void updateOrderStatus(Integer orderId, String status);
}
