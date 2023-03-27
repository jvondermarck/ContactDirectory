package com.cnam.businessdirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class CreateContactActivity extends AppCompatActivity {

    private MyBusContactDbHelper m_busContactDbHelper;

    private EditText m_firstName;
    private EditText m_lastname;
    private EditText m_address;
    private EditText m_phone;

    private BusinessContact m_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        m_firstName = findViewById(R.id.firstName);
        // TODO 16 Get controlers for lastname, address and phone
        m_lastname = findViewById(R.id.lastName);
        m_address = findViewById(R.id.address);
        m_phone = findViewById(R.id.phone);

        m_busContactDbHelper = new MyBusContactDbHelper(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) { // on récupère les données du bundle si il existe
            m_contact = (BusinessContact) bundle.getSerializable(ViewContactList.EXTRA_CONTACT);

            // TODO 23 fill firstName lastName address and phone using Contact Getter
            m_firstName.setText(m_contact.getFirstName());
            m_lastname.setText(m_contact.getLastName());
            m_address.setText(m_contact.getAddress());
            m_phone.setText(m_contact.getPhoneNumber());
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        // TODO 17 set Toolbar Title with "Create New Contact"
        // TODO 22 set Toolbar Title with "Create New Contact" or "Update Contact"
        if (m_contact != null) {
            toolbar.setTitle("Update Contact");
        } else {
            toolbar.setTitle("Create New Contact");
        }
        setSupportActionBar(toolbar);

        // Show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // display la flèche de retour
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    // TODO 18 Manage a menu in the toolBar on overriding method onCreateOptionsMenu
    // If we are on mode update contact : hide menu item create
    // and display update and delete
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // display the toolbar menu with the save icon
        getMenuInflater().inflate(R.menu.create_contact_menu, menu);

        if (m_contact != null) {
            menu.findItem(R.id.action_save).setVisible(false);
            menu.findItem(R.id.action_update).setVisible(true);
            menu.findItem(R.id.action_delete).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }


    // TODO 19 Manage the menu toolBar icon click on overriding method onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) { // id of the folder menu/create_contact_menu.xml
            m_busContactDbHelper.addContact(new BusinessContact(
                    m_busContactDbHelper.getContactsCount(),
                    m_firstName.getText().toString(),
                    m_lastname.getText().toString(),
                    m_address.getText().toString(),
                    m_phone.getText().toString()
            ));
            finish();
        } else if(id == R.id.action_update) {
            m_contact.setFirstName(m_firstName.getText().toString());
            m_contact.setLastName(m_lastname.getText().toString());
            m_contact.setAddress(m_address.getText().toString());
            m_contact.setPhoneNumber(m_phone.getText().toString());

            m_busContactDbHelper.updateContact(m_contact);
            finish();
        } else if(id == R.id.action_delete) {
            m_busContactDbHelper.deleteContact(m_contact);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    // TODO 24 Manage update and delete buttons

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}