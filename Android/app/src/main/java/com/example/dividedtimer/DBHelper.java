// Author: IncentWang
// Date: 2021/11/2

package com.example.dividedtimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "timer.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql = new StringBuilder();
        // CREATE TABLE main_view(id INT PRIMARY KEY, name TEXT PRIMARY KEY, planned_time DOUBLE, actual_time DOUBLE);
        sqLiteDatabase.execSQL("CREATE TABLE main_view(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, planned_time DOUBLE, actual_time DOUBLE);");
        sqLiteDatabase.execSQL("CREATE TABLE week_view(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, start_time TEXT, end_time TEXT, planned_time DOUBLE, actual_time DOUBLE);");
        sqLiteDatabase.execSQL("CREATE TABLE raw_data(id INTEGER, name TEXT, start_time TEXT, last_time DOUBLE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
