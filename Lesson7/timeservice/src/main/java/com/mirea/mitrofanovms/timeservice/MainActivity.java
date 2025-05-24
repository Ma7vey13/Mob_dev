package com.mirea.mitrofanovms.timeservice;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mirea.mitrofanovms.timeservice.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TimeService";
    private ActivityMainBinding binding;
    private final String host = "time.nist.gov";
    private final int port = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetTimeTask().execute();
            }
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            StringBuilder timeResult = new StringBuilder();
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                String secondLine = reader.readLine();
                timeResult.append("").append(secondLine);

                Log.d(TAG, "Полученные данные:\n" + timeResult.toString());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "Ошибка подключения: " + e.getMessage();
            }
            return timeResult.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            binding.textView.setText(result);
        }
    }
}