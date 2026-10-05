package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView tvGreeting;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialize views
        tvGreeting = findViewById(R.id.tvGreeting);
        btnBack = findViewById(R.id.btnBack);

        // Get the name from Intent
        String name = getIntent().getStringExtra("name_key");
        
        // Set the greeting message
        if (name != null && !name.isEmpty()) {
            tvGreeting.setText("Hello.. " + name);
        } else {
            tvGreeting.setText("Hello.. Guest");
        }

        // Set back button click listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to MainActivity or finish current activity
                finish(); // This will go back to the previous activity
            }
        });
    }
}