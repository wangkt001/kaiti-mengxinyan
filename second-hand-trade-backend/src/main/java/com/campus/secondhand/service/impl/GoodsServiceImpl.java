package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.GoodsDao;
import com.campus.secondhand.model.Goods;
import com.campus.secondhand.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> list() {
        return goodsDao.list();
    }

    @Override
    public List<Goods> listByCategory(Integer categoryId) {
        return goodsDao.listByCategory(categoryId);
    }

    @Override
    public List<Goods> listByUser(Integer userId) {
        return goodsDao.listByUser(userId);
    }

    @Override
    public List<Goods> search(String keyword) {
        return goodsDao.search(keyword);
    }

    @Override
    public Goods getById(Integer id) {
        return goodsDao.getById(id);
    }

    @Override
    public List<Goods> findByCategoryId(Integer categoryId) {
        return goodsDao.listByCategory(categoryId);
    }

    @Override
    public List<Goods> findByUserId(Integer userId) {
        return goodsDao.listByUser(userId);
    }

    @Override
    public void save(Goods goods) {
        goodsDao.insert(goods);
    }

    @Override
    public void updateById(Goods goods) {
        goodsDao.update(goods);
    }

    @Override
    public void removeById(Integer id) {
        goodsDao.delete(id);
    }

    @Override
    public List<Goods> listAll() {
        return goodsDao.listAll();
    }

    @Override
    public void updateGoods(Integer id, Goods goods) {
        Goods existingGoods = goodsDao.getById(id);
        if (existingGoods != null) {
            if (goods.getName() != null) {
                existingGoods.setName(goods.getName());
            }
            if (goods.getDescription() != null) {
                existingGoods.setDescription(goods.getDescription());
            }
            if (goods.getPrice() != null) {
                existingGoods.setPrice(goods.getPrice());
            }
            if (goods.getStock() != null) {
                existingGoods.setStock(goods.getStock());
            }
            if (goods.getCategoryId() != null) {
                existingGoods.setCategoryId(goods.getCategoryId());
            }
            if (goods.getUserId() != null) {
                existingGoods.setUserId(goods.getUserId());
            }
            if (goods.getStatus() != null) {
                existingGoods.setStatus(goods.getStatus());
            }
            if (goods.getViewCount() != null) {
                existingGoods.setViewCount(goods.getViewCount());
            }
            goodsDao.update(existingGoods);
        }
    }

    @Override
    public void deleteGoods(Integer id) {
        goodsDao.delete(id);
    }
}