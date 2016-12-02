package com.takemehome.utils;

import org.json.JSONObject;

/**
 * Created by federicofarina on 6/17/16.
 */
public interface Serializable {
    JSONObject toJson();

    void fromJson(JSONObject jsonObject);
}
