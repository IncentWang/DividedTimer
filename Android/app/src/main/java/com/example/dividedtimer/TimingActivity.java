// Author: IncentWang
// Date: 2021/11/2

package com.example.dividedtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public class TimingActivity extends AppCompatActivity {
    String name;
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    long timerStart;
    long timerEnd;
    TimerThread thread;
    DBManipulator dbManipulator;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        startTimer();
        dbManipulator = new DBManipulator(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startTimer(){
        LocalDateTime ldt = LocalDateTime.now();
        startDate = ldt.toLocalDate().toString();
        startTime = ldt.toLocalTime().toString();
        timerStart = System.currentTimeMillis();
        TextView tv = findViewById(R.id.dubug_text);
        thread = new TimerThread(tv, timerStart);
        thread.start();
    }

    public void stopOrStartTimer(View view){
        if (thread.timerStopped){
            thread.changeTimerStopped(false);
        }
        else{
            thread.changeTimerStopped(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void endTimer(View view){
        thread.timerEnded = true;
        long lastTime = System.currentTimeMillis() - thread.startTime - thread.totalStopTime;

        dbManipulator.saveRawData(name, lastTime, startDate + startTime);
        startActivity(new Intent(this, MainActivity.class));
    }
}