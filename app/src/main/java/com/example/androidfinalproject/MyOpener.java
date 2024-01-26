package com.example.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class creates the Database on the phone and
 *  stores methods for CRUD operations
 *
 * @author Byron, Alycia
 */
public class MyOpener extends SQLiteOpenHelper {
    protected static String DATABASE_NAME = "NasaDB";
    protected static int VERSION_NUM = 1;
    public static String TABLE_NAME = "NASA_IMAGES";
    public static String COL_DATE = "date";
    public static String COL_URL = "url";
    public static String COL_HDURL = "hd_url";
    public static String COL_FILEPATH = "filepath";
    public static String COL_TITLE = "title";
    public MyOpener(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    //Called when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COL_DATE + " TEXT,"
                + COL_URL + " TEXT,"
                + COL_TITLE + " TEXT,"
                + COL_FILEPATH + " TEXT,"
                + COL_HDURL + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Load data from the database and return a list of saved dates
    public List<NasaImage> loadData() {
        List<NasaImage> SavedDates = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String[] columns = {COL_DATE, COL_TITLE, COL_FILEPATH, COL_URL,COL_HDURL};
        Cursor results = db.query(false, TABLE_NAME, columns, null, null, null, null, null, null);

        int DateColumnIndex = results.getColumnIndex(COL_DATE);
        int TitleColumnIndex = results.getColumnIndex(COL_TITLE);
        int FilePathColumnIndex = results.getColumnIndex(COL_FILEPATH);
        int UrlColumnIndex = results.getColumnIndex(COL_URL);
        int HdUrlColumnIndex = results.getColumnIndex(COL_HDURL);



        // Iterate through the database results and create saved date objects
        while (results.moveToNext()) {
            String Date = results.getString(DateColumnIndex);
            String Title = results.getString(TitleColumnIndex);
            String FilePath = results.getString(FilePathColumnIndex);
            String Url = results.getString(UrlColumnIndex);
            String HdUrl = results.getString(HdUrlColumnIndex);


            NasaImage savedDate = new NasaImageBuilder().setDate(Date).setUrl(Url).setHdUrl(HdUrl).setTitle(Title).setFilePath(FilePath).createNasaImage();
            SavedDates.add(savedDate);
        }
        results.close();
        db.close();
        return SavedDates;
    }

    // Add a date to the database
    public void addToDB(String date, String title, String filepath, String url, String hdUrl) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        values.put(COL_TITLE, title);
        values.put(COL_FILEPATH, filepath);
        values.put(COL_URL, url);
        values.put(COL_HDURL, hdUrl);

        db.insert(TABLE_NAME, null, values);
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        printCursor(cursor);
        cursor.close();
        db.close();
    }

    // Delete a date from the database by Date
    public void deleteFromDB(String Date) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COL_DATE + "=?", new String[]{String.valueOf(Date)});
        db.close();
    }

    // Print cursor information for debugging
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