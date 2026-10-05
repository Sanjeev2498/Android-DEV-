package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CardViewActivity extends AppCompatActivity {

    private CardView cardStudent, cardCourses, cardSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        // Initialize CardViews
        cardStudent = findViewById(R.id.cardStudent);
        cardCourses = findViewById(R.id.cardCourses);
        cardSettings = findViewById(R.id.cardSettings);

        // Set click listeners for cards
        cardStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CardViewActivity.this, "Student card clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        cardCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CardViewActivity.this, "Courses card clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CardViewActivity.this, "Settings card clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}