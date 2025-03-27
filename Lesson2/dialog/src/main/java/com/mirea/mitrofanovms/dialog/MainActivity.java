package com.mirea.mitrofanovms.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onClickShowTimeDialog(View view) {
        MyTimeDialogFragment timeDialog = new MyTimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickShowDateDialog(View view) {
        MyDateDialogFragment dateDialog = new MyDateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialog = new MyProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progressDialog");
    }

    public void onClickShowSnackbar(View view) {
        Snackbar.make(view, "Это Snackbar сообщение", Snackbar.LENGTH_LONG)
                .setAction("Действие", v -> {
                    Toast.makeText(MainActivity.this,
                            "Действие выполнено",
                            Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}