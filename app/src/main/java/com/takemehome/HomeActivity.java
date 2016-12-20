package com.takemehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.takemehome.model.Contact;
import com.takemehome.model.Profile;
import com.takemehome.utils.Session;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;

import java.util.List;
import java.util.Locale;

/**
 * Created by ruitzei on 12/9/16.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    public static final double TO_LOCATION_LATITUDE = -34.587765;
    public static final double TO_LOCATION_LONGITUDE = -58.410256;
    public static final double FROM_LOCATION_LATITUDE = -34.617679;
    public static final double FROM_LOCATION_LONGITUDE = -58.368306;

    private View btn1;
    private View mapBtn;
    private View emergencyCallBtn;
    private View favoriteCallBtn;

    private Toolbar toolbar;
    private TextView groupName;
    private TextView createGroupText;
    private RideRequestButton uberBtn;
    private TakeMeHomeApp app;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        app = (TakeMeHomeApp) getApplication();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Take me home");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Profile profile = Session.getInstance(this).getProfile();

        TextView nameHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameProf);
        nameHeader.setText(profile.getName());

        TextView emailHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailProf);
        emailHeader.setText(profile.getEmail());


        mapBtn = findViewById(R.id.mapa);
        emergencyCallBtn = findViewById(R.id.emergency_call);
        favoriteCallBtn = findViewById(R.id.favorite_call);


        uberBtn = (RideRequestButton) findViewById(R.id.uber_btn);
        mapBtn.setOnClickListener(goToMapBtnListener());
        emergencyCallBtn.setOnClickListener(getEmergencyCallBtnListener());
        favoriteCallBtn.setOnClickListener(getFavoriteBtnListener());

        setupUber();
    }

    public View.OnClickListener goToMapBtnListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To Mocked location
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", TO_LOCATION_LATITUDE, TO_LOCATION_LONGITUDE, "Where the party is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        };
    }

    public View.OnClickListener getEmergencyCallBtnListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Bottom left");
            }
        };
    }

    public View.OnClickListener getFavoriteBtnListener() {
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_acc_group) {
            // If no group was created, we let the user create a new one.
            if (app.getContactFavs() == null) {
                goToCreateNewGroup();
            } else {
                //if group was created, we let the user pick his favourite contact.
                goToPickFavouriteContact();
            }
        } else if (id == R.id.nav_logout) {
            Session.getInstance(this).logout();
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            finish();
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callContact() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        final List<Contact> contactFavs = app.getContactFavs();
        if (contactFavs.size() > 0) {
            Contact contact = contactFavs.get(0);
            callIntent.setData(Uri.parse("tel:" + contact.getNumber()));
            startActivity(callIntent);
        } else {
            //todo show group creation activity?
        }
    }

    // Takes us to facultad de ingenieria and drops at alto palermo
    public void setupUber() {
        RideParameters rideParams = new RideParameters.Builder()
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setPickupLocation(FROM_LOCATION_LATITUDE, FROM_LOCATION_LONGITUDE, "Facultad de Ingenieria", ",Paseo Colon 850")
                .setDropoffLocation(TO_LOCATION_LATITUDE, TO_LOCATION_LONGITUDE, "Casa de Kevin", "Calle falsa 1234")
                .build();

        uberBtn.setRideParameters(rideParams);
    }
}
