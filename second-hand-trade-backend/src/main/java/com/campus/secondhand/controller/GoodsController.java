package com.campus.secondhand.controller;

import com.campus.secondhand.model.Goods;
import com.campus.secondhand.model.GoodsImage;
import com.campus.secondhand.service.GoodsService;
import com.campus.secondhand.service.GoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsImageService goodsImageService;

    @GetMapping("/list")
    public List<Map<String, Object>> list() {
        List<Goods> goodsList = goodsService.list();
        return goodsList.stream().map(goods -> {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", goods.getId());
            goodsMap.put("name", goods.getName());
            goodsMap.put("description", goods.getDescription());
            goodsMap.put("price", goods.getPrice());
            goodsMap.put("stock", goods.getStock());
            goodsMap.put("categoryId", goods.getCategoryId());
            goodsMap.put("userId", goods.getUserId());
            goodsMap.put("status", goods.getStatus());
            goodsMap.put("createdAt", goods.getCreatedAt());
            goodsMap.put("updatedAt", goods.getUpdatedAt());
            goodsMap.put("images", goodsImageService.findByGoodsId(goods.getId()));
            return goodsMap;
        }).collect(Collectors.toList());
    }

    @GetMapping("/category/{id}")
    public List<Map<String, Object>> listByCategory(@PathVariable Integer id) {
        List<Goods> goodsList = goodsService.findByCategoryId(id);
        return goodsList.stream().map(goods -> {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", goods.getId());
            goodsMap.put("name", goods.getName());
            goodsMap.put("description", goods.getDescription());
            goodsMap.put("price", goods.getPrice());
            goodsMap.put("stock", goods.getStock());
            goodsMap.put("categoryId", goods.getCategoryId());
            goodsMap.put("userId", goods.getUserId());
            goodsMap.put("status", goods.getStatus());
            goodsMap.put("createdAt", goods.getCreatedAt());
            goodsMap.put("updatedAt", goods.getUpdatedAt());
            goodsMap.put("images", goodsImageService.findByGoodsId(goods.getId()));
            return goodsMap;
        }).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public List<Map<String, Object>> listByUser(@PathVariable Integer id) {
        List<Goods> goodsList = goodsService.findByUserId(id);
        return goodsList.stream().map(goods -> {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", goods.getId());
            goodsMap.put("name", goods.getName());
            goodsMap.put("description", goods.getDescription());
            goodsMap.put("price", goods.getPrice());
            goodsMap.put("stock", goods.getStock());
            goodsMap.put("categoryId", goods.getCategoryId());
            goodsMap.put("userId", goods.getUserId());
            goodsMap.put("status", goods.getStatus());
            goodsMap.put("createdAt", goods.getCreatedAt());
            goodsMap.put("updatedAt", goods.getUpdatedAt());
            goodsMap.put("images", goodsImageService.findByGoodsId(goods.getId()));
            return goodsMap;
        }).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String keyword) {
        List<Goods> goodsList = goodsService.search(keyword);
        return goodsList.stream().map(goods -> {
            Map<String, Object> goodsMap = new HashMap<>();
            goodsMap.put("id", goods.getId());
            goodsMap.put("name", goods.getName());
            goodsMap.put("description", goods.getDescription());
            goodsMap.put("price", goods.getPrice());
            goodsMap.put("stock", goods.getStock());
            goodsMap.put("categoryId", goods.getCategoryId());
            goodsMap.put("userId", goods.getUserId());
            goodsMap.put("status", goods.getStatus());
            goodsMap.put("createdAt", goods.getCreatedAt());
            goodsMap.put("updatedAt", goods.getUpdatedAt());
            goodsMap.put("images", goodsImageService.findByGoodsId(goods.getId()));
            return goodsMap;
        }).collect(Collectors.toList());
    }

    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable Integer id) {
        Goods goods = goodsService.getById(id);
        System.out.println("商品信息:");
        System.out.println("ID: " + goods.getId());
        System.out.println("Name: " + goods.getName());
        System.out.println("User ID: " + goods.getUserId());
        Map<String, Object> goodsMap = new HashMap<>();
        goodsMap.put("id", goods.getId());
        goodsMap.put("name", goods.getName());
        goodsMap.put("description", goods.getDescription());
        goodsMap.put("price", goods.getPrice());
        goodsMap.put("stock", goods.getStock());
        goodsMap.put("categoryId", goods.getCategoryId());
        goodsMap.put("userId", goods.getUserId());
        goodsMap.put("status", goods.getStatus());
        goodsMap.put("createdAt", goods.getCreatedAt());
        goodsMap.put("updatedAt", goods.getUpdatedAt());
        goodsMap.put("images", goodsImageService.findByGoodsId(goods.getId()));
        return goodsMap;
    }

    @PostMapping("/add")
    public Goods add(@RequestBody Goods goods) {
        goods.setStatus("active");
        goods.setCreatedAt(java.time.LocalDateTime.now());
        goods.setUpdatedAt(java.time.LocalDateTime.now());
        goodsService.save(goods);
        return goods;
    }

    @PutMapping("/update")
    public Goods update(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return goods;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        goodsService.removeById(id);
    }
}
