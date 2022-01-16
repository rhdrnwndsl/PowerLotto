package com.jiw.powerlotto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * 이번주 당첨번호 표시. 하단 버튼 표시. 당신의 길일 체크(로또사는날). 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class MenuActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    Button mBtnSort, mBtnPre, mBtnQr, mBtnNum;
    PowerSDK mPowerSDK;
    ArrayList<ListModel> listModelArrayList;
    ListAdapter listAdapter;

    /** 배너광고 */
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    private void init()
    {
        // 초기화
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                
            }
        });

        mAdView = findViewById(R.id.main_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mPowerSDK = PowerSDK.getInstance();
        mBtnSort = findViewById(R.id.menu_btn_sort);
        mBtnPre = findViewById(R.id.menu_btn_preview);
        mBtnQr = findViewById(R.id.menu_btn_qr);
        mBtnNum = findViewById(R.id.menu_btn_number);

        mBtnPre.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 101);
        });
        mBtnQr.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), QR_ResultActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 102);
        });
        mBtnNum.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), NumberGeneratorActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 103);
        });

        mBtnSort.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), NumberSortingActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 104);
        });

        UpdateList();
    }


    private void ButtonEffect()
    {
        ObjectAnimator anim = ObjectAnimator.ofInt(mBtnNum, "textColor", Color.YELLOW, Color.RED, Color.YELLOW);
        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }

    private void UpdateList()
    {
        ButtonEffect();
        listModelArrayList = new ArrayList<>();
        listAdapter = new ListAdapter(this, R.layout.layout_list, listModelArrayList);
        ListView listView = findViewById(R.id.list_menu_view);

        String [] data = mPowerSDK.SelectData(Database.DB_QRCHECK_TABLENAME); /* sqlite 에서 데이터 가져오기 */

        String [] data1 = mPowerSDK.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */
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
                        getServerData(tmp[0]);
                    } else {
                        // 리스트에 해당 항목 추가
                        listModelArrayList.add(listModel);
                        listAdapter.notifyDataSetChanged();
                    }
                } else {
                    // 리스트에 해당 항목 추가
                    listModelArrayList.add(listModel);
                    listAdapter.notifyDataSetChanged();
                }



            }
        }

        listView.setAdapter(listAdapter);
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
                        listModelArrayList.add(listModel);
                        listAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(), "이전회차 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "이전회차 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }

        }

        else if (requestCode == 102) {
            if (data != null)
            {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(),"QR 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
                UpdateList();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "QR 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == 103) {
            if (data != null)
            {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(),"번호생성 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "번호생성 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == 104) {
            if (data != null)
            {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(),"번호통계 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "번호통계 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }
        }
    }
}