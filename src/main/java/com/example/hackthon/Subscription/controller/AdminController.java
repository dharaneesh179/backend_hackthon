package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.Discount;
import com.example.hackthon.Subscription.model.Plan;
import com.example.hackthon.Subscription.model.analytics.PlanUsage;
import com.example.hackthon.Subscription.model.analytics.SubscriptionStats;
import com.example.hackthon.Subscription.model.analytics.Suggestions;
import com.example.hackthon.Subscription.service.AnalyticsService;
import com.example.hackthon.Subscription.service.DiscountService;
import com.example.hackthon.Subscription.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final PlanService planService;
    private final DiscountService discountService;
    private final AnalyticsService analyticsService;

    public AdminController(PlanService planService,
                           DiscountService discountService,
                           AnalyticsService analyticsService) {
        this.planService = planService;
        this.discountService = discountService;
        this.analyticsService = analyticsService;
    }

    // --- Plan Management ---
    @PostMapping("/plans")
    public Plan addPlan(@RequestBody Plan plan) {
        return planService.addPlan(plan);
    }

    @PutMapping("/plans/{planId}")
    public Plan updatePlan(@PathVariable Long planId, @RequestBody Plan plan) {
        return planService.updatePlan(planId, plan);
    }

    @DeleteMapping("/plans/{planId}")
    public void deletePlan(@PathVariable Long planId) {
        planService.deletePlan(planId);
    }

    @GetMapping("/plans")
    public List<Plan> getAllPlans() {
        return planService.getAllPlans();
    }

    // --- Discount Management ---
    @PostMapping("/discounts")
    public Discount addDiscount(@RequestBody Discount discount) {
        return discountService.addDiscount(discount);
    }

    @PutMapping("/discounts/{discountId}")
    public Discount updateDiscount(@PathVariable Long discountId, @RequestBody Discount discount) {
        return discountService.updateDiscount(discountId, discount);
    }

    @DeleteMapping("/discounts/{discountId}")
    public void deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
    }

    @GetMapping("/discounts")
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    // --- Analytics ---
    @GetMapping("/analytics/subscription-trends")
    public SubscriptionStats getSubscriptionTrends() {
        return analyticsService.getSubscriptionTrends();
    }

    @GetMapping("/analytics/popular-plans")
    public List<PlanUsage> getPopularPlans() {
        return analyticsService.getPopularPlans();
    }

    // --- AI-powered suggestions ---
    @GetMapping("/analytics/suggestions")
    public Suggestions getAISuggestions() {
        return analyticsService.getAISuggestions();
    }
}
