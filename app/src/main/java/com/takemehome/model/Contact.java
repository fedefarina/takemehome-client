package com.takemehome.model;

import com.takemehome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruitzei on 12/9/16.
 */

public class Contact {

    private String name;
    private int image;

    public Contact(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static List<Contact> getMockedList() {
        List<Contact> list = new ArrayList<>();
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));
        list.add(new Contact("Kevin kev", R.mipmap.ic_launcher));

        return list;
    }
}
