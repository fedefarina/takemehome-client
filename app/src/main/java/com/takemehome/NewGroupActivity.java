package com.takemehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by ruitzei on 12/9/16.
 */

public class NewGroupActivity extends AppCompatActivity {

    private static final String TAG = NewGroupActivity.class.getSimpleName();
    private Toolbar toolbar;

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

        FragmentTestContacts newGroupFragment = FragmentTestContacts.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.holder, newGroupFragment)
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
                break;
            } case android.R.id.home: {
                Log.d("menu", "home");
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
