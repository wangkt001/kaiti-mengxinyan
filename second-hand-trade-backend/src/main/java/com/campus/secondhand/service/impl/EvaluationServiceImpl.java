package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.EvaluationDao;
import com.campus.secondhand.model.Evaluation;
import com.campus.secondhand.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationDao evaluationDao;

    @Override
    public List<Evaluation> listByOrder(Integer orderId) {
        return evaluationDao.listByOrder(orderId);
    }

    @Override
    public List<Evaluation> listByEvaluated(Integer evaluatedId) {
        return evaluationDao.listByEvaluated(evaluatedId);
    }

    @Override
    public List<Evaluation> findByOrderId(Integer orderId) {
        return evaluationDao.listByOrder(orderId);
    }

    @Override
    public List<Evaluation> findByEvaluatedId(Integer evaluatedId) {
        return evaluationDao.listByEvaluated(evaluatedId);
    }

    @Override
    public void save(Evaluation evaluation) {
        evaluationDao.insert(evaluation);
    }
}