package com.example.mad_1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private DatabaseHelper dbHelper;
    private TextInputEditText etContact, etPassword;
    private MaterialButton btnLogin, btnGoToRegister;
    private ImageView profileImage;
    private Button btnCaptureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: MainActivity started.");

        // Initialize views
        dbHelper = new DatabaseHelper(this);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        profileImage = findViewById(R.id.profileImage);
        btnCaptureImage = findViewById(R.id.btnCaptureImage);

        // Set up button listeners
        btnGoToRegister.setOnClickListener(v -> {
            Log.d(TAG, "Register button clicked.");
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        btnCaptureImage.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }
        });

        btnLogin.setOnClickListener(v -> {
            String contact = etContact.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            Log.d(TAG, "Login button clicked. Contact: " + contact);

            // Validate login credentials
            Cursor userCursor = dbHelper.getUserByContact(contact);
            if (userCursor != null && userCursor.moveToFirst()) {
                @SuppressLint("Range") String storedPassword = userCursor.getString(userCursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
                Log.d(TAG, "User found in database. Stored Password: " + storedPassword);

                if (password.equals(storedPassword)) {
                    Log.d(TAG, "Password matches. Login successful.");

                    // Save login state in SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    // Navigate to Dashboard
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Log.w(TAG, "Invalid password entered.");
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                }
                userCursor.close();
            } else {
                Log.w(TAG, "User not found with contact: " + contact);
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to take profile picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
        }
    }
}