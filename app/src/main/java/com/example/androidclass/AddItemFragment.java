package com.example.androidclass;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddItemFragment extends Fragment {

    private RadioGroup rgItemType;
    private RadioButton rbLost, rbFound;
    private EditText etItemTitle, etDescription, etLocation, etDate;
    private EditText etContactName, etContactPhone, etContactEmail;
    private Spinner spinnerCategory;
    private Button btnSelectDate, btnSubmitItem;
    private ProgressBar progressBar;

    private Calendar calendar;
    private String selectedItemType = "Lost";

    public AddItemFragment() {
        // Required empty public constructor
    }

    public static AddItemFragment newInstance() {
        return new AddItemFragment();
    }

    public static AddItemFragment newInstance(String itemType) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString("itemType", itemType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        
        initializeViews(view);
        setupSpinner();
        setupDatePicker();
        setupClickListeners();
        handleArguments();
        
        return view;
    }

    private void initializeViews(View view) {
        rgItemType = view.findViewById(R.id.rgItemType);
        rbLost = view.findViewById(R.id.rbLost);
        rbFound = view.findViewById(R.id.rbFound);
        etItemTitle = view.findViewById(R.id.etItemTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etLocation = view.findViewById(R.id.etLocation);
        etDate = view.findViewById(R.id.etDate);
        etContactName = view.findViewById(R.id.etContactName);
        etContactPhone = view.findViewById(R.id.etContactPhone);
        etContactEmail = view.findViewById(R.id.etContactEmail);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        btnSubmitItem = view.findViewById(R.id.btnSubmitItem);
        progressBar = view.findViewById(R.id.progressBar);

        calendar = Calendar.getInstance();
    }

    private void setupSpinner() {
        String[] categories = {
            "Select Category",
            "Electronics (Phone, Laptop, Tablet)",
            "Personal Items (Wallet, Keys, Cards)",
            "Clothing & Accessories",
            "Books & Stationery",
            "Bags & Backpacks",
            "Sports Equipment",
            "Jewelry & Watches",
            "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), 
            android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void setupDatePicker() {
        // Set current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private void setupClickListeners() {
        // Radio group listener
        rgItemType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLost) {
                selectedItemType = "Lost";
                btnSubmitItem.setText("REPORT LOST ITEM");
                btnSubmitItem.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_red_dark));
            } else if (checkedId == R.id.rbFound) {
                selectedItemType = "Found";
                btnSubmitItem.setText("REPORT FOUND ITEM");
                btnSubmitItem.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_green_dark));
            }
        });

        // Date picker button
        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());

        // Submit button
        btnSubmitItem.setOnClickListener(v -> submitItem());
    }

    private void handleArguments() {
        if (getArguments() != null) {
            String itemType = getArguments().getString("itemType", "Lost");
            if ("Found".equals(itemType)) {
                rbFound.setChecked(true);
                selectedItemType = "Found";
                btnSubmitItem.setText("REPORT FOUND ITEM");
                btnSubmitItem.setBackgroundTintList(getContext().getColorStateList(android.R.color.holo_green_dark));
            }
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            getContext(),
            (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                etDate.setText(sdf.format(calendar.getTime()));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        datePickerDialog.show();
    }

    private void submitItem() {
        // Validate form
        if (!validateForm()) {
            return;
        }

        // Show progress
        progressBar.setVisibility(View.VISIBLE);
        btnSubmitItem.setEnabled(false);

        // Create new item from form data
        String title = etItemTitle.getText().toString().trim();
        String category = (String) spinnerCategory.getSelectedItem();
        String description = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String contactName = etContactName.getText().toString().trim();
        String contactEmail = etContactEmail.getText().toString().trim();
        String contactPhone = etContactPhone.getText().toString().trim();

        // Create new LostFoundItem
        LostFoundItem newItem = new LostFoundItem(title, selectedItemType, category, 
            description, location, contactName, contactEmail);
        
        if (!contactPhone.isEmpty()) {
            newItem.setContactPhone(contactPhone);
        }
        
        // Set date occurred from form
        try {
            String dateString = etDate.getText().toString();
            // In real app, parse the date properly
            // For now, we'll use current date
            newItem.setDateOccurred(calendar.getTime());
        } catch (Exception e) {
            // Use current date as fallback
        }

        // Simulate posting item (in real app, this would save to database/server)
        new android.os.Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            btnSubmitItem.setEnabled(true);

            // Add item through main activity
            if (getActivity() instanceof CampusLostFoundActivity) {
                ((CampusLostFoundActivity) getActivity()).addNewItem(newItem);
            }

            // Clear form
            clearForm();
        }, 2000);
    }

    private boolean validateForm() {
        String title = etItemTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String contactName = etContactName.getText().toString().trim();
        String contactEmail = etContactEmail.getText().toString().trim();

        if (title.isEmpty()) {
            etItemTitle.setError("Please enter item title");
            etItemTitle.requestFocus();
            return false;
        }

        if (spinnerCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description.isEmpty()) {
            etDescription.setError("Please provide item description");
            etDescription.requestFocus();
            return false;
        }

        if (location.isEmpty()) {
            etLocation.setError("Please specify location");
            etLocation.requestFocus();
            return false;
        }

        if (contactName.isEmpty()) {
            etContactName.setError("Please enter your name");
            etContactName.requestFocus();
            return false;
        }

        if (contactEmail.isEmpty()) {
            etContactEmail.setError("Please enter your email");
            etContactEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(contactEmail).matches()) {
            etContactEmail.setError("Please enter valid email address");
            etContactEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void clearForm() {
        etItemTitle.setText("");
        etDescription.setText("");
        etLocation.setText("");
        etContactName.setText("");
        etContactPhone.setText("");
        etContactEmail.setText("");
        spinnerCategory.setSelection(0);
        rbLost.setChecked(true);
        
        // Reset date to current date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etDate.setText(sdf.format(Calendar.getInstance().getTime()));
    }
}