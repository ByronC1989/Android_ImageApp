package com.example.androidfinalproject;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


public class favourite_date_list extends AppCompatActivity {
    private List<favourite_DateAdapter> SavedDateList;
    private MyListAdapter todoAdapter;
    private MyOpener MyOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_date);

        MyOpener myOpener = new MyOpener(this);

        SQLiteDatabase db = MyOpener.getReadableDatabase();
        Cursor cursor = db.query(MyOpener.TABLE_NAME, null, null, null, null, null, null);
        MyOpener.printCursor(cursor);

        SavedDateList = MyOpener.loadData();
        todoAdapter = new MyListAdapter(SavedDateList);

        ListView savedDateListView = findViewById(R.id.list_view);
        savedDateListView.setAdapter(todoAdapter);

        EditText editText = findViewById(R.id.type_here_edit);
        Switch favouriteSwitch = findViewById(R.id.favourite_switch);
        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            String date = editText.getText().toString();
            boolean isFavourite = favouriteSwitch.isChecked();

            String nasaPictureUrl = getNasaPictureUrl();

//            favourite_DateAdapter savedDate = new favourite_DateAdapter(ID,date,isFavourite, nasaPictureUrl);
//            SavedDateList.add(savedDate);

            MyOpener.addToDB(date, isFavourite, nasaPictureUrl);

            todoAdapter.notifyDataSetChanged();
            editText.setText("");
        });

        savedDateListView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is:" + (position + 1))
                    .setPositiveButton("Yes", (click, arg) -> {
                        int ID = SavedDateList.get(position).getID();

                        MyOpener.deleteFromDB(ID);

                        SavedDateList.remove(position);
                        todoAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
    }

    private String getNasaPictureUrl() {
       return getNasaPictureUrl();
    }

    class MyListAdapter extends BaseAdapter {
        private List<favourite_DateAdapter> SavedDateList;

        public MyListAdapter(List<favourite_DateAdapter> SavedDates) {
            super();
            this.SavedDateList = SavedDates;
        }

        public int getCount() {
            return SavedDateList.size();
        }

        public Object getItem(int position) {
            return SavedDateList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View old, ViewGroup parent) {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            if (newView == null) {
                newView = inflater.inflate(R.layout.favourite_date, parent, false);
            }

            TextView tView = newView.findViewById(R.id.list_content);

            favourite_DateAdapter SavedDates = (favourite_DateAdapter) getItem(position);

            tView.setText(SavedDates.getDate());

            if (SavedDates.isFavourite()) {
                newView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            } else {
                newView.setBackgroundColor(Color.TRANSPARENT);
                tView.setTextColor(Color.BLACK);
            }

            return newView;
        }
    }
}