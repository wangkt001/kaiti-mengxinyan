package com.campus.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.secondhand.model.GoodsImage;

import java.util.List;

public interface GoodsImageService extends IService<GoodsImage> {
    List<GoodsImage> findByGoodsId(Integer goodsId);
}
