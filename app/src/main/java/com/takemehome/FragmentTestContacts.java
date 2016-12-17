package com.takemehome;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.takemehome.adapter.ContactsAdapter;
import com.takemehome.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruitzei on 12/17/16.
 */

public class FragmentTestContacts extends Fragment {

    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    private List<Contact> contacts = new ArrayList<>();


    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    // TODO: Implement a more advanced example that makes use of this
    private static final String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";

    String sortOrder       = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";


    // Defines a variable for the search string
    private String mSearchString = "@gmail.com";
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };

    private RecyclerView mContactListView;

    public static FragmentTestContacts newInstance() {
        
        Bundle args = new Bundle();

        FragmentTestContacts fragment = new FragmentTestContacts();
        fragment.setArguments(args);
        return fragment;
    }


    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View root = inflater.inflate(R.layout.contact_list_fragment, container, false);
        mContactListView = (RecyclerView) root.findViewById(R.id.contacts_recycler);
        mContactListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactListView.setItemAnimator(new DefaultItemAnimator());
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initializes a loader for loading the contacts
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                /*
                 * Makes search string into pattern and
                 * stores it in the selection array
                 */

                // Starts the query
                return new CursorLoader(
                        getActivity(),
                        uri,
                        PROJECTION,
                        null,
                        null,
                        sortOrder);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> objectLoader, Cursor c) {
                // Put the result Cursor in the adapter for the ListView
                int mNameColIdx = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int  mIdColIdx = c.getColumnIndex(ContactsContract.Contacts._ID);
                int mNumber = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                if (c.moveToNext()) {
                    do {
                        String contactName = c.getString(mNameColIdx);
                        long contactId = c.getLong(mIdColIdx);
                        String number = c.getString(mNumber);

                        contacts.add(new Contact(contactName, ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId), number));
                    } while (c.moveToNext());
                }

                Log.d("Size: ", Integer.toString(contacts.size()));

                mContactListView.setAdapter(new ContactsAdapter(contacts, listener));
            }

            @Override
            public void onLoaderReset(Loader<Cursor> cursorLoader) {
                // TODO do I need to do anything here?
            }
        });
    }

    private ContactsAdapter.ContactSelectedListener listener = new ContactsAdapter.ContactSelectedListener() {
        @Override
        public void onContactSelected(Contact contact) {
            Log.d("Fragment Test Contacts", "selected contact:" + contact.getName());
        }
    };

}
