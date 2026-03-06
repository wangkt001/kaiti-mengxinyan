package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.EvaluationDao;
import com.campus.secondhand.model.Evaluation;
import com.campus.secondhand.service.EvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationDao, Evaluation> implements EvaluationService {

    @Override
    public List<Evaluation> findByOrderId(Integer orderId) {
        QueryWrapper<Evaluation> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Evaluation> findByEvaluatedId(Integer evaluatedId) {
        QueryWrapper<Evaluation> wrapper = new QueryWrapper<>();
        wrapper.eq("evaluated_id", evaluatedId);
        return baseMapper.selectList(wrapper);
    }
}
