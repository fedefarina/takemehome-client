package com.takemehome.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.takemehome.R;
import com.takemehome.model.Contact;

import java.util.List;

/**
 * Created by ruitzei on 12/9/16.
 */

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ContactsAdapter.class.getSimpleName();

    private List<Contact> contacts;
    private ContactSelectedListener listener;

    public interface ContactSelectedListener {
        void onContactSelected(Contact contact);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public ContactsAdapter(List<Contact> contacts, ContactSelectedListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "Position" + position + "/" + getItemCount());

        if (holder instanceof ContactsViewHolder) {
            ContactsViewHolder contactsHolder = (ContactsViewHolder) holder;

            final Contact contact = contacts.get(position);

            contactsHolder.textView.setText(contact.getName());

            contactsHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    contact.setChecked(b);
                }
            });

            contactsHolder.checkBox.setChecked(contact.getChecked());
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
            listener.onContactSelected(contacts.get(getAdapterPosition()));
        }

        public ContactsViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.contact_name);
            imageView = (ImageView) v.findViewById(R.id.contact_profile);
            checkBox = (AppCompatCheckBox) v.findViewById(R.id.checkbox);
            v.setOnClickListener(this);
        }
    }
}
