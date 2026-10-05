package com.example.androidclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HelloNameActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnSayHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_name);

        // Initialize views
        etName = findViewById(R.id.etName);
        btnSayHello = findViewById(R.id.btnSayHello);

        // Set click listener for Say Hello button
        btnSayHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                
                // Validate input
                if (name.isEmpty()) {
                    etName.setError("Please enter your name");
                    etName.requestFocus();
                    return;
                }

                // Navigate to SecondActivity with name
                Intent intent = new Intent(HelloNameActivity.this, SecondActivity.class);
                intent.putExtra("name_key", name);
                startActivity(intent);
            }
        });
    }
}