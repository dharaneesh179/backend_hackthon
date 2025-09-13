package com.example.hackthon.Subscription.repository;

import com.example.hackthon.Subscription.model.Subscription;
import com.example.hackthon.Subscription.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Find all subscriptions for a specific user
    List<Subscription> findAllByUser(User user);
}
