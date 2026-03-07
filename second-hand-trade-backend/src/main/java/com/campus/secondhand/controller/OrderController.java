package com.campus.secondhand.controller;

import com.campus.secondhand.model.Order;
import com.campus.secondhand.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/buyer")
    public List<Order> listByBuyer(@RequestHeader("X-User-Id") Integer userId) {
        return orderService.findByBuyerId(userId);
    }

    @GetMapping("/seller")
    public List<Order> listBySeller(@RequestHeader("X-User-Id") Integer userId) {
        return orderService.findBySellerId(userId);
    }

    @GetMapping("/detail/{id}")
    public Order detail(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @PostMapping("/create")
    public Order create(@RequestBody Order order, @RequestHeader("X-User-Id") Integer userId) {
        order.setBuyerId(userId);
        return orderService.createOrder(order);
    }

    @PutMapping("/update-status")
    public void updateStatus(@RequestParam Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
    }
}
