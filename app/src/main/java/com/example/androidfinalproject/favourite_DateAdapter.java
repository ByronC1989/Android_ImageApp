package com.example.androidfinalproject;
import androidx.appcompat.app.AppCompatActivity;

public class favourite_DateAdapter extends AppCompatActivity {
        private  int ID;
        private String date;
        private boolean isFavourite;
        private String nasaPictureUrl;

        public favourite_DateAdapter(int ID,String date, boolean isFavourite, String nasaPictureUrl) {
            this.ID = ID;
            this.date = date;
            this.isFavourite = isFavourite;
            this.nasaPictureUrl = nasaPictureUrl;
        }

        public String getDate() {
            return date;
        }
        public int getID(){
            return ID;
        }

        public boolean isFavourite() {
            return isFavourite;
        }

        public String getNasaPictureUrl() {
            return nasaPictureUrl;
        }
    }