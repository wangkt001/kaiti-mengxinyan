package com.campus.secondhand.controller;

import com.campus.secondhand.model.Evaluation;
import com.campus.secondhand.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/order/{id}")
    public List<Evaluation> listByOrder(@PathVariable Integer id) {
        return evaluationService.findByOrderId(id);
    }

    @GetMapping("/evaluated/{id}")
    public List<Evaluation> listByEvaluated(@PathVariable Integer id) {
        return evaluationService.findByEvaluatedId(id);
    }

    @PostMapping("/add")
    public Evaluation add(@RequestBody Evaluation evaluation) {
        evaluationService.save(evaluation);
        return evaluation;
    }
}
