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
        if (planRepository.existsByNameIgnoreCase(plan.getName())) {
            throw new IllegalArgumentException("Plan with the same name already exists");
        }
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
        // Check if name is changing and if the new name is already taken by another plan
        if (!existingPlan.getName().equalsIgnoreCase(planDetails.getName()) &&
                planRepository.existsByNameIgnoreCase(planDetails.getName())) {
            throw new IllegalArgumentException("Plan with the same name already exists");
        }

        existingPlan.setName(planDetails.getName());
        existingPlan.setDescription(planDetails.getDescription());
        existingPlan.setPrice(planDetails.getPrice());
        existingPlan.setActive(planDetails.isActive());

        return planRepository.save(existingPlan);
    }

    public boolean deletePlan(Long id) {
        if (planRepository.existsById(id)) {
            // Instead of deleting, you could just mark it inactive if you want soft delete
            planRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Plan> getPlansByPriceRange(Double minPrice, Double maxPrice) {
        return planRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
