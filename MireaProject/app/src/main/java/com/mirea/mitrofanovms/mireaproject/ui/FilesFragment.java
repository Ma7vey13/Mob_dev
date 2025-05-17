package com.mirea.mitrofanovms.mireaproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.mirea.mitrofanovms.mireaproject.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FilesFragment extends Fragment {
    private EditText etFileName, etFileContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);

        etFileName = view.findViewById(R.id.etFileName);
        etFileContent = view.findViewById(R.id.etFileContent);
        Button btnSaveFile = view.findViewById(R.id.btnSaveFile);
        Button btnLoadFile = view.findViewById(R.id.btnLoadFile);

        btnSaveFile.setOnClickListener(v -> saveToFile());
        btnLoadFile.setOnClickListener(v -> loadFromFile());

        return view;
    }

    private void saveToFile() {
        String filename = etFileName.getText().toString();
        String content = etFileContent.getText().toString();

        if (filename.isEmpty() || content.isEmpty()) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = requireContext().openFileOutput(filename, 0);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(getContext(), "Файл сохранен", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        String filename = etFileName.getText().toString();

        if (filename.isEmpty()) {
            Toast.makeText(getContext(), "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileInputStream fis = requireContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            etFileContent.setText(sb.toString());
            Toast.makeText(getContext(), "Файл загружен", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Файл не найден", Toast.LENGTH_SHORT).show();
        }
    }
}