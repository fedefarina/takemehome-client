package com.takemehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.takemehome.adapter.ContactsAdapter;
import com.takemehome.model.Contact;

/**
 * Created by ruitzei on 12/9/16.
 */

public class NewGroupFragment extends Fragment {

    private static final String TAG = NewGroupFragment.class.getSimpleName();
    private RecyclerView mRecycler;
    private ContactsAdapter contactsAdapter;

    public static NewGroupFragment newInstance() {
        Bundle args = new Bundle();

        NewGroupFragment fragment = new NewGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_fragment, null);

        Log.d(TAG, "Fragment created");

        mRecycler = (RecyclerView) view.findViewById(R.id.contacts_recycler);
        contactsAdapter = new ContactsAdapter(Contact.getMockedList());

        final LinearLayoutManager roomListLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(roomListLayoutManager);
        mRecycler.setAdapter(contactsAdapter);

        return view;
    }
}
