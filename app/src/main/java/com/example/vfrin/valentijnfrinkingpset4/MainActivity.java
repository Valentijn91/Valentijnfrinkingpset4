package com.example.vfrin.valentijnfrinkingpset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter adapter;
    private ListView itemList;
    private DBmanager dbManager;
    private String[] from = new String[] {DatabaseHelper.SUBJECT};
    private int[] to = new int[] {R.id.textitem};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemList = (ListView) findViewById(R.id.itemList);
        getList();
        listViewListener();
    }

    /* Deletes the list item when long clicken on an item. */
    private void listViewListener() {
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbManager.delete(id);
                getList();
                Toast.makeText(MainActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /* Adds an item in the editbox when pressed on the add button. */
    public void addItem(View v) {
        EditText text = (EditText) findViewById(R.id.textNewItem);
        String addtext = text.getText().toString();
        /* Gives notification when the item added is an empty string. */
        if(addtext.equals("")){
            Toast.makeText(this, "Empty item", Toast.LENGTH_SHORT).show();
        }
        else {
            dbManager.insert(addtext);
            getList();
            text.setText("");
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
        }
    }

    /* Opens the Database and retrieves all the items in the list view. */
    public void getList() {
        dbManager = new DBmanager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        adapter = new SimpleCursorAdapter(this, R.layout.item_in_list, cursor, from, to);
        adapter.notifyDataSetChanged();
        itemList.setAdapter(adapter);
    }

}