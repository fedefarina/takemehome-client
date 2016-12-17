package com.takemehome.adapter;

import android.content.ContentUris;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.takemehome.R;
import com.takemehome.model.Contact;

/**
 * Created by ruitzei on 12/17/16.
 */

public class ContactsAdapterCursor extends RecyclerView.Adapter<ContactViewHolder> {

    private Cursor mCursor;
    private int mNameColIdx, mIdColIdx, mNumber;



    public ContactsAdapterCursor(Cursor cursor) {
        mCursor = cursor;
        mNameColIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        mIdColIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        mNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int pos) {

        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_item, parent, false);

        return new ContactViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int pos) {
        // Extract info from cursor
        mCursor.moveToPosition(pos);
        String contactName = mCursor.getString(mNameColIdx);
        long contactId = mCursor.getLong(mIdColIdx);
        String number = mCursor.getString(mNumber);

        // Create contact model and bind to viewholder
        Contact c = new Contact(contactName, ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId), number);

        contactViewHolder.bind(c);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
