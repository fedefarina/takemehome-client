package com.takemehome;

import android.app.Application;

import com.takemehome.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruitzei on 12/17/16.
 */

public class TakeMeHomeApp extends Application {

    private List<Contact> contactFavs = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public List<Contact> getContactFavs() {
        return contactFavs;
    }

    public void setContactFavs(List<Contact> contactFavs) {
        this.contactFavs = contactFavs;
    }
}
