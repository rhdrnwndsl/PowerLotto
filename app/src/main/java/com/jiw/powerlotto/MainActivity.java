package com.jiw.powerlotto;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
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
 * 스플레쉬 화면
 */
public class MainActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    LottieAnimationView animationView;

    int mPreviewComplete = 0;

    JsonObject jsonObject;
    RequestQueue requestQueue;
    String no1, no2, no3, no4, no5, no6, bonus, url;
    int drwNo;
    Context _ctx;

    PowerSDK powerSDK;

    boolean loop = true;    //에러가 나올 때까지 루프를 돌아서 당첨번호를 모은다

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.splash_animationView);
        _ctx = this;
        powerSDK = new PowerSDK(this); //최초로 한번 설정한다
//        animationView.setOnClickListener(v->{
//            Intent intent = new Intent(this, MenuActivity.class);
//            startActivity(intent);
//            finish();
//        });

        init();
    }


    private void init()
    {
        url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=";

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        int _tableCount = 1;

        try {
            String[] _db = powerSDK.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME);
            if (_db != null) {
                _tableCount = _db.length + 1;
            } else {
                _tableCount = 1;
            }

        } catch (SQLiteException ex) {
            Log.d(TAG,ex.toString());
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
            }, 3000);
        }

        for(int i = _tableCount; i < 5000; i++)
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

                    if (loop)
                    {
                        loop = false;
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Intent intent = new Intent(_ctx, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }, 3000);
                    }

                    return;
                }
                if (jsonObject == null) {
                    if (loop)
                    {
                        loop = false;
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Intent intent = new Intent(_ctx, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }, 3000);
                    }
                    return;
                }
                drwNo = d;
                no1 = "" + jsonObject.get("drwtNo1");
                no2 = "" + jsonObject.get("drwtNo2");
                no3 = "" + jsonObject.get("drwtNo3");
                no4 = "" + jsonObject.get("drwtNo4");
                no5 = "" + jsonObject.get("drwtNo5");
                no6 = "" + jsonObject.get("drwtNo6");
                bonus = "" + jsonObject.get("bnusNo");
                Log.d("당첨번호", drwNo + "회차 당첨번호 : " + no1 + ", " + no2 + ", "
                        + no3 + ", " + no4 + ", " + no5 + ", " + no6 + ", " + bonus);
                try {
                    powerSDK.InsertPreviewData(drwNo,
                            no1,
                            no2,
                            no3,
                            no4,
                            no5,
                            no6,
                            bonus);
                }
                catch (SQLiteException ex)
                {
                    Log.d(TAG,ex.toString());
                }

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

}