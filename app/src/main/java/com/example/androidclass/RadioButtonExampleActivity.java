package com.example.androidclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RadioButtonExampleActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioMale, radioFemale;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button_example);

        // Initialize views
        radioGroup = findViewById(R.id.radioGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Submit button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected radio button ID
                int selectedId = radioGroup.getCheckedRadioButtonId();
                
                if (selectedId == -1) {
                    // No radio button selected
                    Toast.makeText(RadioButtonExampleActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Find the selected radio button
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedGender = selectedRadioButton.getText().toString();

                // Navigate to SecondActivity with selected gender
                Intent intent = new Intent(RadioButtonExampleActivity.this, RadioButtonResultActivity.class);
                intent.putExtra("selected_gender", selectedGender);
                startActivity(intent);
            }
        });
    }
}