package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.GoodsImageDao;
import com.campus.secondhand.model.GoodsImage;
import com.campus.secondhand.service.GoodsImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsImageServiceImpl extends ServiceImpl<GoodsImageDao, GoodsImage> implements GoodsImageService {

    @Override
    public List<GoodsImage> findByGoodsId(Integer goodsId) {
        QueryWrapper<GoodsImage> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId);
        return baseMapper.selectList(wrapper);
    }
}
