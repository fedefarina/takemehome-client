package com.takemehome.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.takemehome.R;
import com.takemehome.model.Contact;

import java.util.List;

/**
 * Created by ruitzei on 12/17/16.
 */

public class ConfirmContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ContactsAdapter.class.getSimpleName();

    private Context context;
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


            Picasso.with(context)
                    .load(contact.getImage())
                    .placeholder(R.mipmap.ic_user)
                    .error(R.mipmap.ic_user)
                    .into(contactsHolder.imageView);

//            contactsHolder.imageView.setBackgroundResource(contact.getImage());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
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
