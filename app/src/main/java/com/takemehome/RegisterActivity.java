package com.takemehome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.Crop;
import com.takemehome.api.TakeMeHomeApi;
import com.takemehome.http.TakeMeHomeJsonRequest;
import com.takemehome.http.VolleyClient;
import com.takemehome.utils.LocationManager;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by ruitzei on 12/9/16.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String KEY_AGE = "age";
    private static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    private static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_PHOTO_PROFILE = "photo_profile";

    private static final char SEX_MALE = 'H';
    private static final char SEX_FEMALE = 'M';

    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";


    // UI references.
    private EditText ageEt;
    private EditText nameEt;
    private EditText usernameEt;
    private EditText passwordEt;
    private RadioButton sexFemaleRb;

    private TextView interestTv;
    private Toolbar myToolbar;

    private Integer[] selectedInterestIndices = new Integer[]{};

    //Profile photo
    private EditText emailEt;
    private Uri currentPhotoUri;
    private ImageView profileIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Set up the register form.
        usernameEt = (EditText) findViewById(R.id.username);
        passwordEt = (EditText) findViewById(R.id.password);
        sexFemaleRb = (RadioButton) findViewById(R.id.sex_female);
        ageEt = (EditText) findViewById(R.id.age);
        emailEt = (EditText) findViewById(R.id.emailEt);
        nameEt = (EditText) findViewById(R.id.nameEt);

        Button mUserRegisterButton = (Button) findViewById(R.id.user_register_button);
        //noinspection ConstantConditions
        mUserRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempRegister();
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        profileIv = (ImageView) findViewById(R.id.profile_image);
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMAGE_REQUEST_CODE);
            }
        });


    }

    private void pickImage(int requestCode) {
        Crop.pickImage(this, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                beginCrop(result.getData());
            } else {
                profileIv.setImageDrawable(null);
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                Glide.with(this)
                .load(Crop.getOutput(result))
                .into(profileIv);
            } else if (resultCode == Crop.RESULT_ERROR) {
                Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void attempRegister() {

        // Reset errors.
        usernameEt.setError(null);
        passwordEt.setError(null);
        ageEt.setError(null);

        // Store values at the time of the login attempt.
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        Character gender = sexFemaleRb.isChecked() ? SEX_FEMALE : SEX_MALE;
        String age = ageEt.getText().toString();
        String email = emailEt.getText().toString();
        String name = nameEt.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            usernameEt.setError(getString(R.string.error_field_required));
            focusView = usernameEt;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError(getString(R.string.error_field_required));
            focusView = passwordEt;
            cancel = true;
        }

        if (TextUtils.isEmpty(age)) {
            ageEt.setError(getString(R.string.error_field_required));
            focusView = ageEt;
            cancel = true;
        }

        String currentEncodedImage = null;

        if (currentPhotoUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), currentPhotoUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();
                currentEncodedImage = Base64.encodeToString(image, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //getActivityIndicator().show();
            userRegister(username, password, gender, age, email, currentEncodedImage, name);
        }
    }

    private void userRegister(String username, String password, Character mSex, String age, String email, String encodedImage, String name) {

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put(KEY_USERNAME, username);
            requestBody.put(KEY_PASSWORD, password);
            requestBody.put(KEY_GENDER, mSex);
            requestBody.put(KEY_AGE, Integer.valueOf(age));
            requestBody.put(KEY_EMAIL, email);
            requestBody.put(KEY_NAME, name);


            requestBody.putOpt(KEY_PHOTO_PROFILE, encodedImage);

            Location lastLocation = LocationManager.getInstance(getApplicationContext()).fetchLastLocation();
            if (lastLocation != null) {
                requestBody.put(KEY_LATITUDE, lastLocation.getLatitude() + "");
                requestBody.put(KEY_LONGITUDE, lastLocation.getLongitude());
            }
        } catch (JSONException e) {
            //Never will happen
        }

        final TakeMeHomeJsonRequest registerRequest = new TakeMeHomeJsonRequest(this, Request.Method.POST, TakeMeHomeApi.getRegisterEndpoint(), requestBody) {

            @Override
            public void onSuccess(JSONObject data) {
                String message = data.optString("message");
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public int expectedCode() {
                return HttpsURLConnection.HTTP_OK;
            }

            @Override
            public void onError(int statusCode) {
                //getActivityIndicator().hide();
            }

            @Override
            public void onNoConnection() {
                //getActivityIndicator().hide();
            }
        };


        VolleyClient.getInstance(getApplicationContext()).addToRequestQueue(registerRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void beginCrop(Uri source) {
        currentPhotoUri = Uri.fromFile(new File(getCacheDir(), "cropped" + System.currentTimeMillis()));
        Crop.of(source, currentPhotoUri).asSquare().start(this);
    }
}