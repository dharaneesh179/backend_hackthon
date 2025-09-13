package com.example.hackthon.Subscription.service;

import com.example.hackthon.Subscription.model.Discount;
import com.example.hackthon.Subscription.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Long discountId, Discount updatedDiscount) {
        Discount existingDiscount = discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        existingDiscount.setCode(updatedDiscount.getCode());
        existingDiscount.setDiscountPercentage(updatedDiscount.getDiscountPercentage());
        existingDiscount.setValidFrom(updatedDiscount.getValidFrom());
        existingDiscount.setValidTo(updatedDiscount.getValidTo());
        existingDiscount.setDescription(updatedDiscount.getDescription());
        existingDiscount.setPlan(updatedDiscount.getPlan());

        return discountRepository.save(existingDiscount);
    }

    public void deleteDiscount(Long discountId) {
        discountRepository.deleteById(discountId);
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }
}
