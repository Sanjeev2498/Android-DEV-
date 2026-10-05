package com.example.androidclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentFormActivity extends AppCompatActivity {

    private EditText etName, etAge, etAddress;
    private RadioGroup rgGender;
    private CheckBox cbAndroid, cbJava, cbKotlin, cbXml, cbFirebase;
    private Button btnSubmit, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        // Initialize views
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);
        rgGender = findViewById(R.id.rgGender);
        cbAndroid = findViewById(R.id.cbAndroid);
        cbJava = findViewById(R.id.cbJava);
        cbKotlin = findViewById(R.id.cbKotlin);
        cbXml = findViewById(R.id.cbXml);
        cbFirebase = findViewById(R.id.cbFirebase);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);

        // Submit button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        // Clear button click listener
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });
    }

    private void submitForm() {
        // Get form data
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // Validate required fields
        if (name.isEmpty()) {
            etName.setError("Please enter your name");
            etName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            etAge.setError("Please enter your age");
            etAge.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            etAddress.setError("Please enter your address");
            etAddress.requestFocus();
            return;
        }

        // Get selected gender
        String gender = "Not selected";
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        if (selectedGenderId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedGenderId);
            gender = selectedRadioButton.getText().toString();
        }

        // Get selected skills
        StringBuilder skills = new StringBuilder();
        if (cbAndroid.isChecked()) {
            skills.append("Android, ");
        }
        if (cbJava.isChecked()) {
            skills.append("Java, ");
        }
        if (cbKotlin.isChecked()) {
            skills.append("Kotlin, ");
        }
        if (cbXml.isChecked()) {
            skills.append("XML, ");
        }
        if (cbFirebase.isChecked()) {
            skills.append("Firebase, ");
        }

        // Remove trailing comma and space
        String skillsStr = skills.toString();
        if (skillsStr.length() > 2) {
            skillsStr = skillsStr.substring(0, skillsStr.length() - 2);
        }

        if (skillsStr.isEmpty()) {
            skillsStr = "No skills selected";
        }

        // Create result message
        String result = "Name: " + name + "\n" +
                       "Age: " + age + "\n" +
                       "Gender: " + gender + "\n" +
                       "Skills: " + skillsStr + "\n" +
                       "Address: " + address;

        // Option 1: Show in Toast
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        // Option 2: Navigate to new activity (uncomment if you want this)
        // Intent intent = new Intent(StudentFormActivity.this, StudentResultActivity.class);
        // intent.putExtra("result", result);
        // startActivity(intent);
    }

    private void clearForm() {
        // Clear all EditText fields
        etName.setText("");
        etAge.setText("");
        etAddress.setText("");

        // Clear gender selection
        rgGender.clearCheck();

        // Uncheck all checkboxes
        cbAndroid.setChecked(false);
        cbJava.setChecked(false);
        cbKotlin.setChecked(false);
        cbXml.setChecked(false);
        cbFirebase.setChecked(false);

        // Clear any error messages
        etName.setError(null);
        etAge.setError(null);
        etAddress.setError(null);

        // Show confirmation
        Toast.makeText(this, "Form cleared successfully!", Toast.LENGTH_SHORT).show();

        // Focus on first field
        etName.requestFocus();
    }
}