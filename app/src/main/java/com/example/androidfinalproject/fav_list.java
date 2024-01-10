package com.example.androidfinalproject;
import androidx.appcompat.app.AppCompatActivity;

public class fav_list extends AppCompatActivity {
    private String Date;
    private int Date_Number;

    public fav_list(String itemText,int Item_Id) {
        this.Date = itemText;
        this.Date_Number = Item_Id;
    }


    public String getItemText() {
        return Date;
    }

    public int getId() {
        return Date_Number;
    }
}