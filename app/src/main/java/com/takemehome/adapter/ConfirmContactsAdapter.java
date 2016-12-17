package com.takemehome.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.takemehome.R;
import com.takemehome.model.Contact;

import java.util.List;

/**
 * Created by ruitzei on 12/17/16.
 */

public class ConfirmContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ContactsAdapter.class.getSimpleName();

    private List<Contact> contacts;

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public ConfirmContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "Position" + position + "/" + getItemCount());

        if (holder instanceof ContactsViewHolder) {
            ContactsViewHolder contactsHolder = (ContactsViewHolder) holder;

            final Contact contact = contacts.get(position);

            contactsHolder.textView.setText(contact.getName());

//            contactsHolder.imageView.setBackgroundResource(contact.getImage());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);

        return new ContactsViewHolder(v);
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        AppCompatCheckBox checkBox;

        @Override
        public void onClick(View view) {
        }

        public ContactsViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.contact_name);
            imageView = (ImageView) v.findViewById(R.id.contact_profile);
            checkBox = (AppCompatCheckBox) v.findViewById(R.id.checkbox);
            checkBox.setVisibility(View.GONE);
            v.setOnClickListener(this);
        }
    }
}
