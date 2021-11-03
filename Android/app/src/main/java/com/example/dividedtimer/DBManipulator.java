// Author: IncentWang
// Date: 2021/11/2

package com.example.dividedtimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManipulator {
    private Context context;
    private DBHelper dbHelper;

    public DBManipulator(Context context){
        this.context = context;
        dbHelper = new DBHelper(this.context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
    }

    public void addTimer(String name, double plannedTime){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.putNull("id");
        cv.put("name", name);
        cv.put("planned_time", plannedTime);
        cv.put("actual_time", 0);
        db.insert("main_view", null, cv);
    }

    public void saveRawData(String name, long time, String startTime){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM main_view WHERE name='" + name + "';", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        double oldTime = cursor.getDouble(3);
        cv.put("id", id);
        cv.put("name", name);
        cv.put("start_time", startTime);
        cv.put("last_time", time);
        db.insert("raw_data", null, cv);

        double addedTime = time / 1.0f / 1000 / 3600;
        db.execSQL("UPDATE main_view SET actual_time=" + Double.toString(oldTime + addedTime) + " WHERE id='" + id + "';");
    }
}
