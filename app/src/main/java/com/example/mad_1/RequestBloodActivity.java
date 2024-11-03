package com.example.mad_1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RequestBloodActivity extends AppCompatActivity {

    private Spinner spnBloodType;
    private EditText etRequesterName, etRequesterContact, etRequestAmount;
    private Button btnSubmitRequest;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        // Initialize views
        spnBloodType = findViewById(R.id.spnBloodType);
        etRequesterName = findViewById(R.id.etRequesterName);
        etRequesterContact = findViewById(R.id.etRequesterContact);
        etRequestAmount = findViewById(R.id.etRequestAmount);
        btnSubmitRequest = findViewById(R.id.btnSubmitRequest);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up the Spinner with blood types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBloodType.setAdapter(adapter);

        // Set click listener for submit button
        btnSubmitRequest.setOnClickListener(v -> submitRequest());
    }

    private void submitRequest() {
        String requesterName = etRequesterName.getText().toString().trim();
        String requesterContact = etRequesterContact.getText().toString().trim();
        String bloodType = spnBloodType.getSelectedItem().toString();

        // Validate input fields
        if (requesterName.isEmpty() || requesterContact.isEmpty() || etRequestAmount.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate request amount
        try {
            int requestAmount = Integer.parseInt(etRequestAmount.getText().toString().trim());

            // Check stock availability
            if (!databaseHelper.isStockAvailable(bloodType, requestAmount)) {
                Toast.makeText(this, "Insufficient stock for the requested blood type", Toast.LENGTH_SHORT).show();
                return;
            }

            // Deduct stock and insert the blood request into the database
            boolean isInserted = databaseHelper.addBloodRequest(bloodType, requestAmount, requesterName, requesterContact);

            if (isInserted) {
                Toast.makeText(this, "Request submitted successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to submit request", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid request amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etRequesterName.setText("");
        etRequesterContact.setText("");
        etRequestAmount.setText("");
        spnBloodType.setSelection(0);
    }
}