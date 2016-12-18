package com.takemehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.takemehome.adapter.ContactsAdapter;
import com.takemehome.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruitzei on 12/9/16.
 */

public class NewGroupActivity extends AppCompatActivity implements ContactsAdapter.ContactSelectedListener {

    private static final String TAG = NewGroupActivity.class.getSimpleName();
    private Toolbar toolbar;
    // too lazy to remove indexes whenn contact is unchecked.
    private List<Contact> contactFavs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "New group activity created");
        setContentView(R.layout.group_create_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Group creation");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        PickContactsFragment newGroupFragment = PickContactsFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.holder, newGroupFragment)
                .commit();
    }

    public void goToPickNameFragment() {
        GroupNameFragment newGroupFragment = GroupNameFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.holder, newGroupFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_next: {
                Log.d("menu", "menu next");

                ((TakeMeHomeApp)getApplication()).setContactFavs(contactFavs);
                goToPickNameFragment();
                break;
            } case android.R.id.home: {
                Log.d("menu", "home");
                onBackPressed();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContactSelected(Contact contact) {
        Log.d("Fragment Test Contacts", "selected contact:" + contact.getName());
        contactFavs.add(contact);
    }

    @Override
    public void onContactDeselected(Contact contact) {
        contactFavs.remove(contact);
    }
}
