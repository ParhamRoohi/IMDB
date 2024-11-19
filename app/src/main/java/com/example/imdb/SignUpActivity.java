package com.example.imdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUpActivity extends AppCompatActivity {


    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    private ImageView arrowImg;
    private RadioButton male;
    private RadioButton female;
    private CheckBox agree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameEditText = findViewById(R.id.name_editText);
        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
        rePasswordEditText = findViewById(R.id.re_password_editText);
        arrowImg = findViewById(R.id.arrow_img);
        male = findViewById(R.id.male_radioButton);
        female = findViewById(R.id.female_radioButton);
        agree = findViewById(R.id.agree_checkBox);

        arrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onNavigateToSignIn(View view) {

        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String rePassword = rePasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(rePassword)) {
            Toast.makeText(this, "Please re-enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!male.isChecked() && !female.isChecked()) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!agree.isChecked()) {
            Toast.makeText(this, "Please check agree", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = male.isChecked() ? "male" : "female";

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("registeredEmail", email);
        editor.putString("registeredPassword", password);
        editor.putString("name", name);
        editor.putString("gender", gender);
        editor.apply();

        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }

}



