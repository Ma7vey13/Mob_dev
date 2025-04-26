package com.mirea.mitrofanovms.data_thread;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.mitrofanovms.data_thread.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = () -> binding.tvInfo.setText("1. runOnUiThread — выполнено через главный поток.\n");
        final Runnable runn2 = () -> binding.tvInfo.setText("2. post — поставлено в очередь обработки UI потока.\n");
        final Runnable runn3 = () -> binding.tvInfo.setText("3. postDelayed — запланировано с задержкой 2 секунды.\n");

        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(runn1); // сразу выполняется в UI-потоке
                TimeUnit.SECONDS.sleep(1);
                binding.tvInfo.post(runn2); // в очередь UI
                binding.tvInfo.postDelayed(runn3, 2000); // через 2 секунды
            } catch (InterruptedException e) {
                Log.e("ThreadError", "Interrupted", e);
            }
        });
        t.start();
    }
}