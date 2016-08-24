package com.dastanapps.dastanLib.networks;

import java.util.HashMap;

/**
 * All network should be in this class
 * Created by IQBAL-MEBELKART on 8/16/2016.
 */

public class RestRequest {


    public static HashMap<String, String> prepareTOS(String resultJson) {
        HashMap<String, String> rdaparams = new HashMap<>();
        rdaparams.put("json", resultJson);
        return rdaparams;
    }
}
