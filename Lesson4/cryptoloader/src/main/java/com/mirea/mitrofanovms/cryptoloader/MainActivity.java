package com.mirea.mitrofanovms.cryptoloader;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.mirea.mitrofanovms.cryptoloader.databinding.ActivityMainBinding;

import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private ActivityMainBinding binding;
    private static final int LoaderID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonEncrypt.setOnClickListener(v -> {
            String message = binding.editTextMessage.getText().toString();
            SecretKey key = CryptoUtils.generateKey();
            byte[] cipherText = CryptoUtils.encryptMsg(message, key);

            Bundle bundle = new Bundle();
            bundle.putByteArray(MyLoader.ARG_WORD, cipherText);
            bundle.putByteArray("key", key.getEncoded());

            LoaderManager.getInstance(this).restartLoader(LoaderID, bundle, this);
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MyLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Toast.makeText(this, "Расшифрованное сообщение: " + data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}