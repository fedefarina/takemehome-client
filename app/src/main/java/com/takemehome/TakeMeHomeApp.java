package com.takemehome;

import android.app.Application;

import com.takemehome.model.Contact;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ruitzei on 12/17/16.
 */

public class TakeMeHomeApp extends Application {

    private List<Contact> contactFavs;
    private String groupName;

    @Override
    public void onCreate() {
        super.onCreate();

        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId("fC2dWGVE8UT89Pe7Cn9AjLg1B4nq2vQP") //This is necessary
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.RIDE_WIDGETS)) //Your scopes for authentication here
                .build();

//This is a convenience method and will set the default config to be used in other components without passing it directly.
        UberSdk.initialize(config);
    }

    public List<Contact> getContactFavs() {
        return contactFavs;
    }

    public void setContactFavs(List<Contact> contactFavs) {
        this.contactFavs = contactFavs;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
