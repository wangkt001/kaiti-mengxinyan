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
        order.setOrderNumber(UUID.randomUUID().toString().replace("-", ""));
        order.setStatus("pending");
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
}