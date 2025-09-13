package com.example.hackthon.Subscription.model.analytics;

public class Suggestions {
    private String message;

    public Suggestions() {}

    public Suggestions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Suggestions{" +
                "message='" + message + '\'' +
                '}';
    }
}
