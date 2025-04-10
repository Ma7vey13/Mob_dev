package com.mirea.mitrofanovms.favoritebook;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView textViewUserBook;
    public static final String BOOK_NAME_KEY = "book_name";
    public static final String QUOTES_KEY = "quotes_name";
    public static final String USER_MESSAGE = "MESSAGE";

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewUserBook = findViewById(R.id.textViewBook);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String userBook = result.getData().getStringExtra(USER_MESSAGE);
                            textViewUserBook.setText(userBook);
                        }
                    }
                }
        );
    }

    public void getInfoAboutBook(View view) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra(BOOK_NAME_KEY, "Ответ");
        intent.putExtra(QUOTES_KEY, "Уверенность — это не когда тебя любят все, а когда тебе всё равно, кто тебя не любит.");
        activityResultLauncher.launch(intent);
    }
}