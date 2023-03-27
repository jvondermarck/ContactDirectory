package com.cnam.businessdirectory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<BusinessContact> contacts;

    public ContactAdapter(Context context, List<BusinessContact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return (contacts == null) ? 0 : contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.contacts.get(i).getContactId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View contactItemView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contactItemView = inflater.inflate(R.layout.contact_list_item, null);
        } else {
            contactItemView = convertView;
        }

        TextView text1 = contactItemView.findViewById(R.id.textViewFullName);
        TextView text2 = contactItemView.findViewById(R.id.textViewAdress);

        BusinessContact contact = this.contacts.get(position);

        text1.setText(contact.getFirstName() + " " + contact.getLastName());
        text2.setText(contact.getAddress());

        return contactItemView;
    }
}
