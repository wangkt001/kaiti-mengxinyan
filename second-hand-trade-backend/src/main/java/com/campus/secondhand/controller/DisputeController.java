package com.campus.secondhand.controller;

import com.campus.secondhand.model.Dispute;
import com.campus.secondhand.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/dispute")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @GetMapping("/user")
    public List<Dispute> listByUser(HttpSession session) {
        Integer userId = ((com.campus.secondhand.model.User) session.getAttribute("user")).getId();
        return disputeService.findByUserId(userId);
    }

    @GetMapping("/order/{id}")
    public List<Dispute> listByOrder(@PathVariable Integer id) {
        return disputeService.findByOrderId(id);
    }

    @PostMapping("/add")
    public Dispute add(@RequestBody Dispute dispute, HttpSession session) {
        dispute.setUserId(((com.campus.secondhand.model.User) session.getAttribute("user")).getId());
        dispute.setStatus("pending");
        disputeService.save(dispute);
        return dispute;
    }

    @PutMapping("/resolve")
    public void resolve(@RequestParam Integer disputeId, @RequestParam String resolution, HttpSession session) {
        Integer adminId = ((com.campus.secondhand.model.User) session.getAttribute("user")).getId();
        disputeService.resolveDispute(disputeId, adminId, resolution);
    }
}
