package com.dastanapps.dastanLib.networks;

/**
 *
 * Interface to receiver network response and error
 * Created by Iqbal Ahmed on 10/13/2015.
 */
public interface IRestRequest {
    void onResponse(int reqID, String resp);
    void onError(String error);
}
