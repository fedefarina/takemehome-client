package com.takemehome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.takemehome.api.TakeMeHomeApi;
import com.takemehome.http.TakeMeHomeJsonRequest;
import com.takemehome.http.VolleyClient;
import com.takemehome.model.Profile;
import com.takemehome.utils.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText mUsernameView;
    private EditText mPasswordView;
    private TextView mRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupLoginForm();
    }

    private void setupLoginForm() {
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        //noinspection ConstantConditions
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mRegisterView = (TextView) findViewById(R.id.link_register);

        //noinspection ConstantConditions
        Spannable wordtoSpan = new SpannableString(mRegisterView.getText());
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRegisterView.setText(wordtoSpan);

        mRegisterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterPage();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            userLogin(username, password);
        }
    }

    private void goToRegisterPage() {
        Log.d(TAG, "Go to register");
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void userLogin(String mUsername, String mPassword) {

        JSONObject requestBody = buildLoginBody(mUsername, mPassword);
        if (requestBody != null) {
            //todo show loading

            final TakeMeHomeJsonRequest mathAppJsonRequest = new TakeMeHomeJsonRequest(this, Request.Method.POST, TakeMeHomeApi.getLoginEndpoint(), requestBody) {
                @Override
                public int expectedCode() {
                    return HttpsURLConnection.HTTP_OK;
                }

                @Override
                public void onSuccess(JSONObject data) {
                    try {
                        String token = data.getString("alias");
                        //Save session info
                        Session instance = Session.getInstance(LoginActivity.this);
                        instance.setToken(token);
                        Profile profile = new Profile();
                        profile.fromJson(data);
                        instance.setProfile(profile);

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        finish();
                        startActivity(i);
                    } catch (JSONException e) {
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onError(int statusCode) {
                    //todo hide
                }

                @Override
                public void onNoConnection() {
                    //todo hide
                }
            };


            VolleyClient.getInstance(LoginActivity.this).getRequestQueue().add(mathAppJsonRequest);
        }
    }

    private JSONObject buildLoginBody(String mUsername, String mPassword) {
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject();
            requestBody.putOpt(KEY_USERNAME, mUsername);
            requestBody.putOpt(KEY_PASSWORD, mPassword);
        } catch (JSONException e) {
            Toast.makeText(this, "Missing username or password", Toast.LENGTH_LONG).show();
        }
        return requestBody;
    }
}

