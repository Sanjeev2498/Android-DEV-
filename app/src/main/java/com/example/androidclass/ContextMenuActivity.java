package com.example.androidclass;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class ContextMenuActivity extends AppCompatActivity {

    private ListView listViewContacts;
    private ArrayList<Contact> contactList;
    private ContactAdapter adapter;
    private int selectedContactPosition = -1;
    private static final String TAG = "ContextMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);

        Log.d(TAG, "onCreate called");
        
        initializeViews();
        setupContactData();
        setupListView();
    }

    private void initializeViews() {
        listViewContacts = findViewById(R.id.listViewContacts);
        Log.d(TAG, "ListView initialized: " + (listViewContacts != null));
    }

    private void setupContactData() {
        contactList = new ArrayList<>();
        
        // Add sample contacts as shown in the image
        contactList.addAll(Arrays.asList(
            new Contact("Amit Sharma", "amit@email.com", "9876543210"),
            new Contact("Pooja Verma", "pooja@email.com", "9876543211"),
            new Contact("Rahul Mehta", "rahul@email.com", "9876543212"),
            new Contact("Sneha Gupta", "sneha@email.com", "9876543213"),
            new Contact("Nisha Kapoor", "nisha@email.com", "9876543214"),
            new Contact("Vikash Kumar", "vikash@email.com", "9876543215"),
            new Contact("Priya Singh", "priya@email.com", "9876543216"),
            new Contact("Ravi Patel", "ravi@email.com", "9876543217")
        ));
        
        Log.d(TAG, "Contact list size: " + contactList.size());
        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            Log.d(TAG, "Contact " + i + ": " + contact.getName() + " - " + contact.getEmail());
        }
    }

    private void setupListView() {
        adapter = new ContactAdapter();
        listViewContacts.setAdapter(adapter);
        
        Log.d(TAG, "Adapter set, count: " + adapter.getCount());
        
        // Register ListView for context menu
        registerForContextMenu(listViewContacts);
        
        // Set item click listener for regular clicks
        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                Toast.makeText(ContextMenuActivity.this, 
                    "Clicked: " + contact.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        
        if (v.getId() == R.id.listViewContacts) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            selectedContactPosition = info.position;
            
            Contact selectedContact = contactList.get(selectedContactPosition);
            menu.setHeaderTitle(selectedContact.getName());
            
            getMenuInflater().inflate(R.menu.contact_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (selectedContactPosition == -1) {
            return super.onContextItemSelected(item);
        }
        
        Contact selectedContact = contactList.get(selectedContactPosition);
        int itemId = item.getItemId();
        
        if (itemId == R.id.menu_call) {
            callContact(selectedContact);
            return true;
        } else if (itemId == R.id.menu_send_email) {
            sendEmail(selectedContact);
            return true;
        } else if (itemId == R.id.menu_edit_contact) {
            editContact(selectedContact);
            return true;
        } else if (itemId == R.id.menu_delete_contact) {
            deleteContact(selectedContactPosition);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void callContact(Contact contact) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + contact.getPhone()));
        
        try {
            startActivity(callIntent);
            Toast.makeText(this, "Calling " + contact.getName(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Unable to make call", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(Contact contact) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + contact.getEmail()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello " + contact.getName());
        
        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
            Toast.makeText(this, "Opening email to " + contact.getName(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void editContact(Contact contact) {
        Toast.makeText(this, "Edit Contact: " + contact.getName() + 
            "\nEmail: " + contact.getEmail() + 
            "\nPhone: " + contact.getPhone(), Toast.LENGTH_LONG).show();
    }

    private void deleteContact(int position) {
        Contact contactToDelete = contactList.get(position);
        contactList.remove(position);
        adapter.notifyDataSetChanged();
        
        Toast.makeText(this, "Deleted: " + contactToDelete.getName(), Toast.LENGTH_SHORT).show();
        selectedContactPosition = -1;
    }

    // Custom adapter for the contact list
    private class ContactAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return contactList.size();
        }

        @Override
        public Object getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            
            Log.d(TAG, "getView called for position: " + position);
            
            if (convertView == null) {
                convertView = LayoutInflater.from(ContextMenuActivity.this)
                    .inflate(R.layout.contact_item, parent, false);
                holder = new ViewHolder();
                holder.tvContactInitial = convertView.findViewById(R.id.tvContactInitial);
                holder.tvContactName = convertView.findViewById(R.id.tvContactName);
                holder.tvContactEmail = convertView.findViewById(R.id.tvContactEmail);
                convertView.setTag(holder);
                Log.d(TAG, "New view created for position: " + position);
            } else {
                holder = (ViewHolder) convertView.getTag();
                Log.d(TAG, "Reusing view for position: " + position);
            }

            Contact contact = contactList.get(position);
            String initial = contact.getInitial();
            String name = contact.getName();
            String email = contact.getEmail();
            
            Log.d(TAG, "Setting data - Initial: " + initial + ", Name: " + name + ", Email: " + email);
            
            holder.tvContactInitial.setText(initial);
            holder.tvContactName.setText(name);
            holder.tvContactEmail.setText(email);
            
            // Set different colors for different contacts
            int[] colors = {
                Color.parseColor("#FF5722"), // Red-Orange
                Color.parseColor("#E91E63"), // Pink
                Color.parseColor("#9C27B0"), // Purple
                Color.parseColor("#673AB7"), // Deep Purple
                Color.parseColor("#3F51B5"), // Indigo
                Color.parseColor("#2196F3"), // Blue
                Color.parseColor("#00BCD4"), // Cyan
                Color.parseColor("#4CAF50")  // Green
            };
            
            holder.tvContactInitial.setBackgroundColor(colors[position % colors.length]);

            return convertView;
        }
    }

    // ViewHolder pattern for better performance
    private static class ViewHolder {
        TextView tvContactInitial;
        TextView tvContactName;
        TextView tvContactEmail;
    }
}