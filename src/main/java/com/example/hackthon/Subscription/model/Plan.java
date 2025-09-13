package com.example.hackthon.Subscription.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;


    private boolean active;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Discount> discounts;

    public Plan() {
    }

    public Plan(Long id, String name, String description, Double price, Integer dataQuotaInGB, String productType, boolean active, List<Subscription> subscriptions, List<Discount> discounts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;

        this.active = active;
        this.subscriptions = subscriptions;
        this.discounts = discounts;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptionxs(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }
}
