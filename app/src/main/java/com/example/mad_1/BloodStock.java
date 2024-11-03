package com.example.mad_1;

public class BloodStock {
    private String bloodType;
    private int quantity;

    public BloodStock(String bloodType, int quantity) {
        this.bloodType = bloodType;
        this.quantity = quantity;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getQuantity() {
        return quantity;
    }
}