package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 이전회차들(모든회차) 당첨번호 표시. 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class PreviewActivity extends AppCompatActivity {

    String TAG = this.getClass().getName();
    int mPreviewComplete = 0;

    JsonObject jsonObject;
    RequestQueue requestQueue;
    String no1, no2, no3, no4, no5, no6, bonus, url;
    int drwNo;
    Context _ctx;

    boolean loop = true;    //에러가 나올 때까지 루프를 돌아서 당첨번호를 모은다

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        _ctx = this;
        init();
    }

    private void init()
    {
        url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=";

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        for(int i = 1; i < 5000; i++)
        {
            if ( loop ) {
                All_Preview_Number(i);
            } else {
                break;
            }
        }
    }

    private void All_Preview_Number(int d)
    {
        StringRequest request = new StringRequest(Request.Method.GET, url + String.valueOf(d), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                jsonObject = (JsonObject) JsonParser.parseString(response);

                if (response.contains("fail"))
                {
                    loop = false;
                    return;
                }
                if (jsonObject == null) {
                    loop = false;
                    return;
                }
                drwNo = d;
                no1 = "당첨번호 1 - " + jsonObject.get("drwtNo1");
                no2 = "당첨번호 2 - " + jsonObject.get("drwtNo2");
                no3 = "당첨번호 3 - " + jsonObject.get("drwtNo3");
                no4 = "당첨번호 4 - " + jsonObject.get("drwtNo4");
                no5 = "당첨번호 5 - " + jsonObject.get("drwtNo5");
                no6 = "당첨번호 6 - " + jsonObject.get("drwtNo6");
                bonus = "보너스 - " + jsonObject.get("bnusNo");
                Log.d("당첨번호", drwNo + "회차 당첨번호 : " + no1 + ", " + no2 + ", "
                        + no3 + ", " + no4 + ", " + no5 + ", " + no6 + ", " + bonus);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                loop = false;
                Log.d("회차검색실패 : ", error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        if(mPreviewComplete == 1)
        {
            intent.putExtra("result","Completed");
        } else {
            intent.putExtra("result","Cancelled");
        }
        setResult(101, intent);
        finish();
        return;
    }
}