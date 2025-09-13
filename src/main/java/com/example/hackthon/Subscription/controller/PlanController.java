package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.Plan;
import com.example.hackthon.Subscription.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "*")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    // Get all active plans
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPlans() {
        List<Plan> plans = planRepository.findByActiveTrue();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Plans retrieved successfully");
        response.put("data", plans);
        return ResponseEntity.ok(response);
    }

    // Get plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPlanById(@PathVariable Long id) {
        Optional<Plan> plan = planRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (plan.isPresent()) {
            response.put("success", true);
            response.put("message", "Plan retrieved successfully");
            response.put("data", plan.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Plan not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Create plan (Admin) - REMOVED @Valid
    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> createPlan(@RequestBody Plan plan) {
        Map<String, Object> response = new HashMap<>();

        if (planRepository.existsByNameIgnoreCase(plan.getName())) {
            response.put("success", false);
            response.put("message", "Plan name already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        plan.setActive(true);
        Plan savedPlan = planRepository.save(plan);
        response.put("success", true);
        response.put("message", "Plan created successfully");
        response.put("data", savedPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update plan (Admin) - REMOVED @Valid
    @PutMapping("/admin/{id}")
    public ResponseEntity<Map<String, Object>> updatePlan(@PathVariable Long id, @RequestBody Plan planDetails) {
        Map<String, Object> response = new HashMap<>();
        Optional<Plan> existingPlan = planRepository.findById(id);

        if (!existingPlan.isPresent()) {
            response.put("success", false);
            response.put("message", "Plan not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Plan plan = existingPlan.get();
        plan.setName(planDetails.getName());
        plan.setDescription(planDetails.getDescription());
        plan.setPrice(planDetails.getPrice());
        plan.setActive(planDetails.isActive());

        Plan updatedPlan = planRepository.save(plan);
        response.put("success", true);
        response.put("message", "Plan updated successfully");
        response.put("data", updatedPlan);
        return ResponseEntity.ok(response);
    }

    // Get admin stats
    @GetMapping("/admin/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long totalPlans = planRepository.count();
        long activePlans = planRepository.findByActiveTrue().size();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPlans", totalPlans);
        stats.put("activePlans", activePlans);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Stats retrieved successfully");
        response.put("data", stats);
        return ResponseEntity.ok(response);
    }
}