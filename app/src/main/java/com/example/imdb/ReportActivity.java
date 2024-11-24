package com.example.imdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imdb.databinding.ActivityReportBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    private ActivityReportBinding binding;
    private String selectedCategory;
    private String userName;
    private ImageView arrowImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUpload.setOnClickListener(v->submitReport());
        arrowImg = findViewById(R.id.arrow_img);

        arrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayList<String> categories = new ArrayList<>();
        categories.add("News");
        categories.add("Celebs");
        categories.add("Movies");
        categories.add("Shows");
        categories.add("Events");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCategory.setAdapter(adapter);


        binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = null;
            }
        });
    }

    private void submitReport() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("name");
        String title = binding.etTitle.getText().toString();
        String content = binding.etContent.getText().toString();
        String senderName = userName;
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        if (title.isEmpty()) {
            binding.etTitle.setError("Title cannot be empty");
            return;
        }

        if (content.isEmpty()) {
            binding.etContent.setError("Content cannot be empty");
            return;
        }

        Report newReport = new Report(title, content, senderName, selectedCategory, timestamp);

        SharedPreferences prefs = getSharedPreferences("ReportsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String reportsJson = prefs.getString("reports", "[]");
        Type type = new TypeToken<ArrayList<Report>>() {}.getType();
        ArrayList<Report> reports = gson.fromJson(reportsJson, type);
        reports.add(newReport);
        editor.putString("reports", gson.toJson(reports));
        editor.apply();

        setResult(RESULT_OK);
        finish();
    }
}
