package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.GoodsImageDao;
import com.campus.secondhand.model.GoodsImage;
import com.campus.secondhand.service.GoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsImageServiceImpl implements GoodsImageService {

    @Autowired
    private GoodsImageDao goodsImageDao;

    @Override
    public List<GoodsImage> listByGoodsId(Integer goodsId) {
        return goodsImageDao.listByGoodsId(goodsId);
    }

    @Override
    public List<GoodsImage> findByGoodsId(Integer goodsId) {
        return goodsImageDao.listByGoodsId(goodsId);
    }

    @Override
    public void save(GoodsImage goodsImage) {
        goodsImageDao.insert(goodsImage);
    }

    @Override
    public void removeById(Integer id) {
        goodsImageDao.delete(id);
    }

    @Override
    public void deleteByGoodsId(Integer goodsId) {
        goodsImageDao.delete(goodsId);
    }
}