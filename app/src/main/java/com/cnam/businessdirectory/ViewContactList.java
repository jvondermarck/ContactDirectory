package com.cnam.businessdirectory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewContactList extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "BUSINESS_CONTACT";

    ListView listViewContact;
    List<BusinessContact> contactList;
    ContactAdapter contactListAdapter;
    MyBusContactDbHelper m_busContactDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_list);

        m_busContactDbHelper = new MyBusContactDbHelper(this);
        // TODO 10 use the createDefaultContactsIfNeeded from dbHelper to display default Contacts in main Activity
        m_busContactDbHelper.createDefaultContactsIfNeeded();

        this.contactList = m_busContactDbHelper.getAllContacts();

        listViewContact = findViewById(R.id.listViewContact);
        contactListAdapter = new ContactAdapter(this, contactList);
        listViewContact.setAdapter(contactListAdapter);

        // todo 21 : manage list to either mosify or delete a contact
        listViewContact.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, CreateContactActivity.class);
            intent.putExtra(EXTRA_CONTACT, contactList.get(position));
            startActivity(intent);
        });
    }

    // onResume is called when the activity is visible
    @Override
    protected void onResume() {
        super.onResume();
        // refresh the list of contacts
        if(contactList != null) {
            contactList.clear();
            contactList.addAll(m_busContactDbHelper.getAllContacts());
        }
        contactListAdapter.notifyDataSetChanged(); // refresh the list
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addBusinessContact) {
            startActivity(new Intent(this, CreateContactActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}