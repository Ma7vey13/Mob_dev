package com.mirea.mitrofanovms.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private EditText editBook;
    private EditText editQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        editBook = findViewById(R.id.editBook);
        editQuote = findViewById(R.id.editQuote);

        TextView devBook = findViewById(R.id.textDevBook);
        TextView devQuote = findViewById(R.id.textDevQuote);

        Intent intent = getIntent();
        if (intent != null) {
            String book = intent.getStringExtra(MainActivity.BOOK_NAME_KEY);
            String quote = intent.getStringExtra(MainActivity.QUOTES_KEY);
            devBook.setText("Любимая книга: " + book);
            devQuote.setText("Цитата из книги: " + quote);
        }
    }

    public void sendBack(View view) {
        String userBook = editBook.getText().toString();
        String userQuote = editQuote.getText().toString();
        String result = String.format("Название Вашей любимой книги: %s. Цитата: %s", userBook, userQuote);

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.USER_MESSAGE, result);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}