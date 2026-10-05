package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private String[] fruits = {
        "Apple", 
        "Banana", 
        "Mango", 
        "Orange", 
        "Grapes", 
        "Strawberry", 
        "Pineapple", 
        "Watermelon", 
        "Kiwi", 
        "Papaya"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Initialize ListView
        listView = findViewById(R.id.listView);

        // Create adapter and set to ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            fruits
        );
        
        // Set adapter to ListView
        listView.setAdapter(adapter);

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFruit = fruits[position];
                Toast.makeText(ListViewActivity.this, 
                    "You selected: " + selectedFruit, 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}