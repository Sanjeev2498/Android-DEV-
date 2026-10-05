package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RadioButtonResultActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button_result);

        // Initialize views
        tvResult = findViewById(R.id.tvResult);
        btnBack = findViewById(R.id.btnBack);

        // Get the selected gender from Intent
        String selectedGender = getIntent().getStringExtra("selected_gender");
        
        // Display the result
        if (selectedGender != null) {
            tvResult.setText("You Selected:\n\n" + selectedGender);
        } else {
            tvResult.setText("No selection received");
        }

        // Back button click listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to previous activity
                finish();
            }
        });
    }
}