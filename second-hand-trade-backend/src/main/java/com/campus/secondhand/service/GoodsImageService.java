package com.campus.secondhand.service;

import com.campus.secondhand.model.GoodsImage;

import java.util.List;

public interface GoodsImageService {
    List<GoodsImage> listByGoodsId(Integer goodsId);

    List<GoodsImage> findByGoodsId(Integer goodsId);

    void save(GoodsImage goodsImage);

    void removeById(Integer id);

    void deleteByGoodsId(Integer goodsId);
}
