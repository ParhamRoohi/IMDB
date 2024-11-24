//
//package com.example.imdb;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.imdb.databinding.ActivityProfileBinding;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProfileActivity extends AppCompatActivity {
//    private ActivityProfileBinding binding;
//    private ReportProfileAdapter profileAdapter;
//    ReportProfileAdapter ReprtProfileAdapter;
//    private List<Report> reports;
//    private ImageView arrowImg;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityProfileBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        Intent intent = getIntent();
//        String userName = intent.getStringExtra("userName");
//        arrowImg = findViewById(R.id.arrow_img);
//        arrowImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        if (userName != null) {
//            binding.userNameTextView.setText(userName);
//        }
//
//        reports = new ArrayList<>();
//        profileAdapter = new ReportProfileAdapter(reports, this);
//        binding.recyclerViewProfile.setLayoutManager(new LinearLayoutManager(this));
//        binding.recyclerViewProfile.setAdapter(profileAdapter);
//
//        loadReports();
//
//        ReprtProfileAdapter.setOnItemLongClickListener(new ReportProfileAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick(int position) {
//                showDeleteDialog(position);
//            }
//        });
//    }
//
//    private void loadReports() {
//        SharedPreferences prefs = getSharedPreferences("ReportsPrefs", MODE_PRIVATE);
//        String reportsJson = prefs.getString("reports", "[]");
//
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<Report>>() {}.getType();
//        reports = gson.fromJson(reportsJson, type);
//
//        if (reports == null) {
//            reports = new ArrayList<>();
//        }
//
//        profileAdapter.updateReports(reports);
//    }
//    private void showDeleteDialog(int position) {
//        new AlertDialog.Builder(ProfileActivity.this)
//                .setMessage("Are you sure you want to delete this report?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteReport(position);
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create()
//                .show();
//    }
//    private void deleteReport(int position) {
//        reports.remove(position);
//        profileAdapter.updateReports(reports);
//
//        SharedPreferences prefs = getSharedPreferences("ReportsPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        Gson gson = new Gson();
//        String updatedReportsJson = gson.toJson(reports);
//        editor.putString("reports", updatedReportsJson);
//        editor.apply();
//
//        Toast.makeText(ProfileActivity.this, "Report deleted", Toast.LENGTH_SHORT).show();
//    }
//}
package com.example.imdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.imdb.databinding.ActivityProfileBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private ReportProfileAdapter profileAdapter;  // Keep this reference
    private List<Report> reports;
    private ImageView arrowImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        arrowImg = findViewById(R.id.arrow_img);
        arrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (userName != null) {
            binding.userNameTextView.setText(userName);
        }

        reports = new ArrayList<>();
        profileAdapter = new ReportProfileAdapter(reports, this);
        binding.recyclerViewProfile.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewProfile.setAdapter(profileAdapter);

        loadReports();

        profileAdapter.setOnItemLongClickListener(new ReportProfileAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                showDeleteDialog(position);
            }
        });
    }

    private void loadReports() {
        SharedPreferences prefs = getSharedPreferences("ReportsPrefs", MODE_PRIVATE);
        String reportsJson = prefs.getString("reports", "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Report>>() {}.getType();
        reports = gson.fromJson(reportsJson, type);

        if (reports == null) {
            reports = new ArrayList<>();
        }

        profileAdapter.updateReports(reports);
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(ProfileActivity.this)
                .setMessage("Are you sure you want to delete this report?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteReport(position);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void deleteReport(int position) {
        reports.remove(position);
        profileAdapter.updateReports(reports);

        SharedPreferences prefs = getSharedPreferences("ReportsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String updatedReportsJson = gson.toJson(reports);
        editor.putString("reports", updatedReportsJson);
        editor.apply();

        Toast.makeText(ProfileActivity.this, "Report deleted", Toast.LENGTH_SHORT).show();
    }
}