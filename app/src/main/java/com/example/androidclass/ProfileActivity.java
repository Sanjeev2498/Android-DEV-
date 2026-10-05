package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageViewProfile;
    private TextView textViewName, textViewEmail, textViewPhone, textViewRating;
    private RatingBar ratingBar;
    private Button buttonEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewRating = findViewById(R.id.textViewRating);
        ratingBar = findViewById(R.id.ratingBar);
        buttonEditProfile = findViewById(R.id.buttonEditProfile);

        // Set rating bar listener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    updateRatingText(rating);
                }
            }
        });

        // Set edit profile button listener
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Edit Profile feature coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set profile image click listener
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Profile picture clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRatingText(float rating) {
        String ratingText = String.format("%.1f out of 5 stars", rating);
        textViewRating.setText(ratingText);
        
        // Show toast with current rating
        Toast.makeText(this, "Rating updated to " + rating + " stars", Toast.LENGTH_SHORT).show();
    }
}