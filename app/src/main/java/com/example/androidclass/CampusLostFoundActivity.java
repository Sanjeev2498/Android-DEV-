package com.example.androidclass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CampusLostFoundActivity extends AppCompatActivity {

    private Button btnHome, btnAddItem, btnMyItems;
    private FragmentManager fragmentManager;
    private DataManager dataManager;
    
    // Fragment instances
    private HomeFragment homeFragment;
    private AddItemFragment addItemFragment;
    private MyItemsFragment myItemsFragment;
    
    private String currentFragmentTag = "HOME";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_lost_found);

        // Initialize data manager
        dataManager = DataManager.getInstance(this);
        
        // Initialize views
        initializeViews();
        
        // Setup navigation buttons
        setupNavigationButtons();
        
        // Initialize fragment manager
        fragmentManager = getSupportFragmentManager();
        
        // Show home fragment by default
        if (savedInstanceState == null) {
            showHomeFragment();
        }
    }

    private void initializeViews() {
        btnHome = findViewById(R.id.btnHome);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnMyItems = findViewById(R.id.btnMyItems);
    }

    private void setupNavigationButtons() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeFragment();
                updateNavigationButtons("HOME");
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemFragment("");
                updateNavigationButtons("ADD_ITEM");
            }
        });

        btnMyItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyItemsFragment();
                updateNavigationButtons("MY_ITEMS");
            }
        });
    }

    // Fragment navigation methods
    public void showHomeFragment() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        
        replaceFragment(homeFragment, "HOME");
        currentFragmentTag = "HOME";
        
        // Refresh home fragment data
        if (homeFragment != null) {
            homeFragment.refreshData();
        }
    }

    public void showAddItemFragment(String itemType) {
        if (itemType != null && !itemType.isEmpty()) {
            // Create new instance with pre-selected type
            addItemFragment = AddItemFragment.newInstance(itemType);
        } else {
            // Create new instance without pre-selection
            addItemFragment = AddItemFragment.newInstance();
        }
        
        replaceFragment(addItemFragment, "ADD_ITEM");
        currentFragmentTag = "ADD_ITEM";
    }

    public void showMyItemsFragment() {
        if (myItemsFragment == null) {
            myItemsFragment = MyItemsFragment.newInstance();
        }
        
        replaceFragment(myItemsFragment, "MY_ITEMS");
        currentFragmentTag = "MY_ITEMS";
    }

    // Generic method to replace fragments
    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment, tag);
        
        // Add to back stack only if it's not the home fragment
        if (!"HOME".equals(tag)) {
            transaction.addToBackStack(tag);
        }
        
        transaction.commit();
    }

    // Update navigation button states
    private void updateNavigationButtons(String activeTag) {
        // Reset all buttons to default state
        resetNavigationButtonColors();
        
        // Highlight active button
        switch (activeTag) {
            case "HOME":
                btnHome.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_bright));
                break;
            case "ADD_ITEM":
                btnAddItem.setBackgroundTintList(getColorStateList(android.R.color.holo_green_dark));
                break;
            case "MY_ITEMS":
                btnMyItems.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
                break;
        }
    }

    private void resetNavigationButtonColors() {
        btnHome.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_light));
        btnAddItem.setBackgroundTintList(getColorStateList(android.R.color.holo_green_dark));
        btnMyItems.setBackgroundTintList(getColorStateList(android.R.color.holo_orange_dark));
    }

    // Data passing methods for fragments
    public void addNewItem(LostFoundItem item) {
        if (item != null) {
            dataManager.addItem(item);
            
            // Show success message
            String message = item.getItemType() + " item '" + item.getTitle() + "' posted successfully!";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            
            // Refresh fragments if they are active
            refreshFragments();
            
            // Navigate back to home
            showHomeFragment();
            updateNavigationButtons("HOME");
        }
    }

    public void updateItem(LostFoundItem item) {
        if (item != null) {
            boolean success = dataManager.updateItem(item);
            if (success) {
                Toast.makeText(this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                refreshFragments();
            } else {
                Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void deleteItem(LostFoundItem item) {
        if (item != null) {
            boolean success = dataManager.deleteItem(item);
            if (success) {
                Toast.makeText(this, "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                refreshFragments();
            } else {
                Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void markItemAsResolved(LostFoundItem item) {
        if (item != null) {
            item.setStatus("Resolved");
            boolean success = dataManager.updateItem(item);
            if (success) {
                Toast.makeText(this, "Item marked as resolved!", Toast.LENGTH_SHORT).show();
                refreshFragments();
            }
        }
    }

    // Get data manager instance for fragments
    public DataManager getDataManager() {
        return dataManager;
    }

    // Refresh all active fragments
    private void refreshFragments() {
        // Refresh home fragment
        Fragment homeFragmentInstance = fragmentManager.findFragmentByTag("HOME");
        if (homeFragmentInstance instanceof HomeFragment) {
            ((HomeFragment) homeFragmentInstance).refreshData();
        }
        
        // Note: MyItemsFragment and AddItemFragment will be refreshed when they become visible
    }

    // Handle back button press
    @Override
    public void onBackPressed() {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        
        if (backStackCount > 0) {
            // Pop from back stack
            fragmentManager.popBackStack();
            
            // Update navigation buttons based on current fragment
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof HomeFragment) {
                updateNavigationButtons("HOME");
                currentFragmentTag = "HOME";
            } else if (currentFragment instanceof AddItemFragment) {
                updateNavigationButtons("ADD_ITEM");
                currentFragmentTag = "ADD_ITEM";
            } else if (currentFragment instanceof MyItemsFragment) {
                updateNavigationButtons("MY_ITEMS");
                currentFragmentTag = "MY_ITEMS";
            }
        } else {
            // No fragments in back stack, exit activity
            super.onBackPressed();
        }
    }

    // Get currently visible fragment
    private Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.fragmentContainer);
    }

    // Handle fragment lifecycle
    @Override
    protected void onResume() {
        super.onResume();
        refreshFragments();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save any pending data if needed
    }

    // Utility methods for fragments to communicate with activity
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Navigation helpers for quick actions
    public void navigateToAddLostItem() {
        showAddItemFragment("Lost");
        updateNavigationButtons("ADD_ITEM");
    }

    public void navigateToAddFoundItem() {
        showAddItemFragment("Found");
        updateNavigationButtons("ADD_ITEM");
    }

    public void navigateToMyItems() {
        showMyItemsFragment();
        updateNavigationButtons("MY_ITEMS");
    }

    // Get statistics for home fragment
    public int[] getItemStatistics() {
        return new int[]{
            dataManager.getLostItemCount(),
            dataManager.getFoundItemCount(),
            dataManager.getActiveItemCount(),
            dataManager.getResolvedItemCount(),
            dataManager.getTotalItemCount()
        };
    }
}