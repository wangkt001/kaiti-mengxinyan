package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.DisputeDao;
import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

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

    @Override
    public List<Dispute> listAll() {
        // 返回模拟数据
        List<Dispute> disputes = new ArrayList<>();

        Dispute dispute1 = new Dispute();
        dispute1.setId(1);
        dispute1.setOrderId(1);
        dispute1.setUserId(1);
        dispute1.setTitle("商品与描述不符");
        dispute1.setDescription("收到的商品与描述的不一致，要求退款");
        dispute1.setStatus("pending");
        dispute1.setCreatedAt(java.time.LocalDateTime.now().minusDays(2));
        dispute1.setUpdatedAt(java.time.LocalDateTime.now().minusDays(2));
        disputes.add(dispute1);

        Dispute dispute2 = new Dispute();
        dispute2.setId(2);
        dispute2.setOrderId(2);
        dispute2.setUserId(2);
        dispute2.setTitle("未收到商品");
        dispute2.setDescription("已经付款但还没有收到商品，要求退款");
        dispute2.setStatus("processing");
        dispute2.setCreatedAt(java.time.LocalDateTime.now().minusDays(1));
        dispute2.setUpdatedAt(java.time.LocalDateTime.now().minusHours(6));
        disputes.add(dispute2);

        Dispute dispute3 = new Dispute();
        dispute3.setId(3);
        dispute3.setOrderId(3);
        dispute3.setUserId(3);
        dispute3.setTitle("商品质量问题");
        dispute3.setDescription("商品质量有问题，要求换货");
        dispute3.setStatus("resolved");
        dispute3.setAdminId(1);
        dispute3.setResolution("已退款");
        dispute3.setCreatedAt(java.time.LocalDateTime.now().minusDays(3));
        dispute3.setUpdatedAt(java.time.LocalDateTime.now().minusDays(1));
        disputes.add(dispute3);

        return disputes;
    }

    @Override
    public void updateStatus(Integer disputeId, String status) {
        Dispute dispute = disputeDao.getById(disputeId);
        if (dispute != null) {
            dispute.setStatus(status);
            disputeDao.update(dispute);
        }
    }
}