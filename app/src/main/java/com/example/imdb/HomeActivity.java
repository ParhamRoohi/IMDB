package com.example.imdb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.example.imdb.databinding.ActivityHomeBinding;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private String userEmail, userName, userGender;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");
        userName = intent.getStringExtra("name");
        userGender = intent.getStringExtra("gender");

        findViewById(R.id.profile_btn).setOnClickListener(v -> showProfileDialog());
        findViewById(R.id.logout_logo).setOnClickListener(v -> logoutUser());
    }

    private void showProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile Information")
                .setMessage("Name: " + userName + "\nEmail: " + userEmail + "\nGender: " + userGender)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }
    private void logoutUser() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onNavigateToCategories(android.view.View view) {
        Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
        startActivity(intent);
    }
    public void onNavigateToReport(android.view.View view) {
        Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
        startActivity(intent);
    }
}
