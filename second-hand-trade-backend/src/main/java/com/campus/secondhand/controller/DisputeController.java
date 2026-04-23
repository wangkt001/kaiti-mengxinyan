package com.campus.secondhand.controller;

import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> add(@RequestBody Dispute dispute, @RequestHeader("X-User-Id") Integer userId) {
        try {
            dispute.setUserId(userId);
            dispute.setStatus("pending");
            disputeService.save(dispute);
            return ResponseEntity.ok(dispute);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("/resolve")
    public ResponseEntity<?> resolve(@RequestParam Integer disputeId, @RequestParam String resolution,
            @RequestHeader("X-User-Id") Integer adminId) {
        try {
            disputeService.resolveDispute(disputeId, adminId, resolution);
            Map<String, Object> result = new HashMap<>();
            result.put("message", "处理成功");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
}
}
