package com.campus.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.secondhand.model.Evaluation;

import java.util.List;

public interface EvaluationService extends IService<Evaluation> {
    List<Evaluation> findByOrderId(Integer orderId);
    List<Evaluation> findByEvaluatedId(Integer evaluatedId);
}
