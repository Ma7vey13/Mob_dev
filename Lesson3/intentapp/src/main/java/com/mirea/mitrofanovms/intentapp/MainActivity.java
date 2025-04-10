package com.mirea.mitrofanovms.intentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            long dateInMillis = System.currentTimeMillis();
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String dateString = sdf.format(new Date(dateInMillis));

            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("time", dateString);
            intent.putExtra("number", 20);
            startActivity(intent);
        });
    }
}