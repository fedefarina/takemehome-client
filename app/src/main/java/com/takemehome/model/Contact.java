package com.takemehome.model;

import android.net.Uri;

/**
 * Created by ruitzei on 12/9/16.
 */

public class Contact {

    private String name;
    private Uri image;
    private String number;
    private Boolean isChecked;

    public Contact(String name, Uri image, String number) {
        this.name = name;
        this.image = image;
        this.number = number;
        this.isChecked = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
