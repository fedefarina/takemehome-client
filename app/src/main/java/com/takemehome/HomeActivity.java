package com.takemehome;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.takemehome.model.Contact;
import com.takemehome.model.Profile;
import com.takemehome.utils.Session;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;

import java.util.List;
import java.util.Locale;

import static com.takemehome.utils.TakeMeHomeConstants.*;

/**
 * Created by ruitzei on 12/9/16.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private static final String SAME_NUMBER = "107";

    private View btn1;
    private View mapBtn;
    private View emergencyCallBtn;
    private View favoriteCallBtn;

    private Toolbar toolbar;
    private TextView groupNameTv;
    private TextView createGroupText;
    private RideRequestButton uberBtn;
    private TakeMeHomeApp app;

    private MenuItem createGroupMenu;


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

        ImageView imageHeader = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        imageHeader.setImageBitmap(profile.getProfilePhotoBitmap(this));

        mapBtn = findViewById(R.id.mapa);
        emergencyCallBtn = findViewById(R.id.emergency_call);
        favoriteCallBtn = findViewById(R.id.favorite_call);

        btn1 = findViewById(R.id.imageButton5);
        uberBtn = (RideRequestButton) findViewById(R.id.uber_btn);
        mapBtn.setOnClickListener(goToMapBtnListener());
        emergencyCallBtn.setOnClickListener(getEmergencyCallBtnListener());
        favoriteCallBtn.setOnClickListener(getFavoriteBtnListener());
        btn1.setOnClickListener(goToUber());

        // We want to change the text once a group is created.
        createGroupMenu = navigationView.getMenu().getItem(0);
        groupNameTv = (TextView) findViewById(R.id.current_group_text);

        setupUber();
    }

    public View.OnClickListener goToUber() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To Mocked location
                uberBtn.performClick();
            }
        };
    }

    public View.OnClickListener goToMapBtnListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH
                , "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f"
                , FROM_LOCATION_LATITUDE, FROM_LOCATION_LONGITUDE
                , TO_LOCATION_LATITUDE, TO_LOCATION_LONGITUDE);
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
                callSame();
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
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_group_cerated), Toast.LENGTH_SHORT).show();
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

        final String groupName = app.getGroupName();
        if (groupName != null) {
            createGroupMenu.setTitle("Elegir contacto favorito");

            final String labelText = "Grupo actual: ";
            this.groupNameTv.setText(labelText + groupName);

            final SpannableStringBuilder sb = new SpannableStringBuilder(labelText + groupName);

            final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
            final StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic
            sb.setSpan(bss, 0, labelText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
            sb.setSpan(iss, labelText.length(), labelText.length() + groupName.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make last 2 characters Italic
            this.groupNameTv.setText(sb);

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find_friends) {
            sendNotification();
            Intent i = new Intent(this, FindFriendsActivity.class);
            startActivity(i);
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
            Intent i = new Intent(this, LoginActivity.class);
            finish();
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callAbstract(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    public void callContact() {
        final List<Contact> contactFavs = app.getContactFavs();
        Contact contact = contactFavs.get(0);
        callAbstract(contact.getNumber());
    }

    public void callSame() {
        callAbstract(SAME_NUMBER);
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

    public void sendNotification() {
        // Create a WearableExtender to add functionality for wearables
        NotificationCompat.WearableExtender wearableExtender =
        new NotificationCompat.WearableExtender()
        .setHintHideIcon(true);

        // Create a NotificationCompat.Builder to build a standard notification
        // then extend it with the WearableExtender
        Notification notif = new NotificationCompat.Builder(getApplicationContext())
        .setContentTitle("Volviendo a casa")
        .setContentText("Yo te voy a cuidar...")
        .setSmallIcon(R.mipmap.takemehome)
        .extend(wearableExtender)
        .build();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
        NotificationManagerCompat.from(getApplicationContext());

        // Issue the notification with notification manager.
        notificationManager.notify(1101, notif);
    }
}
