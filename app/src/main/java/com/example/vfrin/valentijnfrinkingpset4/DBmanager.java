package com.example.vfrin.valentijnfrinkingpset4;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBmanager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DBmanager(Context c) {
        context = c;
    }

    /* Opens the database to perform actions on it. */
    public DBmanager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /* Inserts item in the database. */
    public void insert(String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    /* Fetches all the items that are in the item list at that moment. */
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /* Deletes the selected item from the database. */
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }


}