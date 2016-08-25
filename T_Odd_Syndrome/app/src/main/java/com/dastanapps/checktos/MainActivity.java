package com.dastanapps.checktos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dastanapps.dastanLib.networks.IRestRequest;
import com.dastanapps.dastanLib.networks.RestAPI;
import com.dastanapps.dastanLib.networks.RestResponse;
import com.dastanapps.dastanLib.networks.StatusB;
import com.dastanapps.dastanLib.utils.FontUtils;
import com.dastanapps.dastanLib.utils.ViewUtils;
import com.dastanapps.db.bean.UserInfoB;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IRestRequest {


    @Bind(R.id.chk_age16)
    public CheckBox chk_age16;
    @Bind(R.id.chk_drugs)
    public CheckBox chk_drugs;
    @Bind(R.id.chk_migranies)
    public CheckBox chk_migranies;

    @Bind(R.id.rg_gender)
    public RadioGroup rg_gender;
    @Bind(R.id.rb_female)
    public RadioButton rb_female;
    @Bind(R.id.rb_male)
    public RadioButton rb_male;

    @Bind(R.id.btn_chk_tos)
    public Button btn_chk_tos;

    @Bind(R.id.tv_result)
    public TextView tv_result;

    @Bind(R.id.cv_result)
    public CardView cv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        FontUtils.setRobotoRegular(chk_age16, chk_drugs, chk_migranies, rb_female, rb_male, btn_chk_tos);
        FontUtils.setRobotoMedium(tv_result);

        btn_chk_tos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        JSONObject resultObj = new JSONObject();
        int rggenderId = rg_gender.getCheckedRadioButtonId();
        long timestamp = System.currentTimeMillis() / 1000;
        try {
            resultObj.put("timestamp", timestamp);
            if (rggenderId == R.id.rb_male) {
                //1 for male
                resultObj.put("gender", true);
            } else {
                // 0 for female
                resultObj.put("gender", false);
            }

            if (chk_age16.isChecked()) {
                resultObj.put("is_below_age_16", true);
            } else {
                resultObj.put("is_below_age_16", false);
            }

            if (chk_migranies.isChecked()) {
                resultObj.put("have_migraines", true);
            } else {
                resultObj.put("have_migraines", false);
            }

            if (chk_drugs.isChecked()) {
                resultObj.put("taking_drugs", true);
            } else {
                resultObj.put("taking_drugs", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserInfoB userCheckupInfob = new UserInfoB();
        userCheckupInfob.timestamp = timestamp;
        userCheckupInfob.checkup_info = resultObj.toString();

        float value = calculateTOS(resultObj.toString());
        setTOS(value);
        userCheckupInfob.percent = value;
        App.getDBInstance().insertUserCheckupInfo(userCheckupInfob);

//        HashMap<String, String> postParams = RestRequest.prepareTOS(resultObj.toString());
//        DastanRest.sentPostRequest(RestAPI.GET_TOS, postParams, RestAPI.ID_GET_TOS, this);
//        ViewUtils.showProgressDialog(MainActivity.this);
    }

    private float calculateTOS(String result) {
        try {
            JSONObject resultObject = new JSONObject(result);
            boolean age = resultObject.getBoolean("is_below_age_16");
            boolean migranies = resultObject.getBoolean("have_migraines");
            boolean drugs = resultObject.getBoolean("taking_drugs");
            boolean gender = resultObject.getBoolean("gender");

            boolean tosValue[] = {age, migranies, drugs, gender};
            int calcPrctage = 0;
            int part = (100 / tosValue.length);
            for (int i = 0; i < tosValue.length; i++) {
                if (tosValue[i]) {
                    calcPrctage += part;
                }
            }

            return calcPrctage;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    void setTOS(float value) {
        cv_result.setVisibility(View.VISIBLE);
        tv_result.setText(value + "% you have T' Odd Syndrome");
    }

    @Override
    public void onResponse(int reqID, String resp) {
        ViewUtils.hideProgressDialog();
        if (isFinishing()) return;
        if (reqID == RestAPI.ID_GET_TOS && !TextUtils.isEmpty(resp)) {
            StatusB statusB = RestResponse.checkStatus(resp);
            if (statusB != null && statusB.status == 1) {
                float tosInpercent = RestResponse.parseTOS(resp);
                setTOS(tosInpercent);
            } else {
                ViewUtils.showToast(MainActivity.this, statusB != null ? statusB.msg : "Invalid Request");
            }
        } else {
            ViewUtils.showToast(MainActivity.this, "Invalid Request");
        }
    }

    @Override
    public void onError(String error) {
        ViewUtils.hideProgressDialog();
        if (isFinishing()) return;

        ViewUtils.showToast(MainActivity.this, error);
    }
}
