package com.example.hackthon.Subscription.model.analytics;

public class PlanUsage {
    private String planName;
    private int activeUsers;

    public PlanUsage() {}

    public PlanUsage(String planName, int activeUsers) {
        this.planName = planName;
        this.activeUsers = activeUsers;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    @Override
    public String toString() {
        return "PlanUsage{" +
                "planName='" + planName + '\'' +
                ", activeUsers=" + activeUsers +
                '}';
    }
}
