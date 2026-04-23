package com.campus.secondhand.service;

import com.campus.secondhand.model.Evaluation;

import java.util.List;

public interface EvaluationService {
    List<Evaluation> listByOrder(Integer orderId);

    List<Evaluation> listByEvaluated(Integer evaluatedId);

    List<Evaluation> findByOrderId(Integer orderId);

    List<Evaluation> findByEvaluatedId(Integer evaluatedId);

    void save(Evaluation evaluation, Integer userId);
}
