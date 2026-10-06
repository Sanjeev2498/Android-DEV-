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

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxJava, checkBoxPython, checkBoxCpp;
    private Button buttonSubmit, buttonGoToLogin, buttonGoToProfile, buttonGoToCitySelection, buttonGoToLanguageSelection, buttonGoToImplicitIntent, buttonGoToStudentForm, buttonGoToHelloName, buttonGoToColorPicker, buttonGoToRadioButton, buttonGoToCardView, buttonGoToFragment, buttonGoToSplash, buttonGoToListView, buttonGoToViewPager, buttonGoToContextMenu, buttonGoToCollegeWebsite, buttonGoToCampusLostFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkBoxJava = findViewById(R.id.checkBoxJava);
        checkBoxPython = findViewById(R.id.checkBoxPython);
        checkBoxCpp = findViewById(R.id.checkBoxCpp);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonGoToLogin = findViewById(R.id.buttonGoToLogin);
        buttonGoToProfile = findViewById(R.id.buttonGoToProfile);
        buttonGoToCitySelection = findViewById(R.id.buttonGoToCitySelection);
        buttonGoToLanguageSelection = findViewById(R.id.buttonGoToLanguageSelection);
        buttonGoToImplicitIntent = findViewById(R.id.buttonGoToImplicitIntent);
        buttonGoToStudentForm = findViewById(R.id.buttonGoToStudentForm);
        buttonGoToHelloName = findViewById(R.id.buttonGoToHelloName);
        buttonGoToColorPicker = findViewById(R.id.buttonGoToColorPicker);
        buttonGoToRadioButton = findViewById(R.id.buttonGoToRadioButton);
        buttonGoToCardView = findViewById(R.id.buttonGoToCardView);
        buttonGoToFragment = findViewById(R.id.buttonGoToFragment);
        buttonGoToSplash = findViewById(R.id.buttonGoToSplash);
        buttonGoToListView = findViewById(R.id.buttonGoToListView);
        buttonGoToViewPager = findViewById(R.id.buttonGoToViewPager);
        buttonGoToContextMenu = findViewById(R.id.buttonGoToContextMenu);
        buttonGoToCollegeWebsite = findViewById(R.id.buttonGoToCollegeWebsite);
        buttonGoToCampusLostFound = findViewById(R.id.buttonGoToCampusLostFound);

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        // Set click listener for navigation to login
        buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to profile
        buttonGoToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to city selection
        buttonGoToCitySelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CitySelectionActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to language selection
        buttonGoToLanguageSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LanguageSelectionActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to implicit intent examples
        buttonGoToImplicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImplicitIntentActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to student form
        buttonGoToStudentForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentFormActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to hello name app
        buttonGoToHelloName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelloNameActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to color picker app
        buttonGoToColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorPickerActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to radio button example
        buttonGoToRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RadioButtonExampleActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to card view example
        buttonGoToCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CardViewActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to fragment example
        buttonGoToFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FragmentExampleActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to splash screen demo
        buttonGoToSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to listview example
        buttonGoToListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to viewpager example
        buttonGoToViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to context menu example
        buttonGoToContextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContextMenuActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to college website
        buttonGoToCollegeWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollegeWebsiteActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for navigation to campus lost & found app
        buttonGoToCampusLostFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CampusLostFoundActivity.class);
                startActivity(intent);
            }
        });
    }

    private void submitForm() {
        // Get form data
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();

        // Get selected gender
        String gender = "";
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedGenderId);
            gender = selectedRadioButton.getText().toString();
        }

        // Get selected programming languages
        StringBuilder languages = new StringBuilder();
        if (checkBoxJava.isChecked()) {
            languages.append("Java ");
        }
        if (checkBoxPython.isChecked()) {
            languages.append("Python ");
        }
        if (checkBoxCpp.isChecked()) {
            languages.append("C++ ");
        }

        // Create message to display
        String message = "Name: " + name + "\n" +
                        "Email: " + email + "\n" +
                        "Phone: " + phone + "\n" +
                        "Gender: " + gender + "\n" +
                        "Languages: " + languages.toString();

        // Display information using Toast
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}