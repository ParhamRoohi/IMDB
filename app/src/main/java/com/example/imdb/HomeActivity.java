package com.example.imdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.imdb.databinding.ActivityHomeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_NEW_REPORT = 1;

    private ActivityHomeBinding binding;
    private ReportAdapter reportAdapter;
    private List<Report> reports;

    private String userEmail, userName, userGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reports = new ArrayList<>();
        reportAdapter = new ReportAdapter(reports, this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(reportAdapter);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");
        userName = intent.getStringExtra("userName");
        userGender = intent.getStringExtra("gender");

        loadReports();

        binding.profileBtn.setOnClickListener(v -> navigateToProfile());
        binding.logoutLogo.setOnClickListener(v -> logoutUser());
        binding.newBtn.setOnClickListener(v -> navigateToReport());
        binding.categoriesBtn.setOnClickListener(v -> navigateToCategories());
    }

    private void logoutUser() {
        Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void navigateToProfile() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String senderName = prefs.getString("senderName", null);
        if (senderName != null) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("userName", senderName);
            startActivity(intent);
        }
    }

    private void navigateToReport() {
        Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
        intent.putExtra("name", userName);
        startActivityForResult(intent, REQUEST_CODE_NEW_REPORT);
    }

    private void navigateToCategories() {
        Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NEW_REPORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW_REPORT && resultCode == RESULT_OK) {
            loadReports();
        }
    }

    private void loadReports() {
        SharedPreferences prefs = getSharedPreferences("ReportsPrefs", MODE_PRIVATE);
        String reportsJson = prefs.getString("reports", "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Report>>() {
        }.getType();
        reports = gson.fromJson(reportsJson, type);

        if (reports == null) {
            reports = new ArrayList<>();
        }

        reportAdapter.updateReports(reports);
    }
}
