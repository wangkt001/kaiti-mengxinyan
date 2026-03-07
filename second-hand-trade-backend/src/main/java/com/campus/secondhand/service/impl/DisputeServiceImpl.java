package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.DisputeDao;
import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private DisputeDao disputeDao;

    @Override
    public List<Dispute> listByUser(Integer userId) {
        return disputeDao.listByUser(userId);
    }

    @Override
    public List<Dispute> listByOrder(Integer orderId) {
        return disputeDao.listByOrder(orderId);
    }

    @Override
    public Dispute getById(Integer id) {
        return disputeDao.getById(id);
    }

    @Override
    public List<Dispute> findByUserId(Integer userId) {
        return disputeDao.listByUser(userId);
    }

    @Override
    public List<Dispute> findByOrderId(Integer orderId) {
        return disputeDao.listByOrder(orderId);
    }

    @Override
    public void resolveDispute(Integer disputeId, Integer adminId, String resolution) {
        Dispute dispute = disputeDao.getById(disputeId);
        if (dispute != null) {
            dispute.setStatus("resolved");
            dispute.setAdminId(adminId);
            dispute.setResolution(resolution);
            disputeDao.update(dispute);
        }
    }

    @Override
    public void save(Dispute dispute) {
        disputeDao.insert(dispute);
    }
}