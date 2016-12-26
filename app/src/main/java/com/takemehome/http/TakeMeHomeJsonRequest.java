package com.takemehome.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.takemehome.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

/**
 * Created by fedefarina on 28/04/16.
 */
public abstract class TakeMeHomeJsonRequest extends JsonObjectRequest {

    private int statusCode;
    WeakReference<Context> contextWr;

    public TakeMeHomeJsonRequest(Context context, String url) {
        this(context, Method.GET, url, null);
    }

    public TakeMeHomeJsonRequest(Context context, int method, String url) {
        this(context, method, url, null);
    }

    public TakeMeHomeJsonRequest(Context context, int method, String url, JSONObject jsonRequest) {
        super(method, url, jsonRequest, null, null);
        contextWr = new WeakReference<>(context);
        setShouldCache(false);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        statusCode = response.statusCode;
        return super.parseNetworkResponse(response);
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        try {
            if (statusCode == expectedCode()) {
                onSuccess(response);
            } else {
                showFirstError(response);
                onError(statusCode);
            }
        } catch (JSONException e) {
            Log.e("No valid JSON: ", e.toString());
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        if (response != null) {
            try {
                String body = new String(error.networkResponse.data, "UTF-8");
                JSONObject responseJSON = new JSONObject(body);

                if (expectedCode() == response.statusCode) {
                    JSONObject data = responseJSON.getJSONObject("data");
                    onSuccess(data);
                } else {
                    showFirstError(responseJSON);
                    onError(response.statusCode);
                }
            } catch (JSONException | UnsupportedEncodingException e) {
                Toast.makeText(getContext(), getContext().getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                onError(response.statusCode);
            }
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
            onNoConnection();
        }
    }


    private void showFirstError(JSONObject responseJSON) throws JSONException {
        String message = responseJSON.getString("error");
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public Context getContext() {
        return contextWr.get();
    }

    public void onNoConnection() {

    }

    public void onError(int statusCode) {

    }

    public void onSuccess(JSONObject data) {

    }

    public abstract int expectedCode();
}
