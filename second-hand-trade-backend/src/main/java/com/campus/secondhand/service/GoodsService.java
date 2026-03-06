package com.campus.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.secondhand.model.Goods;

import java.util.List;

public interface GoodsService extends IService<Goods> {
    List<Goods> findByCategoryId(Integer categoryId);
    List<Goods> findByUserId(Integer userId);
    List<Goods> search(String keyword);
}
