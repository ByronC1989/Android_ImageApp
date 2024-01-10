package com.example.androidfinalproject;
import androidx.appcompat.app.AppCompatActivity;

public class favourite_DateAdapter extends AppCompatActivity {
        private String date;
        private boolean isFavourite;
        private String nasaPictureUrl;

        public favourite_DateAdapter(String date, boolean isFavourite, String nasaPictureUrl) {
            this.date = date;
            this.isFavourite = isFavourite;
            this.nasaPictureUrl = nasaPictureUrl;
        }

        public String getDate() {
            return date;
        }

        public boolean isFavourite() {
            return isFavourite;
        }

        public String getNasaPictureUrl() {
            return nasaPictureUrl;
        }
    }