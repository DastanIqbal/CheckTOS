package com.dastanapps.dastanLib;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dastanapps.checktos.App;
import com.dastanapps.dastanLib.networks.OkHttpStack;


/**
 * Main Context class for the dastan lib
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class DastanApp {
    public static final String TAG = App.class
            .getSimpleName();

    private static RequestQueue mRequestQueue;

    public static Context getInstance() {
        return App.getInstance();
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getInstance(), new OkHttpStack());
        }

        return mRequestQueue;
    }

    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public static <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public static void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
