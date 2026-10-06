package com.example.androidclass;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyItemsFragment extends Fragment {

    private TextView tvItemCount;
    private ListView lvMyItems;
    private LinearLayout layoutEmptyState;
    private Button btnFilterAll, btnFilterLost, btnFilterFound, btnFilterActive;
    private Button btnAddFirstItem;

    private ArrayList<MyItem> allItemsList;
    private ArrayList<MyItem> filteredItemsList;
    private MyItemsAdapter adapter;
    private String currentFilter = "All";

    public MyItemsFragment() {
        // Required empty public constructor
    }

    public static MyItemsFragment newInstance() {
        return new MyItemsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_items, container, false);
        
        initializeViews(view);
        setupSampleData();
        setupFilterButtons();
        setupEmptyStateButton();
        updateDisplay();
        
        return view;
    }

    private void initializeViews(View view) {
        tvItemCount = view.findViewById(R.id.tvItemCount);
        lvMyItems = view.findViewById(R.id.lvMyItems);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        btnFilterAll = view.findViewById(R.id.btnFilterAll);
        btnFilterLost = view.findViewById(R.id.btnFilterLost);
        btnFilterFound = view.findViewById(R.id.btnFilterFound);
        btnFilterActive = view.findViewById(R.id.btnFilterActive);
        btnAddFirstItem = view.findViewById(R.id.btnAddFirstItem);

        allItemsList = new ArrayList<>();
        filteredItemsList = new ArrayList<>();
    }

    private void setupSampleData() {
        // Get real data from data manager instead of sample data
        if (getActivity() instanceof CampusLostFoundActivity) {
            DataManager dataManager = ((CampusLostFoundActivity) getActivity()).getDataManager();
            List<LostFoundItem> items = dataManager.getAllItems();
            
            // Convert to MyItem objects for display
            for (LostFoundItem item : items) {
                MyItem myItem = new MyItem(
                    item.getTitle(),
                    item.getItemType(),
                    item.getStatus(),
                    item.getCategory(),
                    item.getLocation(),
                    item.getDescription(),
                    item.getTimeAgo()
                );
                myItem.setLostFoundItem(item); // Keep reference to original item
                allItemsList.add(myItem);
            }
        } else {
            // Fallback sample data if activity is not available
            allItemsList.addAll(Arrays.asList(
                new MyItem("iPhone 13 Pro - Blue", "Lost", "Active", "Electronics", 
                    "Library 2nd Floor", "Blue iPhone with black case", "2 days ago"),
                new MyItem("Red Backpack", "Found", "Active", "Bags", 
                    "Main Cafeteria", "Large red backpack with laptop compartment", "1 day ago"),
                new MyItem("Car Keys - BMW", "Lost", "Resolved", "Personal Items", 
                    "Parking Lot B", "BMW car keys with leather keychain", "5 days ago"),
                new MyItem("Mathematics Textbook", "Found", "Active", "Books", 
                    "Room 204", "Calculus textbook by James Stewart", "3 days ago")
            ));
        }
    }

    private void setupFilterButtons() {
        View.OnClickListener filterClickListener = v -> {
            // Reset all button colors
            resetFilterButtonColors();
            
            // Set selected button color
            Button clickedButton = (Button) v;
            clickedButton.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_blue_light));
            
            // Apply filter
            if (v == btnFilterAll) {
                currentFilter = "All";
            } else if (v == btnFilterLost) {
                currentFilter = "Lost";
            } else if (v == btnFilterFound) {
                currentFilter = "Found";
            } else if (v == btnFilterActive) {
                currentFilter = "Active";
            }
            
            applyFilter();
        };

        btnFilterAll.setOnClickListener(filterClickListener);
        btnFilterLost.setOnClickListener(filterClickListener);
        btnFilterFound.setOnClickListener(filterClickListener);
        btnFilterActive.setOnClickListener(filterClickListener);
    }

    private void setupEmptyStateButton() {
        btnAddFirstItem.setOnClickListener(v -> {
            if (getActivity() instanceof CampusLostFoundActivity) {
                ((CampusLostFoundActivity) getActivity()).showAddItemFragment("");
            }
        });
    }

    private void resetFilterButtonColors() {
        int defaultColor = android.R.color.darker_gray;
        btnFilterAll.setBackgroundTintList(getContext().getColorStateList(defaultColor));
        btnFilterLost.setBackgroundTintList(getContext().getColorStateList(defaultColor));
        btnFilterFound.setBackgroundTintList(getContext().getColorStateList(defaultColor));
        btnFilterActive.setBackgroundTintList(getContext().getColorStateList(defaultColor));
    }

    private void applyFilter() {
        filteredItemsList.clear();
        
        for (MyItem item : allItemsList) {
            boolean shouldInclude = false;
            
            switch (currentFilter) {
                case "All":
                    shouldInclude = true;
                    break;
                case "Lost":
                    shouldInclude = "Lost".equals(item.getType());
                    break;
                case "Found":
                    shouldInclude = "Found".equals(item.getType());
                    break;
                case "Active":
                    shouldInclude = "Active".equals(item.getStatus());
                    break;
            }
            
            if (shouldInclude) {
                filteredItemsList.add(item);
            }
        }
        
        updateDisplay();
    }

    private void updateDisplay() {
        tvItemCount.setText("You have posted " + allItemsList.size() + " items");
        
        if (filteredItemsList.isEmpty()) {
            lvMyItems.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            lvMyItems.setVisibility(View.VISIBLE);
            layoutEmptyState.setVisibility(View.GONE);
            
            if (adapter == null) {
                adapter = new MyItemsAdapter();
                lvMyItems.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void editItem(MyItem item, int position) {
        Toast.makeText(getContext(), "Edit: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        // In real app, navigate to edit form
    }

    private void markItemResolved(MyItem item, int position) {
        new AlertDialog.Builder(getContext())
            .setTitle("Mark as Resolved")
            .setMessage("Are you sure you want to mark '" + item.getTitle() + "' as resolved?")
            .setPositiveButton("Yes", (dialog, which) -> {
                item.setStatus("Resolved");
                
                // Update in data manager through main activity
                if (getActivity() instanceof CampusLostFoundActivity && item.getLostFoundItem() != null) {
                    ((CampusLostFoundActivity) getActivity()).markItemAsResolved(item.getLostFoundItem());
                }
                
                adapter.notifyDataSetChanged();
            })
            .setNegativeButton("No", null)
            .show();
    }

    private void deleteItem(MyItem item, int position) {
        new AlertDialog.Builder(getContext())
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete '" + item.getTitle() + "'? This action cannot be undone.")
            .setPositiveButton("Delete", (dialog, which) -> {
                // Remove from data manager through main activity
                if (getActivity() instanceof CampusLostFoundActivity && item.getLostFoundItem() != null) {
                    ((CampusLostFoundActivity) getActivity()).deleteItem(item.getLostFoundItem());
                }
                
                allItemsList.remove(item);
                filteredItemsList.remove(item);
                adapter.notifyDataSetChanged();
                updateDisplay();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    // Custom adapter for my items list
    private class MyItemsAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return filteredItemsList.size();
        }

        @Override
        public Object getItem(int position) {
            return filteredItemsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_my_item, parent, false);
                holder = new ViewHolder();
                holder.tvItemType = convertView.findViewById(R.id.tvItemType);
                holder.tvItemStatus = convertView.findViewById(R.id.tvItemStatus);
                holder.tvPostDate = convertView.findViewById(R.id.tvPostDate);
                holder.tvItemTitle = convertView.findViewById(R.id.tvItemTitle);
                holder.tvCategory = convertView.findViewById(R.id.tvCategory);
                holder.tvLocation = convertView.findViewById(R.id.tvLocation);
                holder.tvDescription = convertView.findViewById(R.id.tvDescription);
                holder.btnEdit = convertView.findViewById(R.id.btnEdit);
                holder.btnMarkResolved = convertView.findViewById(R.id.btnMarkResolved);
                holder.btnDelete = convertView.findViewById(R.id.btnDelete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MyItem item = filteredItemsList.get(position);
            
            // Set item data
            holder.tvItemType.setText(item.getType());
            holder.tvItemStatus.setText(item.getStatus());
            holder.tvPostDate.setText(item.getPostDate());
            holder.tvItemTitle.setText(item.getTitle());
            holder.tvCategory.setText("📂 " + item.getCategory());
            holder.tvLocation.setText("📍 " + item.getLocation());
            holder.tvDescription.setText(item.getDescription());
            
            // Set colors based on type and status
            if ("Lost".equals(item.getType())) {
                holder.tvItemType.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_red_dark));
            } else {
                holder.tvItemType.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_green_dark));
            }
            
            if ("Active".equals(item.getStatus())) {
                holder.tvItemStatus.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_green_light));
            } else {
                holder.tvItemStatus.setBackgroundTintList(getContext().getColorStateList(android.R.color.darker_gray));
            }
            
            // Set button click listeners
            holder.btnEdit.setOnClickListener(v -> editItem(item, position));
            holder.btnMarkResolved.setOnClickListener(v -> markItemResolved(item, position));
            holder.btnDelete.setOnClickListener(v -> deleteItem(item, position));
            
            // Disable mark resolved button if already resolved
            holder.btnMarkResolved.setEnabled("Active".equals(item.getStatus()));
            
            return convertView;
        }
    }

    // ViewHolder pattern for better performance
    private static class ViewHolder {
        TextView tvItemType, tvItemStatus, tvPostDate, tvItemTitle;
        TextView tvCategory, tvLocation, tvDescription;
        Button btnEdit, btnMarkResolved, btnDelete;
    }

    // Simple data class for items
    private static class MyItem {
        private String title, type, status, category, location, description, postDate;
        private LostFoundItem lostFoundItem; // Reference to original item

        public MyItem(String title, String type, String status, String category, 
                     String location, String description, String postDate) {
            this.title = title;
            this.type = type;
            this.status = status;
            this.category = category;
            this.location = location;
            this.description = description;
            this.postDate = postDate;
        }

        // Getters and setters
        public String getTitle() { return title; }
        public String getType() { return type; }
        public String getStatus() { return status; }
        public String getCategory() { return category; }
        public String getLocation() { return location; }
        public String getDescription() { return description; }
        public String getPostDate() { return postDate; }
        
        public void setStatus(String status) { 
            this.status = status;
            if (lostFoundItem != null) {
                lostFoundItem.setStatus(status);
            }
        }
        
        public LostFoundItem getLostFoundItem() { return lostFoundItem; }
        public void setLostFoundItem(LostFoundItem item) { this.lostFoundItem = item; }
    }
}