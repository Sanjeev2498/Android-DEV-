package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelectionActivity extends AppCompatActivity {

    private CheckBox checkBoxJava, checkBoxPython, checkBoxCpp;
    private Button buttonSubmit, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        // Initialize views
        checkBoxJava = findViewById(R.id.checkBoxJava);
        checkBoxPython = findViewById(R.id.checkBoxPython);
        checkBoxCpp = findViewById(R.id.checkBoxCpp);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonClear = findViewById(R.id.buttonClear);

        // Set submit button click listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedLanguages();
            }
        });

        // Set clear button click listener
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSelections();
            }
        });
    }

    private void showSelectedLanguages() {
        List<String> selectedLanguages = new ArrayList<>();

        // Check which languages are selected
        if (checkBoxJava.isChecked()) {
            selectedLanguages.add("Java");
        }
        if (checkBoxPython.isChecked()) {
            selectedLanguages.add("Python");
        }
        if (checkBoxCpp.isChecked()) {
            selectedLanguages.add("C++");
        }

        // Create message to display
        String message;
        if (selectedLanguages.isEmpty()) {
            message = "No programming languages selected. Please select at least one language.";
        } else if (selectedLanguages.size() == 1) {
            message = "Selected Language: " + selectedLanguages.get(0);
        } else {
            StringBuilder sb = new StringBuilder("Selected Languages: ");
            for (int i = 0; i < selectedLanguages.size(); i++) {
                sb.append(selectedLanguages.get(i));
                if (i < selectedLanguages.size() - 1) {
                    sb.append(", ");
                }
            }
            message = sb.toString();
        }

        // Display selected languages using Toast
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void clearAllSelections() {
        // Uncheck all checkboxes
        checkBoxJava.setChecked(false);
        checkBoxPython.setChecked(false);
        checkBoxCpp.setChecked(false);
        
        // Show confirmation message
        Toast.makeText(this, "All selections cleared!", Toast.LENGTH_SHORT).show();
    }
}