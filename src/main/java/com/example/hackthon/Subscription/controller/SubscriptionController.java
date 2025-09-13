package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.Plan;
import com.example.hackthon.Subscription.model.Subscription;
import com.example.hackthon.Subscription.model.SubscriptionStatus;
import com.example.hackthon.Subscription.model.User;
import com.example.hackthon.Subscription.repository.PlanRepository;
import com.example.hackthon.Subscription.repository.SubscriptionRepository;
import com.example.hackthon.Subscription.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOwnSubscriptions(@RequestParam Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Map<String, Object> response = new HashMap<>();
        if (user.isPresent()) {
            List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user.get());
            response.put("success", true);
            response.put("message", "Subscriptions retrieved successfully");
            response.put("data", subscriptions);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> subscribeToPlan(@RequestParam Long userId, @RequestParam Long planId) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = userRepository.findById(userId);
        Optional<Plan> plan = planRepository.findById(planId);

        if (user.isPresent() && plan.isPresent()) {
            Subscription subscription = new Subscription();
            subscription.setUser(user.get());
            subscription.setPlan(plan.get());
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(1)); // Default 1-month subscription
            subscription.setStatus(SubscriptionStatus.ACTIVE);
            subscription.setLastModified(LocalDateTime.now());

            Subscription savedSubscription = subscriptionRepository.save(subscription);
            response.put("success", true);
            response.put("message", "Subscription created successfully");
            response.put("data", savedSubscription);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("success", false);
            response.put("message", "User or Plan not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}/upgrade")
    public ResponseEntity<Map<String, Object>> upgradeSubscription(@PathVariable Long id, @RequestParam Long newPlanId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(id);
        Optional<Plan> newPlan = planRepository.findById(newPlanId);

        if (existingSubscription.isPresent() && newPlan.isPresent()
                && newPlan.get().getPrice() > existingSubscription.get().getPlan().getPrice()) {
            Subscription subscription = existingSubscription.get();
            subscription.setPlan(newPlan.get());
            subscription.setLastModified(LocalDateTime.now());

            Subscription updatedSubscription = subscriptionRepository.save(subscription);
            response.put("success", true);
            response.put("message", "Subscription upgraded successfully");
            response.put("data", updatedSubscription);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Subscription or new plan not found, or new plan price is not higher");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelSubscription(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(id);

        if (existingSubscription.isPresent()) {
            Subscription subscription = existingSubscription.get();
            subscription.setStatus(SubscriptionStatus.CANCELLED);
            subscription.setLastModified(LocalDateTime.now());

            Subscription updatedSubscription = subscriptionRepository.save(subscription);
            response.put("success", true);
            response.put("message", "Subscription cancelled successfully");
            response.put("data", updatedSubscription);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Subscription not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getAllSubscriptions() {
        Map<String, Object> response = new HashMap<>();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        response.put("success", true);
        response.put("message", "All subscriptions retrieved successfully");
        response.put("data", subscriptions);
        return ResponseEntity.ok(response);
    }
}
