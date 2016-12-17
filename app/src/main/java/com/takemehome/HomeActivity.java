package com.takemehome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

/**
 * Created by ruitzei on 12/9/16.
 */

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private View btn1;
    private View btn2;
    private View btn3;
    private View btn4;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Take me home");
        setSupportActionBar(toolbar);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);

        btn1.setOnClickListener(getBtn1Listener());
        btn2.setOnClickListener(getBtn2Listener());
        btn3.setOnClickListener(getBtn3Listener());
        btn4.setOnClickListener(getBtn4Listener());
    }

    public View.OnClickListener getBtn1Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateNewGroup();
                Log.d(TAG, "upper left");
            }
        };
    }

    public View.OnClickListener  getBtn2Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "upper right");
            }
        };
    }

    public View.OnClickListener  getBtn3Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Bottom left");
            }
        };
    }

    public View.OnClickListener  getBtn4Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Bottom right");
            }
        };
    }

    public void goToCreateNewGroup() {
        Intent intent = new Intent(this, NewGroupActivity.class);
        startActivity(intent);
    }
}
