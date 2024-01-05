package com.example.androidfinalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    // database definition variables.
    protected final static String DATABASE_NAME = "NasaDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "NASA_IMAGES";
    public final static String COL_ID = "_id";
    public final static String COL_DATE = "date";
    public final static String COL_URL = "url";
    public final static String COL_HDURL = "hd_url";

    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table in database.
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE + " text,"
                + COL_URL  + " text,"
                + COL_HDURL + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old database table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create new table
        onCreate(db);
    }
}
