package com.example.hackthon.Subscription.repository;

import com.example.hackthon.Subscription.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Optional<Discount> findByCode(String code);

    // Remove the findByActiveTrue method
    List<Discount> findAll();  // Just fetch all discounts
}
