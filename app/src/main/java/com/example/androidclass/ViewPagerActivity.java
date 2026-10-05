package com.example.androidclass;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout dotsIndicator;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        dotsIndicator = findViewById(R.id.dotsIndicator);

        // Create adapter and set to ViewPager2
        adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager2 for dots indicator
        new TabLayoutMediator(dotsIndicator, viewPager, 
            (tab, position) -> {
                // Set tab text or just leave empty for dots
            }).attach();
    }
}