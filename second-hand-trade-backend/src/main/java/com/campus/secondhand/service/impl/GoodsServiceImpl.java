package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.GoodsDao;
import com.campus.secondhand.model.Goods;
import com.campus.secondhand.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements GoodsService {

    @Override
    public List<Goods> findByCategoryId(Integer categoryId) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        wrapper.eq("status", "active");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Goods> findByUserId(Integer userId) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Goods> search(String keyword) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.like("name", keyword).or().like("description", keyword);
        wrapper.eq("status", "active");
        return baseMapper.selectList(wrapper);
    }
}
