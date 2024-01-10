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


public class favouritedate_list extends AppCompatActivity {
    private List<fav_list> SavedDateList;
    private MyListAdapter todoAdapter;
    private Database_opener dbOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_date);

        dbOpener = new Database_opener(this);

        SQLiteDatabase db = dbOpener.getReadableDatabase();
        Cursor cursor = db.query(Database_opener.Table_Name, null, null, null, null, null, null);
        dbOpener.printCursor(cursor);

        SavedDateList = dbOpener.loadData();
        todoAdapter = new MyListAdapter(SavedDateList);

        ListView SavedDateList = findViewById(R.id.list_view);
        SavedDateList.setAdapter(todoAdapter);

        dbOpener.loadData();
        todoAdapter.notifyDataSetChanged();

        EditText editText = findViewById(R.id.type_here_edit);
        Switch FavouriteSwitch = findViewById(R.id.favourite_switch);
        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            String itemText = editText.getText().toString();
            boolean isFavourite = FavouriteSwitch.isChecked();

            fav_list SavedDate = new fav_list(itemText, isFavourite, 0);
            SavedDateList.add(SavedDate);

            dbOpener.addToDB(itemText, isFavourite);

            todoAdapter.notifyDataSetChanged();
            editText.setText("");
        });

        SavedDateList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is:" + (position + 1))
                    .setPositiveButton("Yes", (click, arg) -> {
                        int itemId = SavedDateList.get(position).getId();

                        dbOpener.deleteFromDB(itemId);

                        SavedDateList.remove(position);
                        todoAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .create().show();
            return true;
        });
    }

    class MyListAdapter extends BaseAdapter {
        private List<fav_list> SavedDateList;

        public MyListAdapter(List<fav_list> todoItemList) {
            super();
            this.SavedDateList = todoItemList;
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

            fav_list todoItem = (fav_list) getItem(position);

            tView.setText(todoItem.getItemText());

            if (SavedDateList.isFavourite()) {
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