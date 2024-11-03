package com.example.mad_1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DonateBloodActivity extends AppCompatActivity {

    private EditText etDonorName, etDonorContact, etDonationAmount, etNotes;
    private Spinner spnBloodType;
    private Button btnSubmitDonation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);

        // Initialize views
        etDonorName = findViewById(R.id.etDonorName);
        etDonorContact = findViewById(R.id.etDonorContact);
        etDonationAmount = findViewById(R.id.etDonationAmount);
        etNotes = findViewById(R.id.etNotes);
        spnBloodType = findViewById(R.id.spnBloodType);
        btnSubmitDonation = findViewById(R.id.btnSubmitDonation);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up the Spinner with blood types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodType.setAdapter(adapter);

        // Set click listener for submit button
        btnSubmitDonation.setOnClickListener(v -> submitDonation());
    }

    private void submitDonation() {
        String donorName = etDonorName.getText().toString().trim();
        String donorContact = etDonorContact.getText().toString().trim();
        String bloodType = spnBloodType.getSelectedItem().toString();
        String notes = etNotes.getText().toString().trim();

        // Validate input fields
        if (donorName.isEmpty() || donorContact.isEmpty() || etDonationAmount.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate donation amount
        try {
            int donationAmount = Integer.parseInt(etDonationAmount.getText().toString().trim());

            // Insert the donation into the database and update the stock
            boolean isInserted = databaseHelper.saveBloodDonation(donorName, donorContact, bloodType, donationAmount, notes);

            if (isInserted) {
                Toast.makeText(this, "Donation submitted successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to submit donation", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid donation amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etDonorName.setText("");
        etDonorContact.setText("");
        etDonationAmount.setText("");
        etNotes.setText("");
        spnBloodType.setSelection(0);
    }
}