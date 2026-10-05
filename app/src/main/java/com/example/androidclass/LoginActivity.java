package com.example.androidclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonGoToProfile;
    private TextView textViewForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonGoToProfile = findViewById(R.id.buttonGoToProfile);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Set click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        // Set click listener for forgot password
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forgot Password feature coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for navigation to profile
        buttonGoToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Basic validation
        if (username.isEmpty()) {
            editTextUsername.setError("Please enter username");
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Please enter password");
            return;
        }

        // Display welcome message
        String welcomeMessage = "Welcome, " + username + "!";
        Toast.makeText(LoginActivity.this, welcomeMessage, Toast.LENGTH_LONG).show();
    }
}