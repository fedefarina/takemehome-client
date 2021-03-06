package com.takemehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.takemehome.model.Contact;
import com.takemehome.utils.TakeMeHomeConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by federicofarina on 12/20/16.
 */
public class FindFriendsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    Map<String, Contact> contactsMap = new HashMap<>();
    private GoogleMap map;
    private Toolbar toolbar;

    private LatLng member1Location = new LatLng(-34.613017f, -58.379802);
    private LatLng member2Location = new LatLng(-34.621537, -58.386615);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.find_friends));

        findViewById(R.id.center_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerMap(true);
            }
        });

        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);

        centerMap(false);

        TakeMeHomeApp app = (TakeMeHomeApp) getApplication();
        String firstName = "Agus";
        String secondName = "Aduriz";

        if (app.getContactFavs() != null && app.getContactFavs().size() >= 2) {
            Contact contact1 = app.getContactFavs().get(0);
            firstName = contact1.getName();
            Contact contact2 = app.getContactFavs().get(1);
            secondName = contact2.getName();
            contactsMap.put(firstName, contact1);
            contactsMap.put(secondName, contact2);
        }

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setBuildingsEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(true);

        LatLng fromLocation = new LatLng(TakeMeHomeConstants.FROM_LOCATION_LATITUDE
        , TakeMeHomeConstants.FROM_LOCATION_LONGITUDE);

        LatLng homeLocation = new LatLng(TakeMeHomeConstants.TO_LOCATION_LATITUDE
        , TakeMeHomeConstants.TO_LOCATION_LONGITUDE);

        map.addMarker(new MarkerOptions()
        .position(fromLocation)
        .title("Me")
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_person_pin_circle_black_48dp)));

        map.addMarker(new MarkerOptions()
        .position(member1Location)
        .title(firstName)
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_person_pin_black_24dp)));

        map.addMarker(new MarkerOptions()
        .position(member2Location)
        .title(secondName)
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_person_pin_black_24dp)));

        map.addMarker(new MarkerOptions()
        .position(homeLocation)
        .title("Casa")
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_home_black_48dp)));

    }

    private void centerMap(boolean animated) {

        LatLng fromLocation = new LatLng(TakeMeHomeConstants.FROM_LOCATION_LATITUDE
        , TakeMeHomeConstants.FROM_LOCATION_LONGITUDE);

        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
        new CameraPosition.Builder()
        .target(fromLocation)
        .zoom(12.0f)
        .bearing(0)
        .tilt(0)
        .build()
        );

        if (animated) {
            map.animateCamera(update, 500, null);
        } else {
            map.moveCamera(update);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Log.d("menu", "home");
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Contact contact = contactsMap.get(marker.getTitle());
        if (contact != null) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contact.getNumber()));
            startActivity(callIntent);
        }
        return false;
    }
}
