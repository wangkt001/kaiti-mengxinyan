package com.campus.secondhand.controller;

import com.campus.secondhand.model.User;
import com.campus.secondhand.model.Goods;
import com.campus.secondhand.model.Order;
import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.UserService;
import com.campus.secondhand.service.GoodsService;
import com.campus.secondhand.service.OrderService;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DisputeService disputeService;

    // 用户管理
    @GetMapping("/users")
    public List<User> listUsers() {
        return userService.listAll();
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    // 商品管理
    @GetMapping("/goods")
    public List<Goods> listGoods() {
        return goodsService.listAll();
    }

    @PutMapping("/goods/{id}")
    public void updateGoods(@PathVariable Integer id, @RequestBody Goods goods) {
        goodsService.updateGoods(id, goods);
    }

    @DeleteMapping("/goods/{id}")
    public void deleteGoods(@PathVariable Integer id) {
        goodsService.deleteGoods(id);
    }

    // 订单管理
    @GetMapping("/orders")
    public List<Order> listOrders() {
        return orderService.listAll();
    }

    @PutMapping("/orders/{id}/status")
    public void updateOrderStatus(@PathVariable Integer id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
    }

    // 纠纷管理
    @GetMapping("/disputes")
    public List<Dispute> listDisputes() {
        return disputeService.listAll();
    }

    @PutMapping("/disputes/{id}/status")
    public void updateDisputeStatus(@PathVariable Integer id, @RequestParam String status) {
        disputeService.updateStatus(id, status);
    }
}
