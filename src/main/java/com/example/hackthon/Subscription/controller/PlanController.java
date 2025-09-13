package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.Plan;
import com.example.hackthon.Subscription.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<?> addPlan(@RequestBody Plan plan) {
        try {
            Plan createdPlan = planService.addPlan(plan);
            return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public List<Plan> getAllPlans(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        if (minPrice != null && maxPrice != null) {
            return planService.getPlansByPriceRange(minPrice, maxPrice);
        } else {
            return planService.getAllPlans();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        Plan plan = planService.getPlanById(id);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
        try {
            Plan updatedPlan = planService.updatePlan(id, plan);
            if (updatedPlan == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedPlan);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        boolean deleted = planService.deletePlan(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
