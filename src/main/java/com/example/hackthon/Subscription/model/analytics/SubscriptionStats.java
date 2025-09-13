package com.example.hackthon.Subscription.model.analytics;

public class SubscriptionStats {
    private int totalSubscriptions;
    private int activeSubscriptions;
    private int cancelledSubscriptions;

    public SubscriptionStats() {}

    public SubscriptionStats(int totalSubscriptions, int activeSubscriptions, int cancelledSubscriptions) {
        this.totalSubscriptions = totalSubscriptions;
        this.activeSubscriptions = activeSubscriptions;
        this.cancelledSubscriptions = cancelledSubscriptions;
    }

    public int getTotalSubscriptions() {
        return totalSubscriptions;
    }

    public void setTotalSubscriptions(int totalSubscriptions) {
        this.totalSubscriptions = totalSubscriptions;
    }

    public int getActiveSubscriptions() {
        return activeSubscriptions;
    }

    public void setActiveSubscriptions(int activeSubscriptions) {
        this.activeSubscriptions = activeSubscriptions;
    }

    public int getCancelledSubscriptions() {
        return cancelledSubscriptions;
    }

    public void setCancelledSubscriptions(int cancelledSubscriptions) {
        this.cancelledSubscriptions = cancelledSubscriptions;
    }

    @Override
    public String toString() {
        return "SubscriptionStats{" +
                "totalSubscriptions=" + totalSubscriptions +
                ", activeSubscriptions=" + activeSubscriptions +
                ", cancelledSubscriptions=" + cancelledSubscriptions +
                '}';
    }
}
