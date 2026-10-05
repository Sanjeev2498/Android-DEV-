package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CitySelectionActivity extends AppCompatActivity {

    private Spinner spinnerCities;
    private Button buttonShowCity;
    
    // Array of cities
    private String[] cities = {
        "Select a city",
        "New York", 
        "Los Angeles", 
        "Chicago", 
        "Houston", 
        "Phoenix", 
        "Philadelphia", 
        "San Antonio", 
        "San Diego", 
        "Dallas", 
        "San Jose",
        "Austin",
        "Jacksonville",
        "Fort Worth",
        "Columbus",
        "Charlotte"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        // Initialize views
        spinnerCities = findViewById(R.id.spinnerCities);
        buttonShowCity = findViewById(R.id.buttonShowCity);

        // Setup spinner
        setupSpinner();

        // Set button click listener
        buttonShowCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedCity();
            }
        });
    }

    private void setupSpinner() {
        // Create ArrayAdapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            cities
        );
        
        // Set dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        // Set adapter to spinner
        spinnerCities.setAdapter(adapter);
    }

    private void showSelectedCity() {
        // Get selected item from spinner
        String selectedCity = spinnerCities.getSelectedItem().toString();
        
        // Check if user has selected a valid city
        if (selectedCity.equals("Select a city")) {
            Toast.makeText(this, "Please select a city first!", Toast.LENGTH_SHORT).show();
        } else {
            // Display selected city in toast
            String message = "Selected City: " + selectedCity;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}