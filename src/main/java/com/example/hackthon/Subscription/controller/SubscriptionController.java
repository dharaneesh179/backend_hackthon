package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.Subscription;
import com.example.hackthon.Subscription.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Long userId, @RequestParam Long planId) {
        try {
            Subscription subscription = subscriptionService.subscribe(userId, planId);
            return new ResponseEntity<>(subscription, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{subscriptionId}/renew")
    public ResponseEntity<?> renewSubscription(@PathVariable Long subscriptionId) {
        try {
            Subscription renewedSubscription = subscriptionService.renewSubscription(subscriptionId);
            return ResponseEntity.ok(renewedSubscription);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{subscriptionId}/change-plan")
    public ResponseEntity<?> changePlan(@PathVariable Long subscriptionId, @RequestParam Long newPlanId) {
        try {
            Subscription updatedSubscription = subscriptionService.changePlan(subscriptionId, newPlanId);
            return ResponseEntity.ok(updatedSubscription);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{subscriptionId}/cancel")
    public ResponseEntity<?> cancelSubscription(@PathVariable Long subscriptionId) {
        boolean cancelled = subscriptionService.cancelSubscription(subscriptionId);
        if (cancelled) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Subscription not found or already cancelled");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getSubscriptionsForUser(@PathVariable Long userId) {
        try {
            List<Subscription> subscriptions = subscriptionService.getSubscriptionsForUser(userId);
            return ResponseEntity.ok(subscriptions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
