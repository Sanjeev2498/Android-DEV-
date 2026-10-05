package com.example.androidclass;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ColorPickerActivity extends AppCompatActivity {

    private TextView tvColorName;
    private Button btnRed, btnGreen;
    private View mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        // Initialize views
        tvColorName = findViewById(R.id.tvColorName);
        btnRed = findViewById(R.id.btnRed);
        btnGreen = findViewById(R.id.btnGreen);
        mainLayout = findViewById(R.id.mainLayout);

        // Red button click listener
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background to red
                mainLayout.setBackgroundColor(Color.RED);
                // Update text to show selected color
                tvColorName.setText("Red");
                tvColorName.setTextColor(Color.WHITE);
            }
        });

        // Green button click listener
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background to green
                mainLayout.setBackgroundColor(Color.GREEN);
                // Update text to show selected color
                tvColorName.setText("Green");
                tvColorName.setTextColor(Color.WHITE);
            }
        });
    }
}