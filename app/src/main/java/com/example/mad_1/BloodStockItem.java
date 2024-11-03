package com.example.mad_1;

public class BloodStockItem {
    private String bloodType;
    private int quantity;
    private String donorName;
    private String contactNumber;

    // Constructor
    public BloodStockItem(String bloodType, int quantity, String donorName, String contactNumber) {
        this.bloodType = bloodType;
        this.quantity = quantity;
        this.donorName = donorName;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getBloodType() {
        return bloodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Optionally, you can add setters if you need to modify these fields after creation
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}