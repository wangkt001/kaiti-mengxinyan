package com.campus.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.secondhand.dao.DisputeDao;
import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.DisputeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisputeServiceImpl extends ServiceImpl<DisputeDao, Dispute> implements DisputeService {

    @Override
    public List<Dispute> findByUserId(Integer userId) {
        QueryWrapper<Dispute> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Dispute> findByOrderId(Integer orderId) {
        QueryWrapper<Dispute> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void resolveDispute(Integer disputeId, Integer adminId, String resolution) {
        Dispute dispute = baseMapper.selectById(disputeId);
        if (dispute != null) {
            dispute.setStatus("resolved");
            dispute.setAdminId(adminId);
            dispute.setResolution(resolution);
            baseMapper.updateById(dispute);
        }
    }
}
