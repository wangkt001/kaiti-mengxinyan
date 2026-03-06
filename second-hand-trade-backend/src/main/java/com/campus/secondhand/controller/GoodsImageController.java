package com.campus.secondhand.controller;

import com.campus.secondhand.model.GoodsImage;
import com.campus.secondhand.service.GoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods-image")
public class GoodsImageController {

    @Autowired
    private GoodsImageService goodsImageService;

    @GetMapping("/goods/{id}")
    public List<GoodsImage> listByGoods(@PathVariable Integer id) {
        return goodsImageService.findByGoodsId(id);
    }

    @PostMapping("/add")
    public GoodsImage add(@RequestBody GoodsImage goodsImage) {
        goodsImageService.save(goodsImage);
        return goodsImage;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        goodsImageService.removeById(id);
    }
}
