package com.takemehome.api;

public class TakeMeHomeApi {

    private static final String LOG = "MatchAPI";

    private static final String LOGIN_ENDPOINT = "/api/accounts/login";
    private static final String REGISTER_ENDPOINT = "/api/accounts/signup";

    private static final String APP_SERVER_IP = "192.168.1.33";
    private static final String APP_SERVER_PORT = "8083";

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