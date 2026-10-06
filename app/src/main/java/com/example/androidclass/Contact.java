package com.example.androidclass;

public class Contact {
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Get the first letter of the name for the initial circle
    public String getInitial() {
        if (name != null && !name.isEmpty()) {
            return String.valueOf(name.charAt(0)).toUpperCase();
        }
        return "?";
    }

    @Override
    public String toString() {
        return name + " - " + email;
    }
}