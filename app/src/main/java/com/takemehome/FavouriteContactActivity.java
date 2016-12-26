package com.takemehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.takemehome.adapter.ConfirmContactsAdapter;
import com.takemehome.adapter.ContactsAdapter;
import com.takemehome.model.Contact;

import java.util.List;

/**
 * Created by ruitzei on 12/18/16.
 */

public class FavouriteContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TakeMeHomeApp app;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favourite);

        app = (TakeMeHomeApp)getApplication();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Favoritos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.favs_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.Adapter adapter = new ConfirmContactsAdapter(app.getContactFavs(), new ContactsAdapter.ContactSelectedListener() {
            @Override
            public void onContactSelected(Contact contact) {
                List<Contact> contacts = app.getContactFavs();

                // Making the contact come first on the list
                contacts.remove(contact);
                contacts.add(0, contact);
                Toast.makeText(getApplicationContext(), contact.getName() + " es tu nuevo contacto favorito", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onContactDeselected(Contact contact) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
