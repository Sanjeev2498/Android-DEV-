package com.example.androidclass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class LostFoundItem {
    
    // Item properties
    private String id;
    private String title;
    private String description;
    private String category;
    private String location;
    private String itemType; // "Lost" or "Found"
    private String status; // "Active", "Resolved", "Inactive"
    private Date dateReported;
    private Date dateOccurred;
    
    // Contact information
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    
    // Additional properties
    private String imagePath;
    private int viewCount;
    private Date lastUpdated;
    
    // Constructors
    public LostFoundItem() {
        this.id = UUID.randomUUID().toString();
        this.dateReported = new Date();
        this.lastUpdated = new Date();
        this.status = "Active";
        this.viewCount = 0;
    }
    
    public LostFoundItem(String title, String itemType, String category, 
                        String description, String location, String contactName, 
                        String contactEmail) {
        this();
        this.title = title;
        this.itemType = itemType;
        this.category = category;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.dateOccurred = new Date();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { 
        this.title = title;
        updateLastModified();
    }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description;
        updateLastModified();
    }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { 
        this.category = category;
        updateLastModified();
    }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { 
        this.location = location;
        updateLastModified();
    }
    
    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { 
        this.itemType = itemType;
        updateLastModified();
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        updateLastModified();
    }
    
    public Date getDateReported() { return dateReported; }
    public void setDateReported(Date dateReported) { this.dateReported = dateReported; }
    
    public Date getDateOccurred() { return dateOccurred; }
    public void setDateOccurred(Date dateOccurred) { 
        this.dateOccurred = dateOccurred;
        updateLastModified();
    }
    
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { 
        this.contactName = contactName;
        updateLastModified();
    }
    
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { 
        this.contactPhone = contactPhone;
        updateLastModified();
    }
    
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { 
        this.contactEmail = contactEmail;
        updateLastModified();
    }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { 
        this.imagePath = imagePath;
        updateLastModified();
    }
    
    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public void incrementViewCount() { this.viewCount++; }
    
    public Date getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
    
    // Helper methods
    private void updateLastModified() {
        this.lastUpdated = new Date();
    }
    
    public boolean isActive() {
        return "Active".equals(status);
    }
    
    public boolean isResolved() {
        return "Resolved".equals(status);
    }
    
    public boolean isLostItem() {
        return "Lost".equals(itemType);
    }
    
    public boolean isFoundItem() {
        return "Found".equals(itemType);
    }
    
    // Formatted date strings
    public String getFormattedDateReported() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(dateReported);
    }
    
    public String getFormattedDateOccurred() {
        if (dateOccurred == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(dateOccurred);
    }
    
    public String getTimeAgo() {
        long diff = new Date().getTime() - dateReported.getTime();
        long days = diff / (24 * 60 * 60 * 1000);
        
        if (days == 0) {
            long hours = diff / (60 * 60 * 1000);
            if (hours == 0) {
                long minutes = diff / (60 * 1000);
                return minutes + " minutes ago";
            }
            return hours + " hours ago";
        } else if (days == 1) {
            return "1 day ago";
        } else {
            return days + " days ago";
        }
    }
    
    // Category icon helper
    public String getCategoryIcon() {
        switch (category) {
            case "Electronics":
            case "Electronics (Phone, Laptop, Tablet)":
                return "📱";
            case "Personal Items":
            case "Personal Items (Wallet, Keys, Cards)":
                return "🔑";
            case "Clothing & Accessories":
                return "👕";
            case "Books & Stationery":
                return "📚";
            case "Bags & Backpacks":
                return "🎒";
            case "Sports Equipment":
                return "⚽";
            case "Jewelry & Watches":
                return "💍";
            default:
                return "📦";
        }
    }
    
    // Search helper method
    public boolean matchesSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return true;
        }
        
        query = query.toLowerCase().trim();
        
        return (title != null && title.toLowerCase().contains(query)) ||
               (description != null && description.toLowerCase().contains(query)) ||
               (category != null && category.toLowerCase().contains(query)) ||
               (location != null && location.toLowerCase().contains(query));
    }
    
    // Display summary for lists
    public String getDisplaySummary() {
        return getCategoryIcon() + " " + title + " - " + location;
    }
    
    @Override
    public String toString() {
        return "LostFoundItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", itemType='" + itemType + '\'' +
                ", status='" + status + '\'' +
                ", location='" + location + '\'' +
                ", dateReported=" + getFormattedDateReported() +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LostFoundItem that = (LostFoundItem) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}