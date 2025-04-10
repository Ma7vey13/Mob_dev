package com.mirea.mitrofanovms.simplefragmentapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private Fragment firstFragment, secondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            WindowInsetsCompat.Type.systemBars();
            return insets;
        });

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

        FragmentManager fm = getSupportFragmentManager();

        Button btnFirst = findViewById(R.id.btnFirstFragment);
        Button btnSecond = findViewById(R.id.btnSecondFragment);

        if (btnFirst != null && btnSecond != null) {
            btnFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fm.beginTransaction().replace(R.id.fragmentContainerView, firstFragment).commit();
                }
            });

            btnSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fm.beginTransaction().replace(R.id.fragmentContainerView, secondFragment).commit();
                }
            });
        }

        // по умолчанию показываем первый фрагмент
        if (savedInstanceState == null && findViewById(R.id.fragmentContainerView) != null) {
            fm.beginTransaction().replace(R.id.fragmentContainerView, firstFragment).commit();
        }
    }
}