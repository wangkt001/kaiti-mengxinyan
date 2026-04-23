package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.OrderDao;
import com.campus.secondhand.dao.GoodsDao;
import com.campus.secondhand.model.Order;
import com.campus.secondhand.model.Goods;
import com.campus.secondhand.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private GoodsDao goodsDao;

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
        Goods goods = goodsDao.getById(order.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }
        if (goods.getStock() < order.getQuantity()) {
            throw new RuntimeException("库存不足");
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        int random = (int) (Math.random() * 100);
        String orderNumber = timestamp + String.format("%02d", random);
        order.setOrderNumber(orderNumber);

        order.setStatus("pending");
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setUpdatedAt(java.time.LocalDateTime.now());
        orderDao.insert(order);

        int newStock = goods.getStock() - order.getQuantity();
        goods.setStock(newStock);
        if (newStock <= 0) {
            goods.setStock(0);
            goods.setStatus("sold");
        }
        goods.setUpdatedAt(java.time.LocalDateTime.now());
        goodsDao.update(goods);

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