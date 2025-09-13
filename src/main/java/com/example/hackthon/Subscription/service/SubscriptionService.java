package com.example.hackthon.Subscription.service;

import com.example.hackthon.Subscription.model.Plan;
import com.example.hackthon.Subscription.model.Subscription;
import com.example.hackthon.Subscription.model.SubscriptionStatus;
import com.example.hackthon.Subscription.model.User;
import com.example.hackthon.Subscription.repository.PlanRepository;
import com.example.hackthon.Subscription.repository.SubscriptionRepository;
import com.example.hackthon.Subscription.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    public Subscription subscribe(Long userId, Long planId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(1));
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setAutoRenew(true);
        subscription.setLastModified(LocalDateTime.now());

        return subscriptionRepository.save(subscription);
    }

    public Subscription renewSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new RuntimeException("Subscription is not active");
        }

        subscription.setEndDate(subscription.getEndDate().plusMonths(1));
        subscription.setLastModified(LocalDateTime.now());

        return subscriptionRepository.save(subscription);
    }

    public Subscription changePlan(Long subscriptionId, Long newPlanId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        Plan newPlan = planRepository.findById(newPlanId)
                .orElseThrow(() -> new RuntimeException("New plan not found"));

        subscription.setPlan(newPlan);
        subscription.setLastModified(LocalDateTime.now());

        return subscriptionRepository.save(subscription);
    }

    public boolean cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElse(null);
        if (subscription == null || subscription.getStatus() == SubscriptionStatus.CANCELLED) {
            return false;
        }
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setLastModified(LocalDateTime.now());

        subscriptionRepository.save(subscription);
        return true;
    }

    public List<Subscription> getSubscriptionsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return subscriptionRepository.findAllByUser(user);
    }
}
