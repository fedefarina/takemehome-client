package com.takemehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.takemehome.model.Contact;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;

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
    private TextView groupName;
    private TextView createGroupText;
    private RideRequestButton uberBtn;
    private TakeMeHomeApp app;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);
        app = (TakeMeHomeApp)getApplication();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Take me home");
        setSupportActionBar(toolbar);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);

        groupName = (TextView) findViewById(R.id.home_group_name);
        createGroupText = (TextView) findViewById(R.id.home_create_group);
        uberBtn = (RideRequestButton) findViewById(R.id.uber_btn);

        btn1.setOnClickListener(getBtn1Listener());
        btn2.setOnClickListener(getBtn2Listener());
        btn3.setOnClickListener(getBtn3Listener());
        btn4.setOnClickListener(getBtn4Listener());

        setupUber();
    }

    public View.OnClickListener getBtn1Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "upper left");

                // If no group was created, we let the user create a new one.
                if (app.getContactFavs() == null) {
                    goToCreateNewGroup();
                } else {
                    //if group was created, we let the user pick his favourite contact.
                    goToPickFavouriteContact();
                }
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

                if (app.getContactFavs() != null) {
                    callContact();
                }
            }
        };
    }

    public void goToCreateNewGroup() {
        Intent intent = new Intent(this, NewGroupActivity.class);
        startActivity(intent);
    }

    public void goToPickFavouriteContact() {
        Intent intent = new Intent(this, FavouriteContactActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String groupText = app.getGroupName() != null ? "Current group: " + app.getGroupName() : "No Group created";
        groupName.setText(groupText);

        String createText = app.getGroupName() != null ? "Pick fav" : "Create new group";
        createGroupText.setText(createText);
    }

    public void callContact() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        Contact contact = app.getContactFavs().get(0);
        callIntent.setData(Uri.parse("tel:"+contact.getNumber()));
        startActivity(callIntent);
    }

    // Takes us to facultad de ingenieria and drops at alto palermo
    public void setupUber() {
        RideParameters rideParams = new RideParameters.Builder()
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setPickupLocation(-34.617679, -58.368306, "Facultad de Ingenieria", ",Paseo Colon 850")
                .setDropoffLocation(-34.587765, -58.410256, "Casa de Kevin", "Calle falsa 1234")
                .build();

        uberBtn.setRideParameters(rideParams);
    }
}
