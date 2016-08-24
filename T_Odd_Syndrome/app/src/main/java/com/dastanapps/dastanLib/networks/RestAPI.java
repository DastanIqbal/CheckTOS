package com.dastanapps.dastanLib.networks;

/**
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class RestAPI {
    private static String BASE_URL = "http://192.168.0.105/";
    private static String MAIL_PATH = "/api/";

    public static String GET_TOS = BASE_URL + MAIL_PATH + "getTOS";
    public static int ID_GET_TOS = 1001;
}
