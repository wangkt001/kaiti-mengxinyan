package com.campus.secondhand.service;

import com.campus.secondhand.model.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> list();

    List<Goods> listByCategory(Integer categoryId);

    List<Goods> listByUser(Integer userId);

    List<Goods> search(String keyword);

    Goods getById(Integer id);

    List<Goods> findByCategoryId(Integer categoryId);

    List<Goods> findByUserId(Integer userId);

    void save(Goods goods);

    void updateById(Goods goods);

    void removeById(Integer id);
}
