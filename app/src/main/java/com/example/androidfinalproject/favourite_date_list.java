package com.example.androidfinalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class favourite_date_list extends AppCompatActivity {
    private List<NasaImage> SavedDateList;
    private MyListAdapter NasaImage;
    private MyOpener myOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_date);

        // Initialize database opener
        myOpener = new MyOpener(this);

        // Load saved dates from the database
        SavedDateList = myOpener.loadData();
        // Adapter for the ListView
        NasaImage = new MyListAdapter(SavedDateList);

        // Set up ListView
        ListView savedDateListView = findViewById(R.id.list_view);
        savedDateListView.setAdapter(NasaImage);

        // Receive the date information from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("date")) {
            String date = intent.getStringExtra("date");
            String title = intent.getStringExtra("title");
            String hdUrl = intent.getStringExtra("hdUrl");

            // Use date as ID
            NasaImage savedDate = new NasaImageBuilder().setDate(date).setUrl(title).setHdUrl(hdUrl).setTitle("").setFilePath("").createNasaImage();
            SavedDateList.add(savedDate);

            // Add the new date to the database
            myOpener.addToDB(date, "", "", getNasaPictureUrl(date));

            // Update the ListView
            NasaImage.notifyDataSetChanged();
        }


        // Set long click listener for items in the ListView
        savedDateListView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Display a confirmation for deleting a date
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is:" + (position + 1))
                    .setPositiveButton("Yes", (click, arg) -> {
                        String Date = SavedDateList.get(position).getDate();

                        // Delete the date from the database
                        myOpener.deleteFromDB(Date);

                        // Remove the date from the list and update the ListView
                        SavedDateList.remove(position);
                        NasaImage.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
    }

    // Construct the URL for NASA picture based on the date
    public static String getNasaPictureUrl(String date) {
        return "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + date;
    }

    // Adapter class for the ListView
    class MyListAdapter extends BaseAdapter {
        private List<NasaImage> savedDateList;

        public MyListAdapter(List<NasaImage> savedDates) {
            super();
            this.savedDateList = savedDates;
        }

        public int getCount() {
            return savedDateList.size();
        }

        public Object getItem(int position) {
            return savedDateList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            // Inflate layout
            if (newView == null) {
                newView = inflater.inflate(R.layout.favouritedate_list, parent, false);
            }

            // Reference to TextView in the layout
            TextView tView = newView.findViewById(R.id.list_content);

            // Get current saved date object
            NasaImage savedDate = (NasaImage) getItem(position);

            // Set date text in the TextView
            tView.setText(savedDate.getDate());

            return newView;
        }
    }
}