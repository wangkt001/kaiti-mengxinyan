package com.campus.secondhand.service;

import com.campus.secondhand.model.Dispute;

import java.util.List;

public interface DisputeService {
    List<Dispute> listByUser(Integer userId);

    List<Dispute> listByOrder(Integer orderId);

    Dispute getById(Integer id);

    List<Dispute> findByUserId(Integer userId);

    List<Dispute> findByOrderId(Integer orderId);

    void resolveDispute(Integer disputeId, Integer adminId, String resolution);

    void save(Dispute dispute);

    List<Dispute> listAll();

    void updateStatus(Integer disputeId, String status);
}
