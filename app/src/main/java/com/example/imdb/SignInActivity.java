package com.example.imdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
    }

    public void onNavigateToHome(android.view.View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String registeredEmail = prefs.getString("registeredEmail", null);
        String registeredPassword = prefs.getString("registeredPassword", null);
        String name = prefs.getString("name", null);


        if (email.equalsIgnoreCase(registeredEmail) && password.equals(registeredPassword)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("senderName", name); // Save the sender name
            editor.apply();

            Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
            homeIntent.putExtra("email", registeredEmail);
            homeIntent.putExtra("userName", name);
            startActivity(homeIntent);
            finish();
        } else {
            Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNavigateToSignUp(android.view.View view) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}


