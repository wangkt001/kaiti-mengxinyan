package com.campus.secondhand.controller;

import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispute")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @GetMapping("/user")
    public List<Dispute> listByUser(@RequestHeader("X-User-Id") Integer userId) {
        return disputeService.findByUserId(userId);
    }

    @GetMapping("/order/{id}")
    public List<Dispute> listByOrder(@PathVariable Integer id) {
        return disputeService.findByOrderId(id);
    }

    @PostMapping("/add")
    public Dispute add(@RequestBody Dispute dispute, @RequestHeader("X-User-Id") Integer userId) {
        dispute.setUserId(userId);
        dispute.setStatus("pending");
        disputeService.save(dispute);
        return dispute;
    }

    @PutMapping("/resolve")
    public void resolve(@RequestParam Integer disputeId, @RequestParam String resolution,
            @RequestHeader("X-User-Id") Integer adminId) {
        disputeService.resolveDispute(disputeId, adminId, resolution);
    }
}
