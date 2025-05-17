package com.mirea.mitrofanovms.employeedb;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDBActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeDBActivity";

    private EditText nameEditText, powerEditText, strengthEditText;
    private SuperHeroDao superHeroDao;
    private ArrayAdapter<String> adapter;
    private List<SuperHero> heroesList = new ArrayList<>();
    private ListView heroesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        // Инициализация UI
        initViews();

        // Настройка адаптера
        setupAdapter();

        // Загрузка данных
        loadHeroes();

        // Обработчик кликов по списку
        setupListClickListener();
    }

    private void initViews() {
        nameEditText = findViewById(R.id.nameEditText);
        powerEditText = findViewById(R.id.powerEditText);
        strengthEditText = findViewById(R.id.strengthEditText);
        heroesListView = findViewById(R.id.heroesListView);

        Button addButton = findViewById(R.id.addButton);
        Button viewButton = findViewById(R.id.viewAllButton);

        addButton.setOnClickListener(v -> addHero());
        viewButton.setOnClickListener(v -> loadHeroes());

        // Инициализация DAO
        superHeroDao = App.getInstance().getDatabase().superHeroDao();
    }

    private void setupAdapter() {
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());
        heroesListView.setAdapter(adapter);
    }

    private void setupListClickListener() {
        heroesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранного героя
                SuperHero selectedHero = heroesList.get(position);

                // Заполняем поля данными героя
                nameEditText.setText(selectedHero.name);
                powerEditText.setText(selectedHero.superpower);
                strengthEditText.setText(String.valueOf(selectedHero.strengthLevel));

                Toast.makeText(EmployeeDBActivity.this,
                        "Выбран: " + selectedHero.name,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHero() {
        try {
            String name = nameEditText.getText().toString();
            String power = powerEditText.getText().toString();
            int strength = Integer.parseInt(strengthEditText.getText().toString());

            if (name.isEmpty() || power.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            SuperHero hero = new SuperHero(name, power, strength);

            new Thread(() -> {
                superHeroDao.insert(hero);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Добавлен: " + hero.name, Toast.LENGTH_SHORT).show();
                    clearFields();
                    loadHeroes();
                });
            }).start();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Некорректный уровень силы", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadHeroes() {
        new Thread(() -> {
            heroesList = superHeroDao.getAll();
            runOnUiThread(() -> {
                adapter.clear();
                for (SuperHero hero : heroesList) {
                    adapter.add(formatHeroInfo(hero));
                }
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private String formatHeroInfo(SuperHero hero) {
        return String.format("%s: %s (сила %d)",
                hero.name, hero.superpower, hero.strengthLevel);
    }

    private void clearFields() {
        nameEditText.setText("");
        powerEditText.setText("");
        strengthEditText.setText("");
    }
}