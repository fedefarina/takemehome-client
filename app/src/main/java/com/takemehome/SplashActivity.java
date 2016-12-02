package com.takemehome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.takemehome.utils.LocationManager;
import com.takemehome.utils.Session;

/**
 * Created by federicofarina on 6/7/16.
 */
public class SplashActivity extends AppCompatActivity {

    public static final int START_DELAY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //Fetch last location
        LocationManager.getInstance(getApplicationContext()).fetchLastLocation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                //If user is logged in
                if (Session.getInstance(SplashActivity.this).isLoggedIn()) {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, START_DELAY);
    }
}
