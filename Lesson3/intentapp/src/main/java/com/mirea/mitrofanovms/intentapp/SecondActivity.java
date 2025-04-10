package com.mirea.mitrofanovms.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String time = intent.getStringExtra("time");
        int number = intent.getIntExtra("number", 0);
        int square = number * number;

        TextView textView = findViewById(R.id.textView);
        String resultText = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ "
                + square + ", а текущее время " + time;
        textView.setText(resultText);
    }
}