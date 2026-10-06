package com.example.androidclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView tvLostCount, tvFoundCount;
    private ListView lvRecentItems;
    private Button btnQuickAddLost, btnQuickAddFound;
    private ArrayList<String> recentItemsList;
    private ArrayAdapter<String> adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        initializeViews(view);
        setupRecentItems();
        setupClickListeners();
        updateStats();
        
        return view;
    }

    private void initializeViews(View view) {
        tvLostCount = view.findViewById(R.id.tvLostCount);
        tvFoundCount = view.findViewById(R.id.tvFoundCount);
        lvRecentItems = view.findViewById(R.id.lvRecentItems);
        btnQuickAddLost = view.findViewById(R.id.btnQuickAddLost);
        btnQuickAddFound = view.findViewById(R.id.btnQuickAddFound);
    }

    private void setupRecentItems() {
        // Get recent items from data manager
        if (getActivity() instanceof CampusLostFoundActivity) {
            DataManager dataManager = ((CampusLostFoundActivity) getActivity()).getDataManager();
            List<LostFoundItem> recentItems = dataManager.getRecentItems(6);
            
            recentItemsList = new ArrayList<>();
            for (LostFoundItem item : recentItems) {
                recentItemsList.add(item.getDisplaySummary() + " - " + item.getTimeAgo());
            }
        } else {
            // Fallback to sample data
            recentItemsList = new ArrayList<>(Arrays.asList(
                "📱 Lost: iPhone 13 - Found near Library",
                "🎒 Found: Blue Backpack - Lost at Cafeteria", 
                "🔑 Lost: Car Keys with BMW keychain",
                "📚 Found: Mathematics Textbook - Room 204",
                "💳 Lost: Student ID Card - John Smith",
                "🎧 Found: Wireless Headphones - Gym Area"
            ));
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, recentItemsList);
        lvRecentItems.setAdapter(adapter);

        // Set item click listener
        lvRecentItems.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = recentItemsList.get(position);
            Toast.makeText(getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
        });
    }

    private void setupClickListeners() {
        btnQuickAddLost.setOnClickListener(v -> {
            // Navigate to Add Item fragment with "Lost" preselected
            if (getActivity() instanceof CampusLostFoundActivity) {
                ((CampusLostFoundActivity) getActivity()).showAddItemFragment("Lost");
            }
            Toast.makeText(getContext(), "Report Lost Item", Toast.LENGTH_SHORT).show();
        });

        btnQuickAddFound.setOnClickListener(v -> {
            // Navigate to Add Item fragment with "Found" preselected
            if (getActivity() instanceof CampusLostFoundActivity) {
                ((CampusLostFoundActivity) getActivity()).showAddItemFragment("Found");
            }
            Toast.makeText(getContext(), "Report Found Item", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateStats() {
        // Get real statistics from data manager
        if (getActivity() instanceof CampusLostFoundActivity) {
            int[] stats = ((CampusLostFoundActivity) getActivity()).getItemStatistics();
            tvLostCount.setText(String.valueOf(stats[0])); // Lost items
            tvFoundCount.setText(String.valueOf(stats[1])); // Found items
        } else {
            // Fallback to sample data
            tvLostCount.setText("5");
            tvFoundCount.setText("3");
        }
    }

    // Method to refresh data when fragment becomes visible
    public void refreshData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        updateStats();
    }
}