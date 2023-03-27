package com.cnam.businessdirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyBusContactDbHelper extends SQLiteOpenHelper {


    private static final String LOG_TAG = "MyBusContactDbHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BusinessContact";

    // Table name: Contact.
    private static final String TABLE_CONTACT = "Contact";

    private static final String COLUMN_CONTACT_ID = "id";
    private static final String COLUMN_CONTACT_FIRSTNAME = "firstname";
    private static final String COLUMN_CONTACT_LASTNAME = "lastname";
    private static final String COLUMN_CONTACT_ADDRESS = "address";
    private static final String COLUMN_CONTACT_PHONE = "phone";

    private static final int COLUMN_CONTACT_ID_POSITION = 0;
    private static final int COLUMN_CONTACT_FIRSTNAME_POSITION = 1;
    private static final int COLUMN_CONTACT_LASTNAME_POSITION = 2;
    private static final int COLUMN_CONTACT_ADDRESS_POSITION = 3;
    private static final int COLUMN_CONTACT_PHONE_POSITION = 4;

    public MyBusContactDbHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 1 create database here
        db.execSQL("CREATE TABLE " + TABLE_CONTACT + "("
                + COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTACT_FIRSTNAME + " TEXT,"
                + COLUMN_CONTACT_LASTNAME + " TEXT,"
                + COLUMN_CONTACT_ADDRESS + " TEXT,"
                + COLUMN_CONTACT_PHONE + " TEXT" + ")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 2 manage Upgrade database here
        // Just do DROP TABLE
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
    }

    public void createDefaultContactsIfNeeded()  {
        int count = this.getContactsCount();
        if(count == 0 ) {
            // TODO 9 create 2 contacts in database
            BusinessContact contact1 = new BusinessContact(2,"John", "Doe", "1 rue de la paix", "0606060606");
            BusinessContact contact2 = new BusinessContact(3,"Jane", "Doe", "2 rue de la paix", "0606060607");
            this.addContact(contact1);
            this.addContact(contact2);
        }
    }


    public void addContact(BusinessContact contact) {
        // TODO 3 create contact in database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_FIRSTNAME, contact.getFirstName());
        values.put(COLUMN_CONTACT_LASTNAME, contact.getLastName());
        values.put(COLUMN_CONTACT_ADDRESS, contact.getAddress());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhoneNumber());

        db.insert(TABLE_CONTACT, null, values);
        db.close();
    }


    public BusinessContact getContact(int id) {
        // TODO 4 get contact from database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACT, new String[] { COLUMN_CONTACT_ID,
                        COLUMN_CONTACT_FIRSTNAME, COLUMN_CONTACT_LASTNAME, COLUMN_CONTACT_ADDRESS, COLUMN_CONTACT_PHONE }, COLUMN_CONTACT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BusinessContact contact = new BusinessContact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return contact;
    }


    public List<BusinessContact> getAllContacts() {
        Log.i(LOG_TAG, "getAllContacts");

        // TODO 5 get ALL contacts from database
        List<BusinessContact> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                BusinessContact contact = new BusinessContact();
                contact.setContactId(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setAddress(cursor.getString(3));
                contact.setPhoneNumber(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        } else {
            Log.i(LOG_TAG, "No contact found");
        }

        return contactList;
    }

    public int getContactsCount() {
        Log.i(LOG_TAG, ">getContactsCount");

        // TODO 6 get contacts number from database
        String countQuery = "SELECT  * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int updateContact(BusinessContact contact) {
        Log.i(LOG_TAG, ">updateContact");

        // TODO 7 update contact in database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_FIRSTNAME, contact.getFirstName());
        values.put(COLUMN_CONTACT_LASTNAME, contact.getLastName());
        values.put(COLUMN_CONTACT_ADDRESS, contact.getAddress());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhoneNumber());

        return db.update(TABLE_CONTACT, values, COLUMN_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contact.getContactId()) });
    }

    public void deleteContact(BusinessContact contact) {
        Log.i(LOG_TAG, ">deleteContact");

        // TODO 8 delete contact from database
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, COLUMN_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contact.getContactId()) });
        db.close();
    }

}