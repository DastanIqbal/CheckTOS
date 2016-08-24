package com.dastanapps.dastanLib.networks;

import com.dastanapps.dastanLib.DastanApp;
import com.dastanapps.dastanLib.utils.NetworkUtils;
import com.dastanapps.dastanLib.utils.ViewUtils;

import java.util.HashMap;

import static android.util.Log.i;

/**
 * This class is the wrapper of the Network lib
 * if in future if we change the network library so we just need to change this class
 * Just to avoid future consequences, using this class not library classes
 */
public class DastanRest {

    private static final String TAG = "DRest";

    public static synchronized void sentPostRequest(String postUrl, HashMap<String, String> postParams, int reqId, IRestRequest iRestRequest) {
        if (NetworkUtils.isConnectingToInternet(DastanApp.getInstance())) {
            VolleyRequest.getPostJsonObject(postUrl, postParams, reqId, iRestRequest);
        } else {
            i(TAG, "No internet connection");
            ViewUtils.showToast(DastanApp.getInstance(), "No internet Connection");
            iRestRequest.onError("No internet Connection");
        }
    }

    public static synchronized void sentGetRequestArray(String postUrl, int reqId, IRestRequest iRestRequest) {
        if (NetworkUtils.isConnectingToInternet(DastanApp.getInstance())) {
            VolleyRequest.getJsonArray(postUrl, reqId, iRestRequest);
        } else {
            i(TAG, "No internet connection");
            ViewUtils.showToast(DastanApp.getInstance(), "No internet Connection");
            iRestRequest.onError("No internet Connection");
        }
    }

    public static synchronized void sentGetRequestObj(String postUrl, int reqId, IRestRequest iRestRequest) {
        if (NetworkUtils.isConnectingToInternet(DastanApp.getInstance())) {
            VolleyRequest.getJsonObj(postUrl, reqId, iRestRequest);
        } else {
            i(TAG, "No internet connection");
            ViewUtils.showToast(DastanApp.getInstance(), "No internet Connection");
            iRestRequest.onError("No internet Connection");
        }
    }

    public static synchronized void deleteRequestObj(String postUrl, int reqId, IRestRequest iRestRequest) {
        if (NetworkUtils.isConnectingToInternet(DastanApp.getInstance())) {
            VolleyRequest.delete(postUrl, reqId, iRestRequest);
        } else {
            i(TAG, "No internet connection");
            ViewUtils.showToast(DastanApp.getInstance(), "No internet Connection");
            iRestRequest.onError("No internet Connection");
        }
    }

}
