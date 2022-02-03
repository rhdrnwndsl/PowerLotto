package com.jiw.powerlotto;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    LoadingDialog loadingDialog;

    boolean loop = true;    //에러가 나올 때까지 루프를 돌아서 당첨번호를 모은다

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.splash_animationView);
        _ctx = this;
        powerSDK = new PowerSDK(this); //최초로 한번 설정한다
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

        openLoadingDialog();

        All_Preview_Number(_tableCount);

    }

    private void openLoadingDialog()
    {
        loadingDialog = new LoadingDialog(MainActivity.this, "초기화 중입니다...");
        loadingDialog.show();

    }

    private void DBUpdate()
    {
        String [] data = powerSDK.SelectData(Database.DB_QRCHECK_TABLENAME); /* sqlite 에서 데이터 가져오기 */

        String [] data1 = powerSDK.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */
        String[] tmp1 = new String[]{};
        if(data1!=null)
        {
            for(String n:data1)
            {
                tmp1 = n.split(",");
                break;
            }
        }

        if(data!=null)
        {
            for(String n:data)
            {
                String[] tmp = n.split("\\^");
                ListModel listModel = ListModel.build(tmp[0], tmp[1]);
                listModel.print();
                if(!listModel.isCheck().equals("1"))
                {
                    if(Integer.parseInt(listModel.getRound()) <= Integer.parseInt(tmp1[0]))
                    {
                        //여기서 다시 서버와 비교해서 업데이트 한다

                        try {
                            getServerData(tmp[0]);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(_ctx, MenuActivity.class);
            startActivity(intent);
            finish();
        }, 1000);
    }

    private void getServerData(String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String finalUrl = url;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document serverDoc = Jsoup.parse(response);
                        String result = serverDoc.getElementById("container").html();

                        // 스크랩핑 결과 데이터 로그로 표시
                        Log.e(TAG, "onResponse: " + result);

                        // 스크랩핑 된 데이터를 목적에 맞게 파싱 후 로그로 표시
                        ListModel listModel = ListModel.build(finalUrl, result);
                        listModel.print();

                        // 리스트에 해당 항목 추가
                        powerSDK.UpdateQRCheckData(finalUrl,result);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        queue.add(stringRequest);
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
                    loadingDialog.dismisDialog();
                    DBUpdate();

                    return;
                }
                if (jsonObject == null) {
                    loadingDialog.dismisDialog();
                    DBUpdate();

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

                if (drwNo == 100)
                {
                    loadingDialog.LoadingTextUpdate("서버에서 데이터를 불러옵니다...");
                }
                else if (drwNo == 400)
                {
                    loadingDialog.LoadingTextUpdate("불러온 데이터를 읽는 중입니다...");
                }
                else if (drwNo == 700)
                {
                    loadingDialog.LoadingTextUpdate("데이터 처리를 완료하였습니다...");
                }
                else if (drwNo == 900)
                {
                    loadingDialog.LoadingTextUpdate("잠시만 기다려 주십시오...");
                }

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

                All_Preview_Number(drwNo + 1);
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