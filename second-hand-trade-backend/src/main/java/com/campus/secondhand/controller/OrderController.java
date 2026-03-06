package com.campus.secondhand.controller;

import com.campus.secondhand.model.Order;
import com.campus.secondhand.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/buyer")
    public List<Order> listByBuyer(HttpSession session) {
        Integer userId = ((com.campus.secondhand.model.User) session.getAttribute("user")).getId();
        return orderService.findByBuyerId(userId);
    }

    @GetMapping("/seller")
    public List<Order> listBySeller(HttpSession session) {
        Integer userId = ((com.campus.secondhand.model.User) session.getAttribute("user")).getId();
        return orderService.findBySellerId(userId);
    }

    @GetMapping("/detail/{id}")
    public Order detail(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @PostMapping("/create")
    public Order create(@RequestBody Order order, HttpSession session) {
        order.setBuyerId(((com.campus.secondhand.model.User) session.getAttribute("user")).getId());
        return orderService.createOrder(order);
    }

    @PutMapping("/update-status")
    public void updateStatus(@RequestParam Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
    }
}
