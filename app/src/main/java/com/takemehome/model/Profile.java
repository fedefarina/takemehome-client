package com.takemehome.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.takemehome.R;
import com.takemehome.utils.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fedefarina on 20/06/16.
 */
public class Profile implements Serializable, Parcelable {

    private static final String NAME = "name";
    private static final String ALIAS = "alias";
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";
    private static final String AGE = "age";
    private static final String PHOTO_PROFILE = "photo_profile";

    private int age;
    private String name;
    private String alias;
    private String email;
    private String gender;
    private String photo_profile;

    public static final Creator<Profile> CREATOR
    = new Creator<Profile>() {
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Profile() {

    }

    public Profile(Parcel in) {
        String jsonString = in.readString();
        try {
            fromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            //Always a valid JSON here
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String jsonString = toJson().toString();
        dest.writeString(jsonString);
    }

    @Override
    public JSONObject toJson() {
        JSONObject profile = new JSONObject();
        try {
            profile.putOpt(NAME, name);
            profile.putOpt(AGE, age);
            profile.putOpt(EMAIL, email);
            profile.putOpt(GENDER, gender);
            profile.putOpt(PHOTO_PROFILE, photo_profile);
            profile.putOpt(ALIAS, alias);
        } catch (JSONException e) {
            //Will be OK
        }
        return profile;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        age = jsonObject.optInt(AGE);
        name = jsonObject.optString(NAME);
        alias = jsonObject.optString(ALIAS);
        email = jsonObject.optString(EMAIL);
        gender = jsonObject.optString(GENDER);
        photo_profile = jsonObject.optString(PHOTO_PROFILE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProfilePhoto() {
        return photo_profile;
    }

    public Bitmap getProfilePhotoBitmap(Context context) {
        Bitmap bitmap = null;
        try {
            byte[] decodedBytes = Base64.decode(photo_profile, 0);
            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            Log.e("Bad base 64", photo_profile);
        }

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
            R.drawable.manu);
        }

        return bitmap;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
