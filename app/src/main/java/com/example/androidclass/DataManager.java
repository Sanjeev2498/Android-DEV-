package com.example.androidclass;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DataManager {
    
    private static DataManager instance;
    private ArrayList<LostFoundItem> allItems;
    private Context context;
    
    // Singleton pattern
    private DataManager(Context context) {
        this.context = context.getApplicationContext();
        this.allItems = new ArrayList<>();
        initializeSampleData();
    }
    
    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }
    
    // Initialize with sample data
    private void initializeSampleData() {
        // Sample lost items
        LostFoundItem item1 = new LostFoundItem(
            "iPhone 13 Pro - Blue", "Lost", "Electronics (Phone, Laptop, Tablet)",
            "Blue iPhone 13 Pro with black protective case. Has a small scratch on the back cover. Screen protector applied.",
            "Library 2nd Floor - Study Area", "John Smith", "john.smith@college.edu"
        );
        item1.setContactPhone("9876543210");
        
        LostFoundItem item2 = new LostFoundItem(
            "Red Backpack - Adidas", "Found", "Bags & Backpacks",
            "Large red Adidas backpack with laptop compartment. Found some notebooks and pens inside.",
            "Main Cafeteria - Near Entry", "Sarah Johnson", "sarah.j@college.edu"
        );
        item2.setContactPhone("9876543211");
        
        LostFoundItem item3 = new LostFoundItem(
            "Car Keys - BMW", "Lost", "Personal Items (Wallet, Keys, Cards)",
            "BMW car keys with black leather keychain. Has BMW logo and remote control buttons.",
            "Parking Lot B - Section 3", "Mike Davis", "mike.davis@college.edu"
        );
        item3.setStatus("Resolved");
        
        LostFoundItem item4 = new LostFoundItem(
            "Mathematics Textbook", "Found", "Books & Stationery",
            "Calculus textbook by James Stewart, 8th Edition. Has handwritten notes in margins.",
            "Room 204 - Mathematics Department", "Lisa Chen", "lisa.chen@college.edu"
        );
        
        LostFoundItem item5 = new LostFoundItem(
            "Wireless Headphones", "Found", "Electronics (Phone, Laptop, Tablet)",
            "Black Sony wireless headphones with carrying case. Battery seems to be working fine.",
            "Gym - Locker Room Area", "Alex Rodriguez", "alex.r@college.edu"
        );
        
        LostFoundItem item6 = new LostFoundItem(
            "Student ID Card", "Lost", "Personal Items (Wallet, Keys, Cards)",
            "Student ID card for Emma Wilson, Student Number: STU2024001. Blue colored card.",
            "Student Union Building", "Emma Wilson", "emma.wilson@college.edu"
        );
        
        // Add items with different timestamps to simulate realistic data
        long currentTime = System.currentTimeMillis();
        item1.setDateReported(new Date(currentTime - (2 * 24 * 60 * 60 * 1000))); // 2 days ago
        item2.setDateReported(new Date(currentTime - (1 * 24 * 60 * 60 * 1000))); // 1 day ago
        item3.setDateReported(new Date(currentTime - (5 * 24 * 60 * 60 * 1000))); // 5 days ago
        item4.setDateReported(new Date(currentTime - (3 * 24 * 60 * 60 * 1000))); // 3 days ago
        item5.setDateReported(new Date(currentTime - (4 * 60 * 60 * 1000))); // 4 hours ago
        item6.setDateReported(new Date(currentTime - (6 * 60 * 60 * 1000))); // 6 hours ago
        
        allItems.add(item1);
        allItems.add(item2);
        allItems.add(item3);
        allItems.add(item4);
        allItems.add(item5);
        allItems.add(item6);
    }
    
    // CRUD Operations
    
    // Create - Add new item
    public void addItem(LostFoundItem item) {
        if (item != null && !allItems.contains(item)) {
            allItems.add(item);
        }
    }
    
    // Read - Get all items
    public List<LostFoundItem> getAllItems() {
        return new ArrayList<>(allItems);
    }
    
    // Read - Get item by ID
    public LostFoundItem getItemById(String id) {
        for (LostFoundItem item : allItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
    
    // Update - Update existing item
    public boolean updateItem(LostFoundItem updatedItem) {
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getId().equals(updatedItem.getId())) {
                allItems.set(i, updatedItem);
                return true;
            }
        }
        return false;
    }
    
    // Delete - Remove item
    public boolean deleteItem(String id) {
        return allItems.removeIf(item -> item.getId().equals(id));
    }
    
    public boolean deleteItem(LostFoundItem item) {
        return allItems.remove(item);
    }
    
    // Filtered queries
    
    // Get items by type (Lost/Found)
    public List<LostFoundItem> getItemsByType(String type) {
        List<LostFoundItem> filteredItems = new ArrayList<>();
        for (LostFoundItem item : allItems) {
            if (type.equals(item.getItemType())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    
    // Get items by status (Active/Resolved/Inactive)
    public List<LostFoundItem> getItemsByStatus(String status) {
        List<LostFoundItem> filteredItems = new ArrayList<>();
        for (LostFoundItem item : allItems) {
            if (status.equals(item.getStatus())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    
    // Get items by category
    public List<LostFoundItem> getItemsByCategory(String category) {
        List<LostFoundItem> filteredItems = new ArrayList<>();
        for (LostFoundItem item : allItems) {
            if (category.equals(item.getCategory())) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }
    
    // Search items
    public List<LostFoundItem> searchItems(String query) {
        List<LostFoundItem> searchResults = new ArrayList<>();
        for (LostFoundItem item : allItems) {
            if (item.matchesSearch(query)) {
                searchResults.add(item);
            }
        }
        return searchResults;
    }
    
    // Get recent items (latest first)
    public List<LostFoundItem> getRecentItems(int limit) {
        List<LostFoundItem> sortedItems = new ArrayList<>(allItems);
        Collections.sort(sortedItems, new Comparator<LostFoundItem>() {
            @Override
            public int compare(LostFoundItem item1, LostFoundItem item2) {
                return item2.getDateReported().compareTo(item1.getDateReported());
            }
        });
        
        if (limit > 0 && sortedItems.size() > limit) {
            return sortedItems.subList(0, limit);
        }
        return sortedItems;
    }
    
    // Statistics
    public int getTotalItemCount() {
        return allItems.size();
    }
    
    public int getLostItemCount() {
        return getItemsByType("Lost").size();
    }
    
    public int getFoundItemCount() {
        return getItemsByType("Found").size();
    }
    
    public int getActiveItemCount() {
        return getItemsByStatus("Active").size();
    }
    
    public int getResolvedItemCount() {
        return getItemsByStatus("Resolved").size();
    }
    
    // Helper methods for UI
    public List<String> getAvailableCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Electronics (Phone, Laptop, Tablet)");
        categories.add("Personal Items (Wallet, Keys, Cards)");
        categories.add("Clothing & Accessories");
        categories.add("Books & Stationery");
        categories.add("Bags & Backpacks");
        categories.add("Sports Equipment");
        categories.add("Jewelry & Watches");
        categories.add("Other");
        return categories;
    }
    
    public List<String> getAllLocations() {
        List<String> locations = new ArrayList<>();
        for (LostFoundItem item : allItems) {
            if (!locations.contains(item.getLocation())) {
                locations.add(item.getLocation());
            }
        }
        Collections.sort(locations);
        return locations;
    }
    
    // Mark item as resolved
    public boolean markItemAsResolved(String itemId) {
        LostFoundItem item = getItemById(itemId);
        if (item != null) {
            item.setStatus("Resolved");
            return true;
        }
        return false;
    }
    
    // Increment view count
    public void incrementViewCount(String itemId) {
        LostFoundItem item = getItemById(itemId);
        if (item != null) {
            item.incrementViewCount();
        }
    }
    
    // Clear all data (for testing purposes)
    public void clearAllData() {
        allItems.clear();
    }
    
    // Reset to sample data
    public void resetToSampleData() {
        allItems.clear();
        initializeSampleData();
    }
}