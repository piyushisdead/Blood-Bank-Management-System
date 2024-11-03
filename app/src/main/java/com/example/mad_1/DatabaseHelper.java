package com.example.mad_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BloodBank.db";
    private static final int DATABASE_VERSION = 2;

    // User Table
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_BLOOD_GROUP = "blood_group";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_PASSWORD = "password";

    // Blood Stock Table
    public static final String TABLE_BLOOD_STOCK = "blood_stock";
    public static final String COLUMN_BLOOD_TYPE = "blood_type";
    public static final String COLUMN_QUANTITY = "quantity";

    // Blood Request Table
    public static final String TABLE_BLOOD_REQUEST = "blood_request";
    public static final String COLUMN_REQUEST_ID = "request_id";
    public static final String COLUMN_REQUEST_BLOOD_TYPE = "blood_type";
    public static final String COLUMN_REQUEST_AMOUNT = "amount";
    public static final String COLUMN_REQUESTER_NAME = "requester_name";
    public static final String COLUMN_REQUESTER_CONTACT = "requester_contact";

    // Blood Donation Table
    public static final String TABLE_BLOOD_DONATION = "blood_donations";
    public static final String COLUMN_DONOR_NAME = "donor_name";
    public static final String COLUMN_DONOR_CONTACT = "donor_contact";
    public static final String COLUMN_DONOR_BLOOD_TYPE = "donor_blood_type";
    public static final String COLUMN_DONATION_AMOUNT = "donation_amount";
    public static final String COLUMN_NOTES = "notes";

    // Blood Bank Branch Table
    public static final String TABLE_BLOOD_BANK_BRANCHES = "blood_bank_branches";
    public static final String COLUMN_BRANCH_NAME = "branch_name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AGE + " INTEGER,"
                + COLUMN_BLOOD_GROUP + " TEXT,"
                + COLUMN_CONTACT + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_BLOOD_STOCK_TABLE = "CREATE TABLE " + TABLE_BLOOD_STOCK + "("
                + COLUMN_BLOOD_TYPE + " TEXT PRIMARY KEY,"
                + COLUMN_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_BLOOD_STOCK_TABLE);

        String CREATE_BLOOD_REQUEST_TABLE = "CREATE TABLE " + TABLE_BLOOD_REQUEST + "("
                + COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_REQUEST_BLOOD_TYPE + " TEXT,"
                + COLUMN_REQUEST_AMOUNT + " INTEGER,"
                + COLUMN_REQUESTER_NAME + " TEXT,"
                + COLUMN_REQUESTER_CONTACT + " TEXT" + ")";
        db.execSQL(CREATE_BLOOD_REQUEST_TABLE);

        String CREATE_BLOOD_DONATION_TABLE = "CREATE TABLE " + TABLE_BLOOD_DONATION + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DONOR_NAME + " TEXT,"
                + COLUMN_DONOR_CONTACT + " TEXT,"
                + COLUMN_DONOR_BLOOD_TYPE + " TEXT,"
                + COLUMN_DONATION_AMOUNT + " INTEGER,"
                + COLUMN_NOTES + " TEXT" + ")";
        db.execSQL(CREATE_BLOOD_DONATION_TABLE);

        String CREATE_BLOOD_BANK_BRANCHES_TABLE = "CREATE TABLE " + TABLE_BLOOD_BANK_BRANCHES + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BRANCH_NAME + " TEXT,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL" + ")";
        db.execSQL(CREATE_BLOOD_BANK_BRANCHES_TABLE);

        addInitialBloodBankBranches(db);
    }

    private void addInitialBloodBankBranches(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_BLOOD_BANK_BRANCHES + " (" + COLUMN_BRANCH_NAME + ", " + COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + ") VALUES ('Branch 1', 37.7749, -122.4194)");
        db.execSQL("INSERT INTO " + TABLE_BLOOD_BANK_BRANCHES + " (" + COLUMN_BRANCH_NAME + ", " + COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + ") VALUES ('Branch 2', 34.0522, -118.2437)");
        db.execSQL("INSERT INTO " + TABLE_BLOOD_BANK_BRANCHES + " (" + COLUMN_BRANCH_NAME + ", " + COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + ") VALUES ('Branch 3', 40.7128, -74.0060)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOOD_STOCK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOOD_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOOD_DONATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOOD_BANK_BRANCHES);
        onCreate(db);
    }

    public boolean addUser(String name, int age, String bloodGroup, String contact, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_BLOOD_GROUP, bloodGroup);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getUserByContact(String contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER, null, COLUMN_CONTACT + "=?", new String[]{contact}, null, null, null);
    }

    public boolean saveBloodDonation(String donorName, String donorContact, String donorBloodType, int donationAmount, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues donationValues = new ContentValues();
        donationValues.put(COLUMN_DONOR_NAME, donorName);
        donationValues.put(COLUMN_DONOR_CONTACT, donorContact);
        donationValues.put(COLUMN_DONOR_BLOOD_TYPE, donorBloodType);
        donationValues.put(COLUMN_DONATION_AMOUNT, donationAmount);
        donationValues.put(COLUMN_NOTES, notes);

        long result = db.insert(TABLE_BLOOD_DONATION, null, donationValues);

        if (result != -1) {
            updateStock(donorBloodType, donationAmount, true);
        }
        db.close();
        return result != -1;
    }

    public boolean addBloodRequest(String bloodType, int amount, String requesterName, String requesterContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REQUEST_BLOOD_TYPE, bloodType);
        values.put(COLUMN_REQUEST_AMOUNT, amount);
        values.put(COLUMN_REQUESTER_NAME, requesterName);
        values.put(COLUMN_REQUESTER_CONTACT, requesterContact);

        long result = db.insert(TABLE_BLOOD_REQUEST, null, values);

        if (result != -1) {
            updateStock(bloodType, amount, false);
        }
        db.close();
        return result != -1;
    }

    public boolean isStockAvailable(String bloodType, int amount) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BLOOD_STOCK, new String[]{COLUMN_QUANTITY},
                COLUMN_BLOOD_TYPE + "=?", new String[]{bloodType}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(0);
            cursor.close();
            return currentQuantity >= amount;
        }
        return false;
    }

    private void updateStock(String bloodType, int amount, boolean isAdding) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_BLOOD_STOCK, new String[]{COLUMN_QUANTITY},
                COLUMN_BLOOD_TYPE + "=?", new String[]{bloodType}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(0);
            ContentValues stockValues = new ContentValues();
            stockValues.put(COLUMN_QUANTITY, isAdding ? (currentQuantity + amount) : (currentQuantity - amount));
            db.update(TABLE_BLOOD_STOCK, stockValues, COLUMN_BLOOD_TYPE + "=?", new String[]{bloodType});
            cursor.close();
        } else if (isAdding) { // If not found in stock, add new entry (only for donation)
            ContentValues stockValues = new ContentValues();
            stockValues.put(COLUMN_BLOOD_TYPE, bloodType);
            stockValues.put(COLUMN_QUANTITY, amount);
            db.insert(TABLE_BLOOD_STOCK, null, stockValues);
        }
        db.close();
    }

    public Cursor getAllBloodStock() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BLOOD_STOCK, null, null, null, null, null, null);
    }

    public interface BloodBankCallback {
        void onResult(List<BloodBank> bloodBanks);
    }

    public void getAllBloodBanks(BloodBankCallback callback) {
        new Thread(() -> {
            List<BloodBank> bloodBanks = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_BRANCH_NAME + ", " + COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + " FROM " + TABLE_BLOOD_BANK_BRANCHES, null);

            if (cursor != null) {
                int branchNameIndex = cursor.getColumnIndexOrThrow(COLUMN_BRANCH_NAME);
                int latitudeIndex = cursor.getColumnIndexOrThrow(COLUMN_LATITUDE);
                int longitudeIndex = cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE);

                while (cursor.moveToNext()) {
                    String branchName = cursor.getString(branchNameIndex);
                    double latitude = cursor.getDouble(latitudeIndex);
                    double longitude = cursor.getDouble(longitudeIndex);
                    bloodBanks.add(new BloodBank(branchName, latitude, longitude));
                }
                cursor.close();
            }
            db.close();
            callback.onResult(bloodBanks);
        }).start();
    }
}