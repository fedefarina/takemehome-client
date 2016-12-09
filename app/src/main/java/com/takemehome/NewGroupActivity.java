package com.takemehome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by ruitzei on 12/9/16.
 */

public class NewGroupActivity extends AppCompatActivity {

    private static final String TAG = NewGroupActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "New group activity created");
        setContentView(R.layout.group_create_layout);

        NewGroupFragment newGroupFragment = NewGroupFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.holder, newGroupFragment)
                .commit();
    }
}
