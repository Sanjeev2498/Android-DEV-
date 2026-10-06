package com.example.androidclass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CollegeWebsiteActivity extends AppCompatActivity {

    private Button btnCollegeWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_website_login);

        // Initialize button
        btnCollegeWebsite = findViewById(R.id.btnCollegeWebsite);
        btnCollegeWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCollegeWebsite();
            }
        });
    }

    private void openCollegeWebsite() {

        String collegeWebsiteUrl = "https://www.krmangalam.edu.in/";
        
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(collegeWebsiteUrl));
        
        try {
            startActivity(browserIntent);
            Toast.makeText(this, "Opening College Website...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open website. Please check your internet connection.", Toast.LENGTH_LONG).show();
        }
    }
}