package com.example.dorenmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="dorenmi";
    public static final String TABLE_NAME="account";
    public static final String TABLE_NAME2="item";
    public static final String TABLE_NAME3="trans";
    public static final String COL_1="email";
    public static final String COL_2="password";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" (email TEXT PRIMARY KEY, password TEXT, role TEXT, vendor TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME2+" (id_item INTEGER PRIMARY KEY, title TEXT, description TEXT, price INTEGER, email TEXT," +
                "FOREIGN KEY(email) REFERENCES emails(email) )");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME3+" (id_trans TEXT PRIMARY KEY, status TEXT, tenant TEXT, renter TEXT, id_item, FOREIGN KEY(id_item) REFERENCES" +
                " items(id_item))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(sqLiteDatabase);
    }

    public long addAccountUser(String email, String role, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("role", role);
        contentValues.put("vendor", "");
        contentValues.put("password", password);
        long register = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return register;
    }

    public long addAccountVendor(String email, String vendor, String role, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("vendor", vendor);
        contentValues.put("role", role);
        contentValues.put("password", password);
        long register = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return register;
    }

    public boolean checkAccount (String email, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String Selection = COL_1 + "=?" + " and " +COL_2 + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, columns, Selection, selectionArgs, null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0){
            return true;
        } else {
            return false;
        }
    }

}
