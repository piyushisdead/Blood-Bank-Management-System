package com.example.mad_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnViewBloodStock;
    private Button btnRequestBlood;
    private Button btnDonateBlood; // Button for donation
    private Button btnFindBloodBanks; // New button for nearby blood banks
    private TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        btnViewBloodStock = findViewById(R.id.btnViewBloodStock);
        btnRequestBlood = findViewById(R.id.btnRequestBlood);
        btnDonateBlood = findViewById(R.id.btnDonateBlood);
        btnFindBloodBanks = findViewById(R.id.btnFindBloodBanks); // Initialize the new button

        // Set welcome message
        tvWelcomeMessage.setText("Welcome to the Blood Bank Management System");

        // Set click listeners for each button
        btnRequestBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, RequestBloodActivity.class);
                startActivity(intent);
            }
        });

        btnDonateBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DonateBloodActivity.class);
                startActivity(intent);
            }
        });

        btnViewBloodStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ViewBloodStockActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the Nearby Blood Banks button
        btnFindBloodBanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NearbyBloodBanksActivity.class);
                startActivity(intent);
            }
        });
    }
}