package com.campus.secondhand.controller;

import com.campus.secondhand.model.Goods;
import com.campus.secondhand.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public List<Goods> list() {
        return goodsService.list();
    }

    @GetMapping("/category/{id}")
    public List<Goods> listByCategory(@PathVariable Integer id) {
        return goodsService.findByCategoryId(id);
    }

    @GetMapping("/user/{id}")
    public List<Goods> listByUser(@PathVariable Integer id) {
        return goodsService.findByUserId(id);
    }

    @GetMapping("/search")
    public List<Goods> search(@RequestParam String keyword) {
        return goodsService.search(keyword);
    }

    @GetMapping("/detail/{id}")
    public Goods detail(@PathVariable Integer id) {
        return goodsService.getById(id);
    }

    @PostMapping("/add")
    public Goods add(@RequestBody Goods goods, @RequestHeader("X-User-Id") Integer userId) {
        goods.setUserId(userId);
        goods.setStatus("active");
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
