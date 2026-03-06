package com.campus.secondhand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.secondhand.model.Dispute;

import java.util.List;

public interface DisputeService extends IService<Dispute> {
    List<Dispute> findByUserId(Integer userId);
    List<Dispute> findByOrderId(Integer orderId);
    void resolveDispute(Integer disputeId, Integer adminId, String resolution);
}
