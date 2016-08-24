package com.dastanapps.dastanLib.networks;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * All netowrk response should be parese in this class
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class RestResponse {

    public static float parseTOS(String resp) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resp);
            int status = jsonObject.getInt("status");
            return (float) jsonObject.getDouble("tos_in_percent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static StatusB checkStatus(String resp) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resp);
            StatusB statusB = new StatusB();
            statusB.status = jsonObject.getInt("status");
            if (jsonObject.has("msg")) {
                statusB.msg = jsonObject.getString("msg");
            }
            return statusB;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
