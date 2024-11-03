package com.example.mad_1;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewBloodStockActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BloodStockAdapter adapter;
    private DatabaseHelper dbHelper;
    private static final String TAG = "ViewBloodStockActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood_stock);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        loadBloodStockData();
    }

    private void loadBloodStockData() {
        Cursor cursor = dbHelper.getAllBloodStock(); // Using getAllBloodStock to retrieve data
        List<BloodStockItem> bloodStockList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int bloodTypeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BLOOD_TYPE);
            int quantityIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY);

            if (bloodTypeIndex >= 0 && quantityIndex >= 0) {
                do {
                    String bloodType = cursor.getString(bloodTypeIndex);
                    int quantity = cursor.getInt(quantityIndex);

                    // Add each item to the list, without donor details since this is stock data
                    bloodStockList.add(new BloodStockItem(bloodType, quantity, "", ""));
                } while (cursor.moveToNext());
            } else {
                Log.e(TAG, "Invalid column indices for blood type or quantity.");
            }
            cursor.close();
        } else {
            Log.w(TAG, "No blood stock data available.");
            Toast.makeText(this, "No blood stock data available.", Toast.LENGTH_SHORT).show();
        }

        adapter = new BloodStockAdapter(bloodStockList);
        recyclerView.setAdapter(adapter);
    }
}