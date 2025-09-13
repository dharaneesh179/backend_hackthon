package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.Discount;
import com.example.hackthon.Subscription.service.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@CrossOrigin(origins = "*")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public Discount addDiscount(@RequestBody Discount discount) {
        return discountService.addDiscount(discount);
    }

    @PutMapping("/{discountId}")
    public Discount updateDiscount(@PathVariable Long discountId, @RequestBody Discount discount) {
        return discountService.updateDiscount(discountId, discount);
    }

    @DeleteMapping("/{discountId}")
    public void deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
    }

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }
}
