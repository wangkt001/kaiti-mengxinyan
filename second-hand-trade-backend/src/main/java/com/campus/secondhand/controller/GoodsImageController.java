package com.campus.secondhand.controller;

import com.campus.secondhand.model.GoodsImage;
import com.campus.secondhand.service.GoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/upload")
    public GoodsImage upload(@RequestParam("file") MultipartFile file, @RequestParam("goodsId") Integer goodsId)
            throws IOException {
        System.out.println("接收到图片上传请求");
        System.out.println("商品ID: " + goodsId);
        System.out.println("文件名: " + file.getOriginalFilename());
        System.out.println("文件大小: " + file.getSize());

        // 将图片转换为base64格式
        byte[] bytes = file.getBytes();
        String base64Data = java.util.Base64.getEncoder().encodeToString(bytes);
        String imageData = "data:image/" + file.getContentType().split("/")[1] + ";base64," + base64Data;

        // 保存到数据库
        GoodsImage goodsImage = new GoodsImage();
        goodsImage.setGoodsId(goodsId);
        goodsImage.setImageData(imageData);
        goodsImage.setCreatedAt(java.time.LocalDateTime.now());
        goodsImageService.save(goodsImage);
        System.out.println("图片信息保存到数据库成功");
        return goodsImage;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        goodsImageService.removeById(id);
    }
}
