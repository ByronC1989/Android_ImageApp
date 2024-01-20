//package com.example.androidfinalproject;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Switch;
//import android.widget.TextView;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import java.util.List;
//
//public class favourite_date_list extends AppCompatActivity {
//    private List<favourite_DateAdapter> SavedDateList;
//    private MyListAdapter todoAdapter;
//    private MyOpener myOpener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.favourite_date);
//
//        //Initialize database opener
//        myOpener = new MyOpener(this);
//
//        //Load saved dates from the database
//        SavedDateList = myOpener.loadData();
//        //adapter for the ListView
//        todoAdapter = new MyListAdapter(SavedDateList);
//
//        //Set up ListView
//        ListView savedDateListView = findViewById(R.id.list_view);
//        savedDateListView.setAdapter(todoAdapter);
//
//        //references to UI elements
//        EditText editText = findViewById(R.id.type_here_edit);
//        Switch favouriteSwitch = findViewById(R.id.favourite_switch);
//        Button addButton = findViewById(R.id.add_button);
//
//        //Set click listener for the "Add" button
//        addButton.setOnClickListener(v -> {
//            String date = editText.getText().toString();
//            boolean isFavourite = favouriteSwitch.isChecked();
//            String nasaPictureUrl = getNasaPictureUrl(date);
//
//            int theId = Id();
//
//            //Create a new date object and add it to the list
//            //favourite_DateAdapter savedDate = new favourite_DateAdapter(theId, date, isFavourite, nasaPictureUrl);
//            //SavedDateList.add(savedDate);
//
//            //Add the new date to the database
//            myOpener.addToDB(date, isFavourite, nasaPictureUrl);
//
//            //Update the ListView
//            todoAdapter.notifyDataSetChanged();
//            editText.setText("");
//        });
//
//        //Set long click listener for items in the ListView
//        savedDateListView.setOnItemLongClickListener((parent, view, position, id) -> {
//            //Display a confirmation for deleting a date
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setTitle("Do you want to delete this?")
//                    .setMessage("The selected row is:" + (position + 1))
//                    .setPositiveButton("Yes", (click, arg) -> {
//                        int ID = SavedDateList.get(position).getID();
//
//                        //Delete the date from the database
//                        myOpener.deleteFromDB(ID);
//
//                        //Remove the date from the list and update the ListView
//                        SavedDateList.remove(position);
//                        todoAdapter.notifyDataSetChanged();
//                    })
//                    .setNegativeButton("No", (click, arg) -> {
//                    })
//                    .create().show();
//            return true;
//        });
//    }
//
//    //construct the URL for NASA picture based on the date
//    public static String getNasaPictureUrl(String date) {
//        return "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + date;
//    }
//
//    //Placeholder method for getting an ID
//    private int Id() {
//        return 0;
//    }
//
//    //Adapter class for the ListView
//    class MyListAdapter extends BaseAdapter {
//        private List<favourite_DateAdapter> SavedDateList;
//
//        public MyListAdapter(List<favourite_DateAdapter> SavedDates) {
//            super();
//            this.SavedDateList = SavedDates;
//        }
//
//        public int getCount() {
//            return SavedDateList.size();
//        }
//
//        public Object getItem(int position) {
//            return SavedDateList.get(position);
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(int position, View old, ViewGroup parent) {
//            View newView = old;
//            LayoutInflater inflater = getLayoutInflater();
//
//            //Inflate layout
//            if (newView == null) {
//                newView = inflater.inflate(R.layout.favourite_date, parent, false);
//            }
//
//            //reference to TextView in the layout
//            TextView tView = newView.findViewById(R.id.list_content);
//
//            // Get current saved date object
//            favourite_DateAdapter SavedDates = (favourite_DateAdapter) getItem(position);
//
//            // Set date text in the TextView
//            tView.setText(SavedDates.getDate());
//
//            // Set background color and text color based on the favorite status
//            if (SavedDates.isFavourite()) {
//                newView.setBackgroundColor(Color.RED);
//                tView.setTextColor(Color.WHITE);
//            } else {
//                newView.setBackgroundColor(Color.TRANSPARENT);
//                tView.setTextColor(Color.BLACK);
//            }
//
//            return newView;
//        }
//    }
//}