package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.OrderDao;
import com.campus.secondhand.model.Order;
import com.campus.secondhand.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> listByBuyer(Integer buyerId) {
        return orderDao.listByBuyer(buyerId);
    }

    @Override
    public List<Order> listBySeller(Integer sellerId) {
        return orderDao.listBySeller(sellerId);
    }

    @Override
    public Order getById(Integer id) {
        return orderDao.getById(id);
    }

    @Override
    public List<Order> findByBuyerId(Integer buyerId) {
        return orderDao.listByBuyer(buyerId);
    }

    @Override
    public List<Order> findBySellerId(Integer sellerId) {
        return orderDao.listBySeller(sellerId);
    }

    @Override
    public Order createOrder(Order order) {
        // 生成订单号：时间戳 + 两位随机数
        String timestamp = String.valueOf(System.currentTimeMillis());
        int random = (int) (Math.random() * 100);
        String orderNumber = timestamp + String.format("%02d", random);
        order.setOrderNumber(orderNumber);

        order.setStatus("completed");
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setUpdatedAt(java.time.LocalDateTime.now());
        orderDao.insert(order);
        return order;
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = orderDao.getById(orderId);
        if (order != null) {
            order.setStatus(status);
            orderDao.update(order);
        }
    }

    @Override
    public List<Order> listAll() {
        return orderDao.listAll();
    }
}