package com.campus.secondhand.service.impl;

import com.campus.secondhand.dao.DisputeDao;
import com.campus.secondhand.dao.OrderDao;
import com.campus.secondhand.dao.UserDao;
import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.model.Order;
import com.campus.secondhand.model.User;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class DisputeServiceImpl implements DisputeService {

    @Autowired
    private DisputeDao disputeDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

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
        if (dispute == null) {
            throw new RuntimeException("纠纷不存在");
        }
        User admin = userDao.getById(adminId);
        if (admin == null) {
            throw new RuntimeException("当前登录管理员信息已失效，请重新登录后再试");
        }
        if (!"admin".equals(admin.getRole())) {
            throw new RuntimeException("当前用户无权处理纠纷");
        }
        if (resolution == null || resolution.trim().isEmpty()) {
            throw new RuntimeException("处理结果不能为空");
        }
        dispute.setStatus("resolved");
        dispute.setAdminId(adminId);
        dispute.setResolution(resolution.trim());
        dispute.setUpdatedAt(LocalDateTime.now());
        disputeDao.update(dispute);
    }

    @Override
    public void save(Dispute dispute) {
        Order order = orderDao.getById(dispute.getOrderId());
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        boolean isOrderUser = dispute.getUserId().equals(order.getBuyerId())
                || dispute.getUserId().equals(order.getSellerId());
        if (!isOrderUser) {
            throw new RuntimeException("您无权提交该订单的纠纷");
        }
        if (dispute.getTitle() == null || dispute.getTitle().trim().isEmpty()) {
            throw new RuntimeException("请输入纠纷标题");
        }
        if (dispute.getDescription() == null || dispute.getDescription().trim().isEmpty()) {
            throw new RuntimeException("请输入纠纷说明");
        }

        List<Dispute> existingDisputes = disputeDao.listByOrder(dispute.getOrderId());
        boolean hasOpenDispute = existingDisputes.stream()
                .anyMatch(item -> item.getUserId().equals(dispute.getUserId()) && !"resolved".equals(item.getStatus()));
        if (hasOpenDispute) {
            throw new RuntimeException("该订单已有未完成纠纷，请等待处理结果");
        }

        LocalDateTime now = LocalDateTime.now();
        dispute.setCreatedAt(now);
        dispute.setUpdatedAt(now);
        disputeDao.insert(dispute);
    }

    @Override
    public List<Dispute> listAll() {
        return disputeDao.listAll();
    }

    @Override
    public void updateStatus(Integer disputeId, String status) {
        Dispute dispute = disputeDao.getById(disputeId);
        if (dispute == null) {
            throw new RuntimeException("纠纷不存在");
        }
        dispute.setStatus(status);
        dispute.setUpdatedAt(LocalDateTime.now());
        disputeDao.update(dispute);
    }
}
