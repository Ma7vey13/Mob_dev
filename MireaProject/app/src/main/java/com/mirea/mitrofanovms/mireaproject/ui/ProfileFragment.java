package com.mirea.mitrofanovms.mireaproject.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.mirea.mitrofanovms.mireaproject.R;

public class ProfileFragment extends Fragment {
    private static final String PREFS_NAME = "ProfilePrefs";
    private EditText etName, etSurname, etGroup, etInterests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        etGroup = view.findViewById(R.id.etGroup);
        etInterests = view.findViewById(R.id.etInterests);
        Button btnSave = view.findViewById(R.id.btnSave);

        loadProfileData();

        btnSave.setOnClickListener(v -> saveProfileData());

        return view;
    }

    private void saveProfileData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("name", etName.getText().toString());
        editor.putString("surname", etSurname.getText().toString());
        editor.putString("group", etGroup.getText().toString());
        editor.putString("interests", etInterests.getText().toString());

        editor.apply();
        Toast.makeText(getContext(), "Профиль сохранен", Toast.LENGTH_SHORT).show();
    }

    private void loadProfileData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        etName.setText(prefs.getString("name", ""));
        etSurname.setText(prefs.getString("surname", ""));
        etGroup.setText(prefs.getString("group", ""));
        etInterests.setText(prefs.getString("interests", ""));
    }
}
