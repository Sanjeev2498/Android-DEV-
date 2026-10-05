package com.example.androidclass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImplicitIntentActivity extends AppCompatActivity {

    private Button btnOpenWebsite, btnMakeCall, btnOpenMaps;
    private EditText etPhoneNumber, etWebsiteUrl, etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        // Initialize views
        btnOpenWebsite = findViewById(R.id.btnOpenWebsite);
        btnMakeCall = findViewById(R.id.btnMakeCall);
        btnOpenMaps = findViewById(R.id.btnOpenMaps);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etWebsiteUrl = findViewById(R.id.etWebsiteUrl);
        etLocation = findViewById(R.id.etLocation);

        // Set default values
        etWebsiteUrl.setText("https://www.google.com");
        etPhoneNumber.setText("9876543210");
        etLocation.setText("New Delhi, India");

        // Open Website Button
        btnOpenWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });

        // Make Phone Call Button
        btnMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        // Open Maps Button
        btnOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });
    }

    private void openWebsite() {
        String url = etWebsiteUrl.getText().toString().trim();
        
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a website URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add http:// if not present
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid URL format", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePhoneCall() {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Create URI with tel scheme
            Uri uri = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Error opening dialer", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMaps() {
        String location = etLocation.getText().toString().trim();
        
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Create URI with geo scheme for location search
            String encodedLocation = Uri.encode(location);
            Uri uri = Uri.parse("geo:0,0?q=" + encodedLocation);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Error opening maps", Toast.LENGTH_SHORT).show();
        }
    }
}