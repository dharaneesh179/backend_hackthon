package com.example.hackthon.Subscription.service;

import com.example.hackthon.Subscription.model.Plan;
import com.example.hackthon.Subscription.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan addPlan(Plan plan) {
        plan.setActive(true);
        return planRepository.save(plan);
    }
    public List<Plan> getAllPlans() {
        return planRepository.findByActiveTrue();
    }

    public Plan getPlanById(Long id) {
        return planRepository.findById(id).orElse(null);
    }

    public Plan updatePlan(Long id, Plan planDetails) {
        Plan existingPlan = planRepository.findById(id).orElse(null);
        if (existingPlan == null) {
            return null;
        }
        existingPlan.setName(planDetails.getName());
        existingPlan.setDescription(planDetails.getDescription());
        existingPlan.setPrice(planDetails.getPrice());
        existingPlan.setActive(planDetails.isActive());

        return planRepository.save(existingPlan);
    }

    public void deletePlan(Long id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
        }
    }
}
