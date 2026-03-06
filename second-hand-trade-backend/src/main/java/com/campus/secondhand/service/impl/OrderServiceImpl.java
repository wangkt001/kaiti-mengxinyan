package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.OrderDao;
import com.campus.secondhand.model.Order;
import com.campus.secondhand.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Override
    public List<Order> findByBuyerId(Integer buyerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", buyerId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Order> findBySellerId(Integer sellerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", sellerId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Order createOrder(Order order) {
        order.setOrderNumber(UUID.randomUUID().toString().replace("-", ""));
        order.setStatus("pending");
        baseMapper.insert(order);
        return order;
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Order order = baseMapper.selectById(orderId);
        if (order != null) {
            order.setStatus(status);
            baseMapper.updateById(order);
        }
    }
}
