package com.example.mad_1;

public class BloodBank {
    private String branchName;
    private double latitude;
    private double longitude;

    public BloodBank(String branchName, double latitude, double longitude) {
        this.branchName = branchName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBranchName() {
        return branchName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
