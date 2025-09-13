package com.example.hackthon.Subscription.repository;

import com.example.hackthon.Subscription.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    boolean existsByNameIgnoreCase(String name);
    Optional<Plan> findByNameIgnoreCase(String name);
    List<Plan> findByActiveTrue();
    List<Plan> findByPriceBetween(Double minPrice, Double maxPrice);
}