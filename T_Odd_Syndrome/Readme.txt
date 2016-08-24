T'Odd Syndrome(TOS) Checkup App
================================

API URL------------>app/src/main/java/com.dastanapps.dastanlib.networks/RestAPI.java
DB Structure------->app/src/main/java/com.dastanapps.db/TOSDB.java

Run App Offline
--- Comment this code
//        HashMap<String, String> postParams = RestRequest.prepareTOS(resultObj.toString());
//        DastanRest.sentPostRequest(RestAPI.GET_TOS, postParams, RestAPI.ID_GET_TOS, this);
//        ViewUtils.showProgressDialog(MainActivity.this);

--- Uncomment this Code
        float value = calculateTOS(resultObj.toString());
        setTOS(value);
        userCheckupInfob.percent = value;
        App.getDBInstance().insertUserCheckupInfo(userCheckupInfob);

Vice vesa to check with API
--- Comment this Code
//        float value = calculateTOS(resultObj.toString());
//        setTOS(value);
//        userCheckupInfob.percent = value;
//        App.getDBInstance().insertUserCheckupInfo(userCheckupInfob);
--- Unomment this code
        HashMap<String, String> postParams = RestRequest.prepareTOS(resultObj.toString());
        DastanRest.sentPostRequest(RestAPI.GET_TOS, postParams, RestAPI.ID_GET_TOS, this);
        ViewUtils.showProgressDialog(MainActivity.this);
