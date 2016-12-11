package com.takemehome.api;

public class TakeMeHomeApi {

    private static final String LOG = "TakeMeHomeApi";

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/users";

    private static final String APP_SERVER_IP = "172.17.0.3";
    private static final String APP_SERVER_PORT = "3000";

    private static String getAppServerURL() {
        return "http://" + APP_SERVER_IP + ":" + APP_SERVER_PORT;
    }

    public static String getLoginEndpoint() {
        return getAppServerURL() + LOGIN_ENDPOINT;
    }

    public static String getRegisterEndpoint() {
        return getAppServerURL() + REGISTER_ENDPOINT;
    }
}