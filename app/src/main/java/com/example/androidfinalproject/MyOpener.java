package com.example.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyOpener extends SQLiteOpenHelper {
    // database definition variables.
    protected static String DATABASE_NAME = "NasaDB";
    protected static int VERSION_NUM = 1;
    public static String TABLE_NAME = "NASA_IMAGES";
    public static String COL_ID = "_id";
    public static String COL_DATE = "date";
    public static String COL_URL = "url";
    public static String COL_HDURL = "hd_url";
    public static String FAVOURITE = "FAVOURITE";


    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATE + " TEXT,"
            + COL_URL + " TEXT,"
            + FAVOURITE + " INTEGER,"
            + COL_HDURL + " TEXT);");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<favourite_DateAdapter> loadData() {
        List<favourite_DateAdapter> SavedDates = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String[] columns = {MyOpener.COL_ID, COL_DATE, FAVOURITE};
        Cursor results = db.query(false, TABLE_NAME, columns, null, null, null, null, null, null);

        int IDColumnIndex = results.getColumnIndex(COL_ID);
        int DateColumnIndex = results.getColumnIndex(COL_DATE);
        int FavouriteColumnIndex = results.getColumnIndex(FAVOURITE);

        while (results.moveToNext()) {
            int ID = results.getInt(IDColumnIndex);
            String Date = results.getString(DateColumnIndex);
            int FavouriteInt = results.getInt(FavouriteColumnIndex);
            boolean FAVOURITE = (FavouriteInt == 1);

//            favourite_DateAdapter Favourite_item = new favourite_DateAdapter(COL_ID,FAVOURITE,COL_DATE);
//            SavedDates.add(Favourite_item);
        }

        results.close();
        db.close();

        return SavedDates;
    }

    public void addToDB(String date, boolean isFavourite, String nasaPictureUrl) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(FAVOURITE, isFavourite ? 1 : 0);
        values.put(COL_URL, nasaPictureUrl);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void deleteFromDB(int ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COL_DATE + "=?", new String[]{String.valueOf(ID)});
        db.close();
    }

    public void printCursor(Cursor cursor) {
        SQLiteDatabase db = getWritableDatabase();
        Log.d("Opener", "Database Version: " + db.getVersion());

        Log.d("Opener", "Number of Columns: " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.d("Opener", "Column " + i + ": " + cursor.getColumnName(i));
        }

        Log.d("Opener", "Number of Results: " + cursor.getCount());

        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d("Opener", "Row " + cursor.getPosition() + ", Column " + i + ": " + cursor.getString(i));
            }
        }

        cursor.close();
        db.close();
    }
}