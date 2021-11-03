// Author: IncentWang
// Date: 2021/11/2

package com.example.dividedtimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this, "timer.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        initAllTimers(db);
    }

    private void initAllTimers(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM main_view;", null);
        if (c != null){
            c.moveToFirst();
            if (c.getCount() != 0){
                // Change things in activity here
                findViewById(R.id.no_active_timer).setVisibility(View.GONE);
                LinearLayout cl = (LinearLayout) findViewById(R.id.all_linearlayout);
                do{
                    LinearLayout hl = new LinearLayout(cl.getContext());
                    hl.setOrientation(LinearLayout.HORIZONTAL);

                    String name = c.getString(1);
                    double plannedTime = c.getDouble(2);
                    double actualTime = c.getDouble(3);

                    TextView tv = new TextView(this);
                    tv.setText(name);
                    LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0.3f
                    );
                    tv.setLayoutParams(lpText);
                    hl.addView(tv);

                    TextView tvTime = new TextView(this);
                    tvTime.setText(Double.toString(actualTime).substring(0, 3) + " / " + Double.toString(plannedTime));
                    LinearLayout.LayoutParams lpTime = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0.4f
                    );
                    tvTime.setLayoutParams(lpTime);
                    hl.addView(tvTime);

                    Button btnStart = new Button(this);
                    btnStart.setText("开始");
                    btnStart.setBackgroundColor(Color.GREEN);
                    LinearLayout.LayoutParams lpStart = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            0.3f
                    );
                    btnStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startTimer(name);
                        }
                    });
                    btnStart.setLayoutParams(lpStart);
                    hl.addView(btnStart);

                    cl.addView(hl);


                }while(c.moveToNext());
            }
        }
        else{
            // Table is empty, should do nothing here
        }
    }

    public void addTimer(View view){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTimerActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startTimer(String name){
        Intent intent = new Intent(this, TimingActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}