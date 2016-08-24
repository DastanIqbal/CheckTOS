package com.dastanapps.db.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IQBAL-MEBELKART on 8/20/2016.
 */

public class UserInfoB implements Parcelable {
    public String checkup_info;
    public long timestamp;
    public float percent;
    public int id;

    public UserInfoB() {

    }

    protected UserInfoB(Parcel in) {
        checkup_info = in.readString();
        timestamp = in.readLong();
        percent = in.readFloat();
        id = in.readInt();
    }

    public static final Creator<UserInfoB> CREATOR = new Creator<UserInfoB>() {
        @Override
        public UserInfoB createFromParcel(Parcel in) {
            return new UserInfoB(in);
        }

        @Override
        public UserInfoB[] newArray(int size) {
            return new UserInfoB[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(checkup_info);
        parcel.writeLong(timestamp);
        parcel.writeFloat(percent);
        parcel.writeInt(id);
    }

    public UserInfoB(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            checkup_info = jsonObject.getString("checkup_info");
            timestamp = jsonObject.getLong("timestamp");
            percent = jsonObject.getLong("probability");
            id = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("checkup_info", checkup_info);
            jsonObject.put("timestamp", timestamp);
            jsonObject.put("probability", percent);
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
