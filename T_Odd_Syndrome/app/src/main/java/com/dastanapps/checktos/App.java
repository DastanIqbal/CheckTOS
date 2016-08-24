package com.dastanapps.checktos;

import android.app.Application;

import com.dastanapps.db.TOSDB;


public class App extends Application {

    public static final String TAG = App.class
            .getSimpleName();


    private static App mInstance;
    private static TOSDB dbInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        dbInstance=new TOSDB(this);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static synchronized TOSDB getDBInstance() {
        return dbInstance;
    }
}