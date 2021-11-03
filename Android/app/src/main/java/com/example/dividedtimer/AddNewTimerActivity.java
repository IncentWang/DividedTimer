// Author: IncentWang
// Date: 2021/11/2

package com.example.dividedtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class AddNewTimerActivity extends AppCompatActivity {

    DBManipulator dbManipulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_timer);
        initNumberPicker();
        dbManipulator = new DBManipulator(this);
    }

    private void initNumberPicker(){
        NumberPicker np = (NumberPicker) findViewById(R.id.NP);
        np.setMinValue(0);
        np.setMaxValue(168);
        np.setValue(40);
    }

    public void addTimer(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = findViewById(R.id.add_new_timer_name_field);
        NumberPicker np = findViewById(R.id.NP);
        dbManipulator.addTimer(editText.getText().toString(), np.getValue());
        startActivity(intent);
    }
}