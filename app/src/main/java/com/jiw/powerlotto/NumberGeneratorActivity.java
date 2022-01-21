package com.jiw.powerlotto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

/**
 * 번호 추첨화면(30초 광고도 표시) 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class NumberGeneratorActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    PowerSDK mPowerSDK;
    Context mCtx;

    TextView mTxt_num_congratu;
    Button mBtn_number_exit;

    /** sqlite에 저장된 최근무결성검사 10개를 리스트로 화면에 구성한다 */
    ListView listview ;
    /** 위 listview 에 연계되는 adapter 해당 리스트들의 각각의 데이터들의 위치 리스트추가 등의 기능을 수행한다다*/
    ListNumberGenerateAdapter adapter;

    Map<String, Integer> SwitchMap = new HashMap<>();

    private InterstitialAd mInterstitialAd;
    private String AD_UNIT_ID = "ca-app-pub-9992643111661420/6492371137";   //실제
//    private String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"; //테스트


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_generator);
        mCtx = this;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mPowerSDK = PowerSDK.getInstance();

        adapter = new ListNumberGenerateAdapter();
        listview = (ListView) findViewById(R.id.list_number_generate);
        listview.setAdapter(adapter);

        mTxt_num_congratu = findViewById(R.id.txt_num_congratu);

        mBtn_number_exit = findViewById(R.id.btn_number_exit);
        mBtn_number_exit.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.putExtra("result","Completed");
            setResult(103, intent);
            finish();
            return;
        });

        init();

//        loadAd();

    }

    private void init()
    {
        String [] data100 = mPowerSDK.SelectData(Database.DB_100COMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */
        String [] data10 = mPowerSDK.SelectData(Database.DB_10COMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */

        for (int i = 1; i < 46; i++)
        {
            PowerSDK.setIntPreference(this,"100_" + String.valueOf(i), 0);
            PowerSDK.setIntPreference(this,"10_" + String.valueOf(i), 0);
        }

        if(data100!=null)
        {
            for(String n:data100)
            {
                String[] tmp = n.split(",");
                mPowerSDK.InsertNumber100SortingData(tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6],tmp[7]);
            }
        }

        if(data10!=null)
        {
            for(String n:data10)
            {
                String[] tmp = n.split(",");
                mPowerSDK.InsertNumber10SortingData(tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6],tmp[7]);
            }
        }


        int max = 45;

        Map<String, Integer> map = new HashMap<>();
        map.put("1", mPowerSDK.getIntPreference(mCtx,"1") + mPowerSDK.getIntPreference(mCtx,"100_1") + mPowerSDK.getIntPreference(mCtx,"10_1"));
        map.put("2", mPowerSDK.getIntPreference(mCtx,"2") + mPowerSDK.getIntPreference(mCtx,"100_2") + mPowerSDK.getIntPreference(mCtx,"10_2"));
        map.put("3", mPowerSDK.getIntPreference(mCtx,"3") + mPowerSDK.getIntPreference(mCtx,"100_3") + mPowerSDK.getIntPreference(mCtx,"10_3"));
        map.put("4", mPowerSDK.getIntPreference(mCtx,"4") + mPowerSDK.getIntPreference(mCtx,"100_4") + mPowerSDK.getIntPreference(mCtx,"10_4"));
        map.put("5", mPowerSDK.getIntPreference(mCtx,"5") + mPowerSDK.getIntPreference(mCtx,"100_5") + mPowerSDK.getIntPreference(mCtx,"10_5"));
        map.put("6", mPowerSDK.getIntPreference(mCtx,"6") + mPowerSDK.getIntPreference(mCtx,"100_6") + mPowerSDK.getIntPreference(mCtx,"10_6"));
        map.put("7", mPowerSDK.getIntPreference(mCtx,"7") + mPowerSDK.getIntPreference(mCtx,"100_7") + mPowerSDK.getIntPreference(mCtx,"10_7"));
        map.put("8", mPowerSDK.getIntPreference(mCtx,"8") + mPowerSDK.getIntPreference(mCtx,"100_8") + mPowerSDK.getIntPreference(mCtx,"10_8"));
        map.put("9", mPowerSDK.getIntPreference(mCtx,"9") + mPowerSDK.getIntPreference(mCtx,"100_9") + mPowerSDK.getIntPreference(mCtx,"10_9"));
        map.put("10", mPowerSDK.getIntPreference(mCtx,"10")+mPowerSDK.getIntPreference(mCtx,"100_10")+ mPowerSDK.getIntPreference(mCtx,"10_10"));

        map.put("11", mPowerSDK.getIntPreference(mCtx,"11") + mPowerSDK.getIntPreference(mCtx,"100_11") + mPowerSDK.getIntPreference(mCtx,"10_11"));
        map.put("12", mPowerSDK.getIntPreference(mCtx,"12") + mPowerSDK.getIntPreference(mCtx,"100_12") + mPowerSDK.getIntPreference(mCtx,"10_12"));
        map.put("13", mPowerSDK.getIntPreference(mCtx,"13") + mPowerSDK.getIntPreference(mCtx,"100_13") + mPowerSDK.getIntPreference(mCtx,"10_13"));
        map.put("14", mPowerSDK.getIntPreference(mCtx,"14") + mPowerSDK.getIntPreference(mCtx,"100_14") + mPowerSDK.getIntPreference(mCtx,"10_14"));
        map.put("15", mPowerSDK.getIntPreference(mCtx,"15") + mPowerSDK.getIntPreference(mCtx,"100_15") + mPowerSDK.getIntPreference(mCtx,"10_15"));
        map.put("16", mPowerSDK.getIntPreference(mCtx,"16") + mPowerSDK.getIntPreference(mCtx,"100_16") + mPowerSDK.getIntPreference(mCtx,"10_16"));
        map.put("17", mPowerSDK.getIntPreference(mCtx,"17") + mPowerSDK.getIntPreference(mCtx,"100_17") + mPowerSDK.getIntPreference(mCtx,"10_17"));
        map.put("18", mPowerSDK.getIntPreference(mCtx,"18") + mPowerSDK.getIntPreference(mCtx,"100_18") + mPowerSDK.getIntPreference(mCtx,"10_18"));
        map.put("19", mPowerSDK.getIntPreference(mCtx,"19") + mPowerSDK.getIntPreference(mCtx,"100_19") + mPowerSDK.getIntPreference(mCtx,"10_19"));
        map.put("20", mPowerSDK.getIntPreference(mCtx,"20") + mPowerSDK.getIntPreference(mCtx,"100_20") + mPowerSDK.getIntPreference(mCtx,"10_20"));

        map.put("21", mPowerSDK.getIntPreference(mCtx,"21") + mPowerSDK.getIntPreference(mCtx,"100_21") + mPowerSDK.getIntPreference(mCtx,"10_21"));
        map.put("22", mPowerSDK.getIntPreference(mCtx,"22") + mPowerSDK.getIntPreference(mCtx,"100_22") + mPowerSDK.getIntPreference(mCtx,"10_22"));
        map.put("23", mPowerSDK.getIntPreference(mCtx,"23") + mPowerSDK.getIntPreference(mCtx,"100_23") + mPowerSDK.getIntPreference(mCtx,"10_23"));
        map.put("24", mPowerSDK.getIntPreference(mCtx,"24") + mPowerSDK.getIntPreference(mCtx,"100_24") + mPowerSDK.getIntPreference(mCtx,"10_24"));
        map.put("25", mPowerSDK.getIntPreference(mCtx,"25") + mPowerSDK.getIntPreference(mCtx,"100_25") + mPowerSDK.getIntPreference(mCtx,"10_25"));
        map.put("26", mPowerSDK.getIntPreference(mCtx,"26") + mPowerSDK.getIntPreference(mCtx,"100_26") + mPowerSDK.getIntPreference(mCtx,"10_26"));
        map.put("27", mPowerSDK.getIntPreference(mCtx,"27") + mPowerSDK.getIntPreference(mCtx,"100_27") + mPowerSDK.getIntPreference(mCtx,"10_27"));
        map.put("28", mPowerSDK.getIntPreference(mCtx,"28") + mPowerSDK.getIntPreference(mCtx,"100_28") + mPowerSDK.getIntPreference(mCtx,"10_28"));
        map.put("29", mPowerSDK.getIntPreference(mCtx,"29") + mPowerSDK.getIntPreference(mCtx,"100_29") + mPowerSDK.getIntPreference(mCtx,"10_29"));
        map.put("30", mPowerSDK.getIntPreference(mCtx,"30") + mPowerSDK.getIntPreference(mCtx,"100_30") + mPowerSDK.getIntPreference(mCtx,"10_30"));

        map.put("31", mPowerSDK.getIntPreference(mCtx,"31") + mPowerSDK.getIntPreference(mCtx,"100_31") + mPowerSDK.getIntPreference(mCtx,"10_31"));
        map.put("32", mPowerSDK.getIntPreference(mCtx,"32") + mPowerSDK.getIntPreference(mCtx,"100_32") + mPowerSDK.getIntPreference(mCtx,"10_32"));
        map.put("33", mPowerSDK.getIntPreference(mCtx,"33") + mPowerSDK.getIntPreference(mCtx,"100_33") + mPowerSDK.getIntPreference(mCtx,"10_33"));
        map.put("34", mPowerSDK.getIntPreference(mCtx,"34") + mPowerSDK.getIntPreference(mCtx,"100_34") + mPowerSDK.getIntPreference(mCtx,"10_34"));
        map.put("35", mPowerSDK.getIntPreference(mCtx,"35") + mPowerSDK.getIntPreference(mCtx,"100_35") + mPowerSDK.getIntPreference(mCtx,"10_35"));
        map.put("36", mPowerSDK.getIntPreference(mCtx,"36") + mPowerSDK.getIntPreference(mCtx,"100_36") + mPowerSDK.getIntPreference(mCtx,"10_36"));
        map.put("37", mPowerSDK.getIntPreference(mCtx,"37") + mPowerSDK.getIntPreference(mCtx,"100_37") + mPowerSDK.getIntPreference(mCtx,"10_37"));
        map.put("38", mPowerSDK.getIntPreference(mCtx,"38") + mPowerSDK.getIntPreference(mCtx,"100_38") + mPowerSDK.getIntPreference(mCtx,"10_38"));
        map.put("39", mPowerSDK.getIntPreference(mCtx,"39") + mPowerSDK.getIntPreference(mCtx,"100_39") + mPowerSDK.getIntPreference(mCtx,"10_39"));
        map.put("40", mPowerSDK.getIntPreference(mCtx,"40") + mPowerSDK.getIntPreference(mCtx,"100_40") + mPowerSDK.getIntPreference(mCtx,"10_40"));

        map.put("41", mPowerSDK.getIntPreference(mCtx,"41") + mPowerSDK.getIntPreference(mCtx,"100_41") + mPowerSDK.getIntPreference(mCtx,"10_41"));
        map.put("42", mPowerSDK.getIntPreference(mCtx,"42") + mPowerSDK.getIntPreference(mCtx,"100_42") + mPowerSDK.getIntPreference(mCtx,"10_42"));
        map.put("43", mPowerSDK.getIntPreference(mCtx,"43") + mPowerSDK.getIntPreference(mCtx,"100_43") + mPowerSDK.getIntPreference(mCtx,"10_43"));
        map.put("44", mPowerSDK.getIntPreference(mCtx,"44") + mPowerSDK.getIntPreference(mCtx,"100_44") + mPowerSDK.getIntPreference(mCtx,"10_44"));
        map.put("45", mPowerSDK.getIntPreference(mCtx,"45") + mPowerSDK.getIntPreference(mCtx,"100_45") + mPowerSDK.getIntPreference(mCtx,"10_45"));


        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        for(Map.Entry<String, Integer> entry : entryList){
            System.out.println("key : " + entry.getKey() + ", value : " + entry.getValue());
            max +=  entry.getValue();
        }

        int finalMax = max;
        adapter = new ListNumberGenerateAdapter();
        FindCombine();
        Map<String,Integer> _tmpMap = new HashMap<>();
        _tmpMap = RandomCountSplit();
        while(adapter.m_list_number_reader.size() < 1 )
        {
            NumberSet(finalMax,_tmpMap);
            try
            {
                Thread.sleep(200);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        NotBonusInit();

        Top20Init();

        listview.setAdapter(adapter);
        ButtonEffect();

    }

    private void NotBonusInit()
    {
        String [] data100 = mPowerSDK.SelectData(Database.DB_100COMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */
        String [] data10 = mPowerSDK.SelectData(Database.DB_10COMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */

        for (int i = 1; i < 46; i++)
        {
            PowerSDK.setIntPreference(this,"100_" + String.valueOf(i), 0);
            PowerSDK.setIntPreference(this,"10_" + String.valueOf(i), 0);
        }

        if(data100!=null)
        {
            for(String n:data100)
            {
                String[] tmp = n.split(",");
                mPowerSDK.InsertNumber100SortingDataNotBonus(tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6]);
            }
        }

        if(data10!=null)
        {
            for(String n:data10)
            {
                String[] tmp = n.split(",");
                mPowerSDK.InsertNumber10SortingDataNotBonus(tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6]);
            }
        }


        int max = 45;

        Map<String, Integer> map = new HashMap<>();
        map.put("1", mPowerSDK.getIntPreference(mCtx,"1") + mPowerSDK.getIntPreference(mCtx,"100_1") + mPowerSDK.getIntPreference(mCtx,"10_1"));
        map.put("2", mPowerSDK.getIntPreference(mCtx,"2") + mPowerSDK.getIntPreference(mCtx,"100_2") + mPowerSDK.getIntPreference(mCtx,"10_2"));
        map.put("3", mPowerSDK.getIntPreference(mCtx,"3") + mPowerSDK.getIntPreference(mCtx,"100_3") + mPowerSDK.getIntPreference(mCtx,"10_3"));
        map.put("4", mPowerSDK.getIntPreference(mCtx,"4") + mPowerSDK.getIntPreference(mCtx,"100_4") + mPowerSDK.getIntPreference(mCtx,"10_4"));
        map.put("5", mPowerSDK.getIntPreference(mCtx,"5") + mPowerSDK.getIntPreference(mCtx,"100_5") + mPowerSDK.getIntPreference(mCtx,"10_5"));
        map.put("6", mPowerSDK.getIntPreference(mCtx,"6") + mPowerSDK.getIntPreference(mCtx,"100_6") + mPowerSDK.getIntPreference(mCtx,"10_6"));
        map.put("7", mPowerSDK.getIntPreference(mCtx,"7") + mPowerSDK.getIntPreference(mCtx,"100_7") + mPowerSDK.getIntPreference(mCtx,"10_7"));
        map.put("8", mPowerSDK.getIntPreference(mCtx,"8") + mPowerSDK.getIntPreference(mCtx,"100_8") + mPowerSDK.getIntPreference(mCtx,"10_8"));
        map.put("9", mPowerSDK.getIntPreference(mCtx,"9") + mPowerSDK.getIntPreference(mCtx,"100_9") + mPowerSDK.getIntPreference(mCtx,"10_9"));
        map.put("10", mPowerSDK.getIntPreference(mCtx,"10")+mPowerSDK.getIntPreference(mCtx,"100_10")+ mPowerSDK.getIntPreference(mCtx,"10_10"));

        map.put("11", mPowerSDK.getIntPreference(mCtx,"11") + mPowerSDK.getIntPreference(mCtx,"100_11") + mPowerSDK.getIntPreference(mCtx,"10_11"));
        map.put("12", mPowerSDK.getIntPreference(mCtx,"12") + mPowerSDK.getIntPreference(mCtx,"100_12") + mPowerSDK.getIntPreference(mCtx,"10_12"));
        map.put("13", mPowerSDK.getIntPreference(mCtx,"13") + mPowerSDK.getIntPreference(mCtx,"100_13") + mPowerSDK.getIntPreference(mCtx,"10_13"));
        map.put("14", mPowerSDK.getIntPreference(mCtx,"14") + mPowerSDK.getIntPreference(mCtx,"100_14") + mPowerSDK.getIntPreference(mCtx,"10_14"));
        map.put("15", mPowerSDK.getIntPreference(mCtx,"15") + mPowerSDK.getIntPreference(mCtx,"100_15") + mPowerSDK.getIntPreference(mCtx,"10_15"));
        map.put("16", mPowerSDK.getIntPreference(mCtx,"16") + mPowerSDK.getIntPreference(mCtx,"100_16") + mPowerSDK.getIntPreference(mCtx,"10_16"));
        map.put("17", mPowerSDK.getIntPreference(mCtx,"17") + mPowerSDK.getIntPreference(mCtx,"100_17") + mPowerSDK.getIntPreference(mCtx,"10_17"));
        map.put("18", mPowerSDK.getIntPreference(mCtx,"18") + mPowerSDK.getIntPreference(mCtx,"100_18") + mPowerSDK.getIntPreference(mCtx,"10_18"));
        map.put("19", mPowerSDK.getIntPreference(mCtx,"19") + mPowerSDK.getIntPreference(mCtx,"100_19") + mPowerSDK.getIntPreference(mCtx,"10_19"));
        map.put("20", mPowerSDK.getIntPreference(mCtx,"20") + mPowerSDK.getIntPreference(mCtx,"100_20") + mPowerSDK.getIntPreference(mCtx,"10_20"));

        map.put("21", mPowerSDK.getIntPreference(mCtx,"21") + mPowerSDK.getIntPreference(mCtx,"100_21") + mPowerSDK.getIntPreference(mCtx,"10_21"));
        map.put("22", mPowerSDK.getIntPreference(mCtx,"22") + mPowerSDK.getIntPreference(mCtx,"100_22") + mPowerSDK.getIntPreference(mCtx,"10_22"));
        map.put("23", mPowerSDK.getIntPreference(mCtx,"23") + mPowerSDK.getIntPreference(mCtx,"100_23") + mPowerSDK.getIntPreference(mCtx,"10_23"));
        map.put("24", mPowerSDK.getIntPreference(mCtx,"24") + mPowerSDK.getIntPreference(mCtx,"100_24") + mPowerSDK.getIntPreference(mCtx,"10_24"));
        map.put("25", mPowerSDK.getIntPreference(mCtx,"25") + mPowerSDK.getIntPreference(mCtx,"100_25") + mPowerSDK.getIntPreference(mCtx,"10_25"));
        map.put("26", mPowerSDK.getIntPreference(mCtx,"26") + mPowerSDK.getIntPreference(mCtx,"100_26") + mPowerSDK.getIntPreference(mCtx,"10_26"));
        map.put("27", mPowerSDK.getIntPreference(mCtx,"27") + mPowerSDK.getIntPreference(mCtx,"100_27") + mPowerSDK.getIntPreference(mCtx,"10_27"));
        map.put("28", mPowerSDK.getIntPreference(mCtx,"28") + mPowerSDK.getIntPreference(mCtx,"100_28") + mPowerSDK.getIntPreference(mCtx,"10_28"));
        map.put("29", mPowerSDK.getIntPreference(mCtx,"29") + mPowerSDK.getIntPreference(mCtx,"100_29") + mPowerSDK.getIntPreference(mCtx,"10_29"));
        map.put("30", mPowerSDK.getIntPreference(mCtx,"30") + mPowerSDK.getIntPreference(mCtx,"100_30") + mPowerSDK.getIntPreference(mCtx,"10_30"));

        map.put("31", mPowerSDK.getIntPreference(mCtx,"31") + mPowerSDK.getIntPreference(mCtx,"100_31") + mPowerSDK.getIntPreference(mCtx,"10_31"));
        map.put("32", mPowerSDK.getIntPreference(mCtx,"32") + mPowerSDK.getIntPreference(mCtx,"100_32") + mPowerSDK.getIntPreference(mCtx,"10_32"));
        map.put("33", mPowerSDK.getIntPreference(mCtx,"33") + mPowerSDK.getIntPreference(mCtx,"100_33") + mPowerSDK.getIntPreference(mCtx,"10_33"));
        map.put("34", mPowerSDK.getIntPreference(mCtx,"34") + mPowerSDK.getIntPreference(mCtx,"100_34") + mPowerSDK.getIntPreference(mCtx,"10_34"));
        map.put("35", mPowerSDK.getIntPreference(mCtx,"35") + mPowerSDK.getIntPreference(mCtx,"100_35") + mPowerSDK.getIntPreference(mCtx,"10_35"));
        map.put("36", mPowerSDK.getIntPreference(mCtx,"36") + mPowerSDK.getIntPreference(mCtx,"100_36") + mPowerSDK.getIntPreference(mCtx,"10_36"));
        map.put("37", mPowerSDK.getIntPreference(mCtx,"37") + mPowerSDK.getIntPreference(mCtx,"100_37") + mPowerSDK.getIntPreference(mCtx,"10_37"));
        map.put("38", mPowerSDK.getIntPreference(mCtx,"38") + mPowerSDK.getIntPreference(mCtx,"100_38") + mPowerSDK.getIntPreference(mCtx,"10_38"));
        map.put("39", mPowerSDK.getIntPreference(mCtx,"39") + mPowerSDK.getIntPreference(mCtx,"100_39") + mPowerSDK.getIntPreference(mCtx,"10_39"));
        map.put("40", mPowerSDK.getIntPreference(mCtx,"40") + mPowerSDK.getIntPreference(mCtx,"100_40") + mPowerSDK.getIntPreference(mCtx,"10_40"));

        map.put("41", mPowerSDK.getIntPreference(mCtx,"41") + mPowerSDK.getIntPreference(mCtx,"100_41") + mPowerSDK.getIntPreference(mCtx,"10_41"));
        map.put("42", mPowerSDK.getIntPreference(mCtx,"42") + mPowerSDK.getIntPreference(mCtx,"100_42") + mPowerSDK.getIntPreference(mCtx,"10_42"));
        map.put("43", mPowerSDK.getIntPreference(mCtx,"43") + mPowerSDK.getIntPreference(mCtx,"100_43") + mPowerSDK.getIntPreference(mCtx,"10_43"));
        map.put("44", mPowerSDK.getIntPreference(mCtx,"44") + mPowerSDK.getIntPreference(mCtx,"100_44") + mPowerSDK.getIntPreference(mCtx,"10_44"));
        map.put("45", mPowerSDK.getIntPreference(mCtx,"45") + mPowerSDK.getIntPreference(mCtx,"100_45") + mPowerSDK.getIntPreference(mCtx,"10_45"));


        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        for(Map.Entry<String, Integer> entry : entryList){
            System.out.println("key : " + entry.getKey() + ", value : " + entry.getValue());
            max +=  entry.getValue();
        }

        int finalMax = max;
        FindCombineNotBonus();
        Map<String,Integer> _tmpMap = new HashMap<>();
        _tmpMap = RandomCountSplit();
        int _mpaCount = 1;
        while(adapter.m_list_number_reader.size() < 4 )
        {
            if(_mpaCount != adapter.m_list_number_reader.size())
            {
                _mpaCount--;
                _tmpMap = RandomCountSplit();
            }

            NumberSet(finalMax,_tmpMap);

            _tmpMap = RandomCountSplit();
            _mpaCount++;
            try
            {
                Thread.sleep(200);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }


    }

    private void Top20Init()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", mPowerSDK.getIntPreference(mCtx,"1"));map.put("11", mPowerSDK.getIntPreference(mCtx,"11"));map.put("21", mPowerSDK.getIntPreference(mCtx,"21"));
        map.put("2", mPowerSDK.getIntPreference(mCtx,"2"));map.put("12", mPowerSDK.getIntPreference(mCtx,"12"));map.put("22", mPowerSDK.getIntPreference(mCtx,"22"));
        map.put("3", mPowerSDK.getIntPreference(mCtx,"3"));map.put("13", mPowerSDK.getIntPreference(mCtx,"13"));map.put("23", mPowerSDK.getIntPreference(mCtx,"23"));
        map.put("4", mPowerSDK.getIntPreference(mCtx,"4"));map.put("14", mPowerSDK.getIntPreference(mCtx,"14"));map.put("24", mPowerSDK.getIntPreference(mCtx,"24"));
        map.put("5", mPowerSDK.getIntPreference(mCtx,"5"));map.put("15", mPowerSDK.getIntPreference(mCtx,"15"));map.put("25", mPowerSDK.getIntPreference(mCtx,"25"));
        map.put("6", mPowerSDK.getIntPreference(mCtx,"6"));map.put("16", mPowerSDK.getIntPreference(mCtx,"16"));map.put("26", mPowerSDK.getIntPreference(mCtx,"26"));
        map.put("7", mPowerSDK.getIntPreference(mCtx,"7"));map.put("17", mPowerSDK.getIntPreference(mCtx,"17"));map.put("27", mPowerSDK.getIntPreference(mCtx,"27"));
        map.put("8", mPowerSDK.getIntPreference(mCtx,"8"));map.put("18", mPowerSDK.getIntPreference(mCtx,"18"));map.put("28", mPowerSDK.getIntPreference(mCtx,"28"));
        map.put("9", mPowerSDK.getIntPreference(mCtx,"9"));map.put("19", mPowerSDK.getIntPreference(mCtx,"19"));map.put("29", mPowerSDK.getIntPreference(mCtx,"29"));
        map.put("10", mPowerSDK.getIntPreference(mCtx,"10")); map.put("20", mPowerSDK.getIntPreference(mCtx,"20")); map.put("30", mPowerSDK.getIntPreference(mCtx,"30"));

        map.put("31", mPowerSDK.getIntPreference(mCtx,"31"));map.put("41", mPowerSDK.getIntPreference(mCtx,"41"));
        map.put("32", mPowerSDK.getIntPreference(mCtx,"32"));map.put("42", mPowerSDK.getIntPreference(mCtx,"42"));
        map.put("33", mPowerSDK.getIntPreference(mCtx,"33"));map.put("43", mPowerSDK.getIntPreference(mCtx,"43"));
        map.put("34", mPowerSDK.getIntPreference(mCtx,"34"));map.put("44", mPowerSDK.getIntPreference(mCtx,"44"));
        map.put("35", mPowerSDK.getIntPreference(mCtx,"35"));map.put("45", mPowerSDK.getIntPreference(mCtx,"45"));
        map.put("36", mPowerSDK.getIntPreference(mCtx,"36"));
        map.put("37", mPowerSDK.getIntPreference(mCtx,"37"));
        map.put("38", mPowerSDK.getIntPreference(mCtx,"38"));
        map.put("39", mPowerSDK.getIntPreference(mCtx,"39"));
        map.put("40", mPowerSDK.getIntPreference(mCtx,"40"));


        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(map.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        int top20Count = 0;
        int _count = 0;
        for(Map.Entry<String, Integer> entry : entryList){
            if(_count == 20)
            {
                break;
            }
            System.out.println("top20key : " + entry.getKey() + ", top20value : " + entry.getValue());
            top20Count+=entry.getValue();
            _count++;
        }

        FindCombineNotBonus();

        while(adapter.m_list_number_reader.size() < 5 )
        {
            NumberSet20(top20Count, entryList);
            try
            {
                Thread.sleep(200);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }



    private void NumberSet(int _max,Map<String,Integer> _countMap)
    {
        Set<Integer> set = new HashSet<>();
        int i = 0;
        int tmp1=0,tmp10=0,tmp20=0,tmp30=0,tmp40=0;
        List<Integer> tmpi = new ArrayList<Integer>();
        Random _random = new Random();
        i = _random.nextInt(_max) + 1;
        i = SwitchNumber(i);
        if(i <= 10)
        {
            tmp1++;
        }
        else if(i >10 && i <= 20)
        {
            tmp10++;
        }
        else if(i >20 && i <= 30)
        {
            tmp20++;
        }
        else if(i >30 && i <= 40)
        {
            tmp30++;
        }
        else if(i >40)
        {
            tmp40++;
        }

        if(_countMap.get("1") < tmp1)
        {
            return;
        }
        else if(_countMap.get("10") < tmp10)
        {
            return;
        }
        else if(_countMap.get("20") < tmp20)
        {
            return;
        }
        else if(_countMap.get("30") < tmp30)
        {
            return;
        }
        else if(_countMap.get("40") < tmp40)
        {
            return;
        }
        tmpi.add(i);
        set.add(i);

        while (set.size() < 6) {
//            Random _random = new Random();
//            i = _random.nextInt(_max) + 1;
//            i = SwitchNumber(i);
//            set.add(i);
            int max = 0;
            Map<String, Integer> _tmpMap = new HashMap<>();
            for ( String _key:SwitchMap.keySet()) {
                if(_key.contains("." + String.valueOf(i) + ",") || _key.contains("," + String.valueOf(i) + "."))
                {
                    String _tmp = _key.replace("." + String.valueOf(i) + "," , "");
                    _tmp = _tmp.replace("," + String.valueOf(i) + "." , "");
                    _tmp = _tmp.replace("," + String.valueOf(i),"");
                    _tmp = _tmp.replace("." + String.valueOf(i),"");
                    _tmpMap.put(_tmp, SwitchMap.get(_key));

                }
            }

            List<Map.Entry<String, Integer>> entryList = new LinkedList<>(_tmpMap.entrySet());
            entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });
            for(Map.Entry<String, Integer> entry : entryList){
                System.out.println("tmpkey : " + entry.getKey() + ", tmpvalue : " + entry.getValue());
                max +=  entry.getValue();
            }

            _random = new Random();
            i = _random.nextInt(max) + 1;
            int _tmpCount = 0;
            for(Map.Entry<String, Integer> entry : entryList){
                System.out.println("setkey : " + entry.getKey() + ", setvalue : " + entry.getValue());
                _tmpCount +=  entry.getValue();
                if (i <= _tmpCount)
                {
                    String tmp = entry.getKey();
                    tmp = tmp.replace(",","");
                    tmp = tmp.replace(".","");
                    i = Integer.valueOf(tmp);
                    break;
                }
            }

            List<Integer> list = new ArrayList<>(set);
            int t1=0,t10=0,t20=0,t30=0,t40=0;
            for(int z= 0; z<list.size(); z++)
            {
                if(list.get(z) <= 10){t1++; }
                else if(list.get(z) >10 && list.get(z) <= 20){t10++; }
                else if(list.get(z) >20 && list.get(z) <= 30){t20++; }
                else if(list.get(z) >30 && list.get(z) <= 40){t30++; }
                else if(list.get(z) >40){t40++; }

            }


            if(i <= 10)
            {

                tmp1 = t1+1;
                if(_countMap.get("1") < tmp1)
                {
                    continue;
                }
            }
            else if(i >10 && i <= 20)
            {

                tmp10 = t10+1;
                if(_countMap.get("10") < tmp10)
                {
                    continue;
                }
            }
            else if(i >20 && i <= 30)
            {

                tmp20 = t20+1;
                if(_countMap.get("20") < tmp20)
                {
                    continue;
                }
            }
            else if(i >30 && i <= 40)
            {

                tmp30=t30+1;
                if(_countMap.get("30") < tmp30)
                {
                    continue;
                }
            }
            else if(i >40)
            {

                tmp40=t40+1;
                if(_countMap.get("40") < tmp40)
                {
                    continue;
                }
            }



            set.add(i);
        }

        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        System.out.println(list);
        int no1 = list.get(0);
        int no2 = list.get(1);
        int no3 = list.get(2);
        int no4 = list.get(3);
        int no5 = list.get(4);
        int no6 = list.get(5);

        tmp1=0;tmp10=0;tmp20=0;tmp30=0;tmp40=0;
        for(int j =0; j<6; j++)
        {
            if(list.get(j) <= 10)
            {
                tmp1++;
            }
            else if(list.get(j) >10 && list.get(j) <= 20)
            {
                tmp10++;
            }
            else if(list.get(j) >20 && list.get(j) <= 30)
            {
                tmp20++;
            }
            else if(list.get(j) >30 && list.get(j) <= 40)
            {
                tmp30++;
            }
            else if(list.get(j) >40)
            {
                tmp40++;
            }
        }
        if(_countMap.get("1") != tmp1)
        {
            return;
        }
        else if(_countMap.get("10") != tmp10)
        {
            return;
        }
        else if(_countMap.get("20") != tmp20)
        {
            return;
        }
        else if(_countMap.get("30") != tmp30)
        {
            return;
        }
        else if(_countMap.get("40") != tmp40)
        {
            return;
        }

        final String _msg = "번호 생성 : " + no1 + ", " + no2 + ", " + no3 + ", " + no4 + ", " + no5 + ", " + no6;
        Log.d(TAG, _msg);

        adapter.addItem(String.valueOf(no1),String.valueOf(no2),String.valueOf(no3),String.valueOf(no4),String.valueOf(no5),String.valueOf(no6));

    }

    private void NumberSet20(int _max, List<Map.Entry<String, Integer>> entryList)
    {
        Set<Integer> set = new HashSet<>();
        int i = 0;
        Random _random = new Random();
        i = _random.nextInt(_max) + 1;
        int _tmpCount = 0;
        for(Map.Entry<String, Integer> entry : entryList){
            System.out.println("setkey : " + entry.getKey() + ", setvalue : " + entry.getValue());
            _tmpCount +=  entry.getValue();
            if (i <= _tmpCount)
            {
                String tmp = entry.getKey();
                tmp = tmp.replace(",","");
                tmp = tmp.replace(".","");
                i = Integer.valueOf(tmp);
                break;
            }
        }

        set.add(i);

        while (set.size() < 6) {

            int max = 0;
            int _count20 = 0;
            Map<String, Integer> _tmpMap = new HashMap<>();
            for ( String _key:SwitchMap.keySet()) {
                if(_key.contains("." + String.valueOf(i) + ",") || _key.contains("," + String.valueOf(i) + "."))
                {
                    if (_count20 == 20)
                    {
                        break;
                    }
                    String _tmp = _key.replace("." + String.valueOf(i) + "," , "");
                    _tmp = _tmp.replace("," + String.valueOf(i) + "." , "");
                    _tmp = _tmp.replace("," + String.valueOf(i),"");
                    _tmp = _tmp.replace("." + String.valueOf(i),"");
                    _tmpMap.put(_tmp, SwitchMap.get(_key));
                    _count20++;

                }
            }

            List<Map.Entry<String, Integer>> _entryList = new LinkedList<>(_tmpMap.entrySet());
            _entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });
            for(Map.Entry<String, Integer> entry : _entryList){
                System.out.println("tmpkey : " + entry.getKey() + ", tmpvalue : " + entry.getValue());
                max +=  entry.getValue();
            }

            _random = new Random();
            i = _random.nextInt(max) + 1;
            int _tmptCount = 0;
            for(Map.Entry<String, Integer> entry : _entryList){
                System.out.println("setkey : " + entry.getKey() + ", setvalue : " + entry.getValue());
                _tmptCount +=  entry.getValue();
                if (i <= _tmptCount)
                {
                    String tmp = entry.getKey();
                    tmp = tmp.replace(",","");
                    tmp = tmp.replace(".","");
                    i = Integer.valueOf(tmp);
                    break;
                }
            }
            set.add(i);
        }

        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        System.out.println(list);
        int no1 = list.get(0);
        int no2 = list.get(1);
        int no3 = list.get(2);
        int no4 = list.get(3);
        int no5 = list.get(4);
        int no6 = list.get(5);

//        if (no5 <= 10)
//        {
//            Log.d(TAG, "10이하 5개");
//            return;
//        }
//
//        else if((no1 > 10 && no1 <= 20) && (no2 > 10 && no2 <= 20) && (no3 > 10 && no3 <= 20) && (no4 > 10 && no4 <= 20) && (no5 > 10 && no5 <= 20))
//        {
//            Log.d(TAG, "10초과 20이하 5개");
//            return;
//        }
//        else if((no2 > 10 && no2 <= 20) && (no3 > 10 && no3 <= 20) && (no4 > 10 && no4 <= 20) && (no5 > 10 && no5 <= 20) && (no6 > 10 && no6 <= 20))
//        {
//            Log.d(TAG, "10초과 20이하 5개");
//            return;
//        }
//
//        else if((no1 > 20 && no1 <= 30) && (no2 > 20 && no2 <= 30) && (no3 > 20 && no3 <= 30) && (no4 > 20 && no4 <= 30) && (no5 > 20 && no5 <= 30))
//        {
//            Log.d(TAG, "20초과 30이하 5개");
//            return;
//        }
//        else if((no2 > 20 && no2 <= 30) && (no3 > 20 && no3 <= 30) && (no4 > 20 && no4 <= 30) && (no5 > 20 && no5 <= 30) && (no6 > 20 && no6 <= 30))
//        {
//            Log.d(TAG, "20초과 30이하 5개");
//            return;
//        }
//
//        else if((no1 > 30 && no1 <= 40) && (no2 > 30 && no2 <= 40) && (no3 > 30 && no3 <= 40) && (no4 > 30 && no4 <= 40) && (no5 > 30 && no5 <= 40))
//        {
//            Log.d(TAG, "30초과 40이하 5개");
//            return;
//        }
//        else if((no2 > 30 && no2 <= 40) && (no3 > 30 && no3 <= 40) && (no4 > 30 && no4 <= 40) && (no5 > 30 && no5 <= 40) && (no6 > 30 && no6 <= 40))
//        {
//            Log.d(TAG, "30초과 40이하 5개");
//            return;
//        }
//
//        else if((no3 > 40) && (no4 > 40) && (no5 > 40) && (no6 > 40))
//        {
//            Log.d(TAG, "40초과 4개");
//            return;
//        }

        final String _msg = "번호 생성 : " + no1 + ", " + no2 + ", " + no3 + ", " + no4 + ", " + no5 + ", " + no6;
        Log.d(TAG, _msg);

        adapter.addItem(String.valueOf(no1),String.valueOf(no2),String.valueOf(no3),String.valueOf(no4),String.valueOf(no5),String.valueOf(no6));

    }

    private int SwitchNumber(int n)
    {
        int n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19,n20,n21,n22,n23,n24,n25,
                n26,n27,n28,n29,n30,n31,n32,n33,n34,n35,n36,n37,n38,n39,n40,n41,n42,n43,n44,n45;
        n1 = mPowerSDK.getIntPreference(mCtx,"1") + mPowerSDK.getIntPreference(mCtx,"10_1") + mPowerSDK.getIntPreference(mCtx,"100_1");
        n2 = mPowerSDK.getIntPreference(mCtx,"2") + mPowerSDK.getIntPreference(mCtx,"10_2") + mPowerSDK.getIntPreference(mCtx,"100_2");
        n3 = mPowerSDK.getIntPreference(mCtx,"3") + mPowerSDK.getIntPreference(mCtx,"10_3") + mPowerSDK.getIntPreference(mCtx,"100_3");
        n4 = mPowerSDK.getIntPreference(mCtx,"4") + mPowerSDK.getIntPreference(mCtx,"10_4") + mPowerSDK.getIntPreference(mCtx,"100_4");
        n5 = mPowerSDK.getIntPreference(mCtx,"5") + mPowerSDK.getIntPreference(mCtx,"10_5") + mPowerSDK.getIntPreference(mCtx,"100_5");
        n6 = mPowerSDK.getIntPreference(mCtx,"6") + mPowerSDK.getIntPreference(mCtx,"10_6") + mPowerSDK.getIntPreference(mCtx,"100_6");
        n7 = mPowerSDK.getIntPreference(mCtx,"7") + mPowerSDK.getIntPreference(mCtx,"10_7") + mPowerSDK.getIntPreference(mCtx,"100_7");
        n8 = mPowerSDK.getIntPreference(mCtx,"8") + mPowerSDK.getIntPreference(mCtx,"10_8") + mPowerSDK.getIntPreference(mCtx,"100_8");
        n9 = mPowerSDK.getIntPreference(mCtx,"9") + mPowerSDK.getIntPreference(mCtx,"10_9") + mPowerSDK.getIntPreference(mCtx,"100_9");
        n10 = mPowerSDK.getIntPreference(mCtx,"10") + mPowerSDK.getIntPreference(mCtx,"10_10") + mPowerSDK.getIntPreference(mCtx,"100_10");

        n11 = mPowerSDK.getIntPreference(mCtx,"11") + mPowerSDK.getIntPreference(mCtx,"10_11") + mPowerSDK.getIntPreference(mCtx,"100_11");
        n12 = mPowerSDK.getIntPreference(mCtx,"12") + mPowerSDK.getIntPreference(mCtx,"10_12") + mPowerSDK.getIntPreference(mCtx,"100_12");
        n13 = mPowerSDK.getIntPreference(mCtx,"13") + mPowerSDK.getIntPreference(mCtx,"10_13") + mPowerSDK.getIntPreference(mCtx,"100_13");
        n14 = mPowerSDK.getIntPreference(mCtx,"14") + mPowerSDK.getIntPreference(mCtx,"10_14") + mPowerSDK.getIntPreference(mCtx,"100_14");
        n15 = mPowerSDK.getIntPreference(mCtx,"15") + mPowerSDK.getIntPreference(mCtx,"10_15") + mPowerSDK.getIntPreference(mCtx,"100_15");
        n16 = mPowerSDK.getIntPreference(mCtx,"16") + mPowerSDK.getIntPreference(mCtx,"10_16") + mPowerSDK.getIntPreference(mCtx,"100_16");
        n17 = mPowerSDK.getIntPreference(mCtx,"17") + mPowerSDK.getIntPreference(mCtx,"10_17") + mPowerSDK.getIntPreference(mCtx,"100_17");
        n18 = mPowerSDK.getIntPreference(mCtx,"18") + mPowerSDK.getIntPreference(mCtx,"10_18") + mPowerSDK.getIntPreference(mCtx,"100_18");
        n19 = mPowerSDK.getIntPreference(mCtx,"19") + mPowerSDK.getIntPreference(mCtx,"10_19") + mPowerSDK.getIntPreference(mCtx,"100_19");
        n20 = mPowerSDK.getIntPreference(mCtx,"20") + mPowerSDK.getIntPreference(mCtx,"10_20") + mPowerSDK.getIntPreference(mCtx,"100_20");

        n21 = mPowerSDK.getIntPreference(mCtx,"21") + mPowerSDK.getIntPreference(mCtx,"10_21") + mPowerSDK.getIntPreference(mCtx,"100_21");
        n22 = mPowerSDK.getIntPreference(mCtx,"22") + mPowerSDK.getIntPreference(mCtx,"10_22") + mPowerSDK.getIntPreference(mCtx,"100_22");
        n23 = mPowerSDK.getIntPreference(mCtx,"23") + mPowerSDK.getIntPreference(mCtx,"10_23") + mPowerSDK.getIntPreference(mCtx,"100_23");
        n24 = mPowerSDK.getIntPreference(mCtx,"24") + mPowerSDK.getIntPreference(mCtx,"10_24") + mPowerSDK.getIntPreference(mCtx,"100_24");
        n25 = mPowerSDK.getIntPreference(mCtx,"25") + mPowerSDK.getIntPreference(mCtx,"10_25") + mPowerSDK.getIntPreference(mCtx,"100_25");
        n26 = mPowerSDK.getIntPreference(mCtx,"26") + mPowerSDK.getIntPreference(mCtx,"10_26") + mPowerSDK.getIntPreference(mCtx,"100_26");
        n27 = mPowerSDK.getIntPreference(mCtx,"27") + mPowerSDK.getIntPreference(mCtx,"10_27") + mPowerSDK.getIntPreference(mCtx,"100_27");
        n28 = mPowerSDK.getIntPreference(mCtx,"28") + mPowerSDK.getIntPreference(mCtx,"10_28") + mPowerSDK.getIntPreference(mCtx,"100_28");
        n29 = mPowerSDK.getIntPreference(mCtx,"29") + mPowerSDK.getIntPreference(mCtx,"10_29") + mPowerSDK.getIntPreference(mCtx,"100_29");
        n30 = mPowerSDK.getIntPreference(mCtx,"30") + mPowerSDK.getIntPreference(mCtx,"10_30") + mPowerSDK.getIntPreference(mCtx,"100_30");

        n31 = mPowerSDK.getIntPreference(mCtx,"31") + mPowerSDK.getIntPreference(mCtx,"10_31") + mPowerSDK.getIntPreference(mCtx,"100_31");
        n32 = mPowerSDK.getIntPreference(mCtx,"32") + mPowerSDK.getIntPreference(mCtx,"10_32") + mPowerSDK.getIntPreference(mCtx,"100_32");
        n33 = mPowerSDK.getIntPreference(mCtx,"33") + mPowerSDK.getIntPreference(mCtx,"10_33") + mPowerSDK.getIntPreference(mCtx,"100_33");
        n34 = mPowerSDK.getIntPreference(mCtx,"34") + mPowerSDK.getIntPreference(mCtx,"10_34") + mPowerSDK.getIntPreference(mCtx,"100_34");
        n35 = mPowerSDK.getIntPreference(mCtx,"35") + mPowerSDK.getIntPreference(mCtx,"10_35") + mPowerSDK.getIntPreference(mCtx,"100_35");
        n36 = mPowerSDK.getIntPreference(mCtx,"36") + mPowerSDK.getIntPreference(mCtx,"10_36") + mPowerSDK.getIntPreference(mCtx,"100_36");
        n37 = mPowerSDK.getIntPreference(mCtx,"37") + mPowerSDK.getIntPreference(mCtx,"10_37") + mPowerSDK.getIntPreference(mCtx,"100_37");
        n38 = mPowerSDK.getIntPreference(mCtx,"38") + mPowerSDK.getIntPreference(mCtx,"10_38") + mPowerSDK.getIntPreference(mCtx,"100_38");
        n39 = mPowerSDK.getIntPreference(mCtx,"39") + mPowerSDK.getIntPreference(mCtx,"10_39") + mPowerSDK.getIntPreference(mCtx,"100_39");
        n40 = mPowerSDK.getIntPreference(mCtx,"40") + mPowerSDK.getIntPreference(mCtx,"10_40") + mPowerSDK.getIntPreference(mCtx,"100_40");

        n41 = mPowerSDK.getIntPreference(mCtx,"41") + mPowerSDK.getIntPreference(mCtx,"10_41") + mPowerSDK.getIntPreference(mCtx,"100_41");
        n42 = mPowerSDK.getIntPreference(mCtx,"42") + mPowerSDK.getIntPreference(mCtx,"10_42") + mPowerSDK.getIntPreference(mCtx,"100_42");
        n43 = mPowerSDK.getIntPreference(mCtx,"43") + mPowerSDK.getIntPreference(mCtx,"10_43") + mPowerSDK.getIntPreference(mCtx,"100_43");
        n44 = mPowerSDK.getIntPreference(mCtx,"44") + mPowerSDK.getIntPreference(mCtx,"10_44") + mPowerSDK.getIntPreference(mCtx,"100_44");
        n45 = mPowerSDK.getIntPreference(mCtx,"45") + mPowerSDK.getIntPreference(mCtx,"10_45") + mPowerSDK.getIntPreference(mCtx,"100_45");

        if(n <= 45)
        {
            return n;
        }
        else if (n > 45 && n <= 45+n1 )
        {
            return 1;
        }
        else if (n > 45+n1 && n <= 45+n1+n2 )
        {
            return 2;
        }
        else if (n > 45+n1+n2 && n <= 45+n1+n2+n3 )
        {
            return 3;
        }
        else if (n > 45+n1+n2+n3 && n <= 45+n1+n2+n3+n4 )
        {
            return 4;
        }
        else if (n > 45+n1+n2+n3+n4 && n <= 45+n1+n2+n3+n4+n5 )
        {
            return 5;
        }
        else if (n > 45+n1+n2+n3+n4+n5 && n <= 45+n1+n2+n3+n4+n5+n6 )
        {
            return 6;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6 && n <= 45+n1+n2+n3+n4+n5+n6+n7)
        {
            return 7;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8 )
        {
            return 8;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9 )
        {
            return 9;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10 )
        {
            return 10;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11 )
        {
            return 11;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12 )
        {
            return 12;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13 )
        {
            return 13;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14 )
        {
            return 14;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15 )
        {
            return 15;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16 )
        {
            return 16;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16 && n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17 )
        {
            return 17;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18 )
        {
            return 18;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19 )
        {
            return 19;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20 )
        {
            return 20;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21 )
        {
            return 21;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22 )
        {
            return 22;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23 )
        {
            return 23;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24 )
        {
            return 24;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25 )
        {
            return 25;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26 )
        {
            return 26;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27  )
        {
            return 27;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28 )
        {
            return 28;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29 )
        {
            return 29;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30 )
        {
            return 30;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31  )
        {
            return 31;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32 )
        {
            return 32;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33 )
        {
            return 33;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34)
        {
            return 34;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35 )
        {
            return 35;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36 )
        {
            return 36;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37 )
        {
            return 37;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38 )
        {
            return 38;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39)
        {
            return 39;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40 )
        {
            return 40;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41 )
        {
            return 41;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42 )
        {
            return 42;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42+n43 )
        {
            return 43;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42+n43 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42+n43+n44 )
        {
            return 44;
        }
        else if (n > 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42+n43+n44 &&
                n <= 45+n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24+n25+n26+n27+n28+n29+n30+n31+n32+n33+n34+n35+n36+n37+n38+n39+n40+n41+n42+n43+n44+n45 )
        {
            return 45;
        }

        return n;
    }

    /**
     * DB에서 로또 회차정보를 가져와서 가장 많이 나온 조합을 찾는다.
     */
    private void FindCombine()
    {

        for (int i = 1; i < 45; i ++)
        {
            for (int j = i+1; j < 46; j++)
            {
                PowerSDK.setIntPreference(mCtx, i + "," + j, 0);
            }
        }

        SwitchMap.clear();

        String [] data = mPowerSDK.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */
        if(data!=null)
        {
            for(String n:data)
            {
                String[] tmp = n.split(",");
                List<Integer> list = new ArrayList<>();
                list.add(Integer.parseInt(tmp[1]));list.add(Integer.parseInt(tmp[2]));list.add(Integer.parseInt(tmp[3]));
                list.add(Integer.parseInt(tmp[4]));list.add(Integer.parseInt(tmp[5]));list.add(Integer.parseInt(tmp[6]));
                list.add(Integer.parseInt(tmp[7]));
                Collections.sort(list);
                int no1 = list.get(0);
                int no2 = list.get(1);
                int no3 = list.get(2);
                int no4 = list.get(3);
                int no5 = list.get(4);
                int no6 = list.get(5);
                int no7 = list.get(6);
                SwitchCombine(no1,no2,no3,no4,no5,no6,no7);

            }

            List<Map.Entry<String, Integer>> entryList = new LinkedList<>(SwitchMap.entrySet());
            entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });

            for(Map.Entry<String, Integer> entry : entryList){
                Log.d(TAG,"key : " + entry.getKey() + ", value : " + entry.getValue());
            }
        }
    }

    /**
     * DB에서 로또 회차정보를 가져와서 가장 많이 나온 조합을 찾는다.
     */
    private void FindCombineNotBonus()
    {

        for (int i = 1; i < 45; i ++)
        {
            for (int j = i+1; j < 46; j++)
            {
                PowerSDK.setIntPreference(mCtx, i + "," + j, 0);
            }
        }

        SwitchMap.clear();

        String [] data = mPowerSDK.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */
        if(data!=null)
        {
            for(String n:data)
            {
                String[] tmp = n.split(",");
                List<Integer> list = new ArrayList<>();
                list.add(Integer.parseInt(tmp[1]));list.add(Integer.parseInt(tmp[2]));list.add(Integer.parseInt(tmp[3]));
                list.add(Integer.parseInt(tmp[4]));list.add(Integer.parseInt(tmp[5]));list.add(Integer.parseInt(tmp[6]));
                Collections.sort(list);
                int no1 = list.get(0);
                int no2 = list.get(1);
                int no3 = list.get(2);
                int no4 = list.get(3);
                int no5 = list.get(4);
                int no6 = list.get(5);
                SwitchCombineNotBonus(no1,no2,no3,no4,no5,no6);

            }

            List<Map.Entry<String, Integer>> entryList = new LinkedList<>(SwitchMap.entrySet());
            entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });

            for(Map.Entry<String, Integer> entry : entryList){
                Log.d(TAG,"key : " + entry.getKey() + ", value : " + entry.getValue());
            }
        }
    }


    private void SwitchCombine(int val1, int val2, int val3, int val4, int val5, int val6, int valBonus)
    {
        int _put = 0;

        _put = SwitchMap.get("." + val1 + "," + val2 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val2 + ".");
        SwitchMap.put("." + val1 + "," + val2 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val3 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val3 + ".");
        SwitchMap.put("." + val1 + "," + val3 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val4 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val4 + ".");
        SwitchMap.put("." + val1 + "," + val4 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val5 + ".");
        SwitchMap.put("." + val1 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val6 + ".");
        SwitchMap.put("." + val1 + "," + val6 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + valBonus + ".") == null ? 0:SwitchMap.get("." + val1 + "," + valBonus + ".");
        SwitchMap.put("." + val1 + "," + valBonus + ".", _put + 1);

        /***************************************************************************/

        _put = SwitchMap.get("." + val2 + "," + val3 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val3 + ".");
        SwitchMap.put("." + val2 + "," + val3 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + val4 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val4 + ".");
        SwitchMap.put("." + val2 + "," + val4 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val5 + ".");
        SwitchMap.put("." + val2 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val6 + ".");
        SwitchMap.put("." + val2 + "," + val6 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + valBonus + ".") == null ? 0:SwitchMap.get("." + val2 + "," + valBonus + ".");
        SwitchMap.put("." + val2 + "," + valBonus + ".", _put + 1);

        /***************************************************************************/

        _put = SwitchMap.get("." + val3 + "," + val4 + ".") == null ? 0:SwitchMap.get("." + val3 + "," + val4 + ".");
        SwitchMap.put("." + val3 + "," + val4 + ".", _put + 1);

        _put = SwitchMap.get("." + val3 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val3 + "," + val5 + ".");
        SwitchMap.put("." + val3 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val3 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val3 + "," + val6 + ".");
        SwitchMap.put("." + val3 + "," + val6 + ".", _put + 1);

        _put = SwitchMap.get("." + val3 + "," + valBonus + ".") == null ? 0:SwitchMap.get("." + val3 + "," + valBonus + ".");
        SwitchMap.put("." + val3 + "," + valBonus + ".", _put + 1);

        /***************************************************************************/

        _put = SwitchMap.get("." + val4 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val4 + "," + val5 + ".");
        SwitchMap.put("." + val4 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val4 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val4 + "," + val6 + ".");
        SwitchMap.put("." + val4 + "," + val6 + ".", _put + 1);

        _put = SwitchMap.get("." + val4 + "," + valBonus + ".") == null ? 0:SwitchMap.get("." + val4 + "," + valBonus + ".");
        SwitchMap.put("." + val4 + "," + valBonus + ".", _put + 1);

        /***************************************************************************/

        _put = SwitchMap.get("." + val5 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val5 + "," + val6 + ".");
        SwitchMap.put("." + val5 + "," + val6 + ".", _put + 1);

        _put = SwitchMap.get("." + val5 + "," + valBonus + ".") == null ? 0:SwitchMap.get("." + val5 + "," + valBonus + ".");
        SwitchMap.put("." + val5 + "," + valBonus + ".", _put + 1);

        /***************************************************************************/

        _put = SwitchMap.get("." + val6 + "," + valBonus + ".") == null ? 0:SwitchMap.get("." + val6 + "," + valBonus + ".");
        SwitchMap.put("." + val6 + "," + valBonus + ".", _put + 1);

    }

    private void SwitchCombineNotBonus(int val1, int val2, int val3, int val4, int val5, int val6)
    {
        int _put = 0;

        _put = SwitchMap.get("." + val1 + "," + val2 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val2 + ".");
        SwitchMap.put("." + val1 + "," + val2 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val3 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val3 + ".");
        SwitchMap.put("." + val1 + "," + val3 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val4 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val4 + ".");
        SwitchMap.put("." + val1 + "," + val4 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val5 + ".");
        SwitchMap.put("." + val1 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val1 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val1 + "," + val6 + ".");
        SwitchMap.put("." + val1 + "," + val6 + ".", _put + 1);


        /***************************************************************************/

        _put = SwitchMap.get("." + val2 + "," + val3 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val3 + ".");
        SwitchMap.put("." + val2 + "," + val3 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + val4 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val4 + ".");
        SwitchMap.put("." + val2 + "," + val4 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val5 + ".");
        SwitchMap.put("." + val2 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val2 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val2 + "," + val6 + ".");
        SwitchMap.put("." + val2 + "," + val6 + ".", _put + 1);


        /***************************************************************************/

        _put = SwitchMap.get("." + val3 + "," + val4 + ".") == null ? 0:SwitchMap.get("." + val3 + "," + val4 + ".");
        SwitchMap.put("." + val3 + "," + val4 + ".", _put + 1);

        _put = SwitchMap.get("." + val3 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val3 + "," + val5 + ".");
        SwitchMap.put("." + val3 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val3 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val3 + "," + val6 + ".");
        SwitchMap.put("." + val3 + "," + val6 + ".", _put + 1);


        /***************************************************************************/

        _put = SwitchMap.get("." + val4 + "," + val5 + ".") == null ? 0:SwitchMap.get("." + val4 + "," + val5 + ".");
        SwitchMap.put("." + val4 + "," + val5 + ".", _put + 1);

        _put = SwitchMap.get("." + val4 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val4 + "," + val6 + ".");
        SwitchMap.put("." + val4 + "," + val6 + ".", _put + 1);


        /***************************************************************************/

        _put = SwitchMap.get("." + val5 + "," + val6 + ".") == null ? 0:SwitchMap.get("." + val5 + "," + val6 + ".");
        SwitchMap.put("." + val5 + "," + val6 + ".", _put + 1);

    }

    /**
     * 1의자리 몇개인지 10의자리 몇개인지 20의자리 몇개인지 30의자리 몇개인지 40의자리 몇개인지 체크 후 먼저 랜덤으로 해당 값을 정하고 이후 숫자를 뽑는다
     */
    @NonNull
    private Map<String, Integer> RandomCountSplit()
    {
        String[] dataAll = mPowerSDK.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME);

        Map<String, Map<String, Integer>> RandomCountMap = new HashMap<>(); //해쉬맵을 각횟차별로 정리한다
        int n1 = 0, n10=0, n20=0, n30=0, n40=0; //자릿수가 몇개인지 체크

        int drwno = 0;  //총 회차

        if (dataAll != null)
        {
            for(String n:dataAll)
            {
                drwno++;
                n1 = 0; n10=0; n20=0; n30=0; n40=0;
                String[] tmp = n.split(",");
                Map<String, Integer> RandomMap = new HashMap<>();   //각각의 자릿수가 몇개였는지를 체크한다
                for (int i = 1; i < 7; i++) //보너스숫자는 제외
                {
                    if (Integer.parseInt(tmp[i]) <= 10)
                    {
                        n1++;
                    }
                    else if (Integer.parseInt(tmp[i]) > 10 && Integer.parseInt(tmp[i]) <= 20)
                    {
                        n10++;
                    }
                    else if (Integer.parseInt(tmp[i]) > 20 && Integer.parseInt(tmp[i]) <= 30)
                    {
                        n20++;
                    }
                    else if (Integer.parseInt(tmp[i]) > 30 && Integer.parseInt(tmp[i]) <= 40)
                    {
                        n30++;
                    }
                    else if (Integer.parseInt(tmp[i]) > 40)
                    {
                        n40++;
                    }
                }

                RandomMap.put("1",n1); RandomMap.put("10",n10); RandomMap.put("20",n20); RandomMap.put("30",n30); RandomMap.put("40",n40);
                RandomCountMap.put(tmp[0],RandomMap);
            }
        }

        int totalN1 = 0,totalN10 = 0,totalN20 = 0,totalN30 = 0,totalN40 = 0;
        int n1_0 = 0,n1_1 = 0,n1_2 = 0,n1_3 = 0,n1_4 = 0,n1_5 = 0,n1_6 = 0;
        int n10_0 = 0,n10_1 = 0,n10_2 = 0,n10_3 = 0,n10_4 = 0,n10_5 = 0,n10_6 = 0;
        int n20_0 = 0,n20_1 = 0,n20_2 = 0,n20_3 = 0,n20_4 = 0,n20_5 = 0,n20_6 = 0;
        int n30_0 = 0,n30_1 = 0,n30_2 = 0,n30_3 = 0,n30_4 = 0,n30_5 = 0,n30_6 = 0;
        int n40_0 = 0,n40_1 = 0,n40_2 = 0,n40_3 = 0,n40_4 = 0,n40_5 = 0,n40_6 = 0;

        int count6_1=0,count6_10=0,count6_20=0,count6_30=0,count6_40=0;

        Map<String, Integer> _tmpMap = new HashMap<>();
        for(int i = 0; i<RandomCountMap.size(); i++)
        {
            _tmpMap.clear();
            _tmpMap = RandomCountMap.get(String.valueOf(i+1));
            totalN1 += _tmpMap.get("1"); totalN10 += _tmpMap.get("10"); totalN20 += _tmpMap.get("20"); totalN30 += _tmpMap.get("30"); totalN40 += _tmpMap.get("40");
            switch (_tmpMap.get("1"))
            {
                case 0: n1_0++; break;
                case 1: n1_1++; break;
                case 2: n1_2++; break;
                case 3: n1_3++; break;
                case 4: n1_4++; break;
                case 5: n1_5++; break;
                case 6: n1_6++; break;
            }
            switch (_tmpMap.get("10"))
            {
                case 0: n10_0++; break;
                case 1: n10_1++; break;
                case 2: n10_2++; break;
                case 3: n10_3++; break;
                case 4: n10_4++; break;
                case 5: n10_5++; break;
                case 6: n10_6++; break;
            }
            switch (_tmpMap.get("20"))
            {
                case 0: n20_0++; break;
                case 1: n20_1++; break;
                case 2: n20_2++; break;
                case 3: n20_3++; break;
                case 4: n20_4++; break;
                case 5: n20_5++; break;
                case 6: n20_6++; break;
            }
            switch (_tmpMap.get("30"))
            {
                case 0: n30_0++; break;
                case 1: n30_1++; break;
                case 2: n30_2++; break;
                case 3: n30_3++; break;
                case 4: n30_4++; break;
                case 5: n30_5++; break;
                case 6: n30_6++; break;
            }
            switch (_tmpMap.get("40"))
            {
                case 0: n40_0++; break;
                case 1: n40_1++; break;
                case 2: n40_2++; break;
                case 3: n40_3++; break;
                case 4: n40_4++; break;
                case 5: n40_5++; break;
                case 6: n40_6++; break;
            }
        }
        /** 1번대 10번대 20번대 30번대 40번대 중 무엇부터 뽑을지 랜덤으로 정한다 */
        Set<Integer> set = new HashSet<>();
        while (set.size()<5)
        {
            int j = 0;
            int i = 0;
            Random _random = new Random();
            j = _random.nextInt(5);
            switch (j)
            {
                case 0:
                    if(count6_1+count6_10+count6_20+count6_30+count6_40 == 6)
                    {
                        set.add(j);
                        break;
                    }

                    _random = new Random();
                    i = _random.nextInt(n1_0+n1_1+n1_2+n1_3+n1_4+n1_5+n1_6) + 1;
                    if (i <= n1_0) {count6_1 = 0;}
                    else if(i>n1_0 && i<=n1_0+n1_1) {count6_1 = 1;}
                    else if(i>n1_0+n1_1 && i<=n1_0+n1_1+n1_2) {count6_1 = 2;}
                    else if(i>n1_0+n1_1+n1_2 && i<=n1_0+n1_1+n1_2+n1_3) {count6_1 = 3;}
                    else if(i>n1_0+n1_1+n1_2+n1_3 && i<=n1_0+n1_1+n1_2+n1_3+n1_4) {count6_1 = 4;}
                    else if(i>n1_0+n1_1+n1_2+n1_3+n1_4 && i<=n1_0+n1_1+n1_2+n1_3+n1_4+n1_5) {count6_1 = 5;}
                    else if(i>n1_0+n1_1+n1_2+n1_3+n1_4+n1_5) {count6_1 = 6;}

                    if(count6_1+count6_10+count6_20+count6_30+count6_40 > 6)
                    {
                        while (count6_1+count6_10+count6_20+count6_30+count6_40==6)
                        {
                            _random = new Random();
                            i = _random.nextInt(n1_0+n1_1+n1_2+n1_3+n1_4+n1_5+n1_6) + 1;
                            if (i <= n1_0) {count6_1 = 0;}
                            else if(i>n1_0 && i<=n1_0+n1_1) {count6_1 = 1;}
                            else if(i>n1_0+n1_1 && i<=n1_0+n1_1+n1_2) {count6_1 = 2;}
                            else if(i>n1_0+n1_1+n1_2 && i<=n1_0+n1_1+n1_2+n1_3) {count6_1 = 3;}
                            else if(i>n1_0+n1_1+n1_2+n1_3 && i<=n1_0+n1_1+n1_2+n1_3+n1_4) {count6_1 = 4;}
                            else if(i>n1_0+n1_1+n1_2+n1_3+n1_4 && i<=n1_0+n1_1+n1_2+n1_3+n1_4+n1_5) {count6_1 = 5;}
                            else if(i>n1_0+n1_1+n1_2+n1_3+n1_4+n1_5) {count6_1 = 6;}
                        }
                    }
                    break;
                case 1:
                    if(count6_1+count6_10+count6_20+count6_30+count6_40 == 6)
                    {
                        set.add(j);
                        break;
                    }

                    _random = new Random();
                    i = _random.nextInt(n10_0+n10_1+n10_2+n10_3+n10_4+n10_5+n10_6) + 1;
                    if (i <= n10_0) {count6_10 = 0;}
                    else if(i>n10_0 && i<=n10_0+n10_1) {count6_10 = 1;}
                    else if(i>n10_0+n10_1 && i<=n10_0+n10_1+n10_2) {count6_10 = 2;}
                    else if(i>n10_0+n10_1+n10_2 && i<=n10_0+n10_1+n10_2+n10_3) {count6_10 = 3;}
                    else if(i>n10_0+n10_1+n10_2+n10_3 && i<=n10_0+n10_1+n10_2+n10_3+n10_4) {count6_10 = 4;}
                    else if(i>n10_0+n10_1+n10_2+n10_3+n10_4 && i<=n10_0+n10_1+n10_2+n10_3+n10_4+n10_5) {count6_10 = 5;}
                    else if(i>n10_0+n10_1+n10_2+n10_3+n10_4+n10_5) {count6_10 = 6;}

                    if(count6_1+count6_10+count6_20+count6_30+count6_40 > 6) {
                        while (count6_1 + count6_10 + count6_20 + count6_30 + count6_40 == 6) {
                            _random = new Random();
                            i = _random.nextInt(n10_0+n10_1+n10_2+n10_3+n10_4+n10_5+n10_6) + 1;
                            if (i <= n10_0) {count6_10 = 0;}
                            else if(i>n10_0 && i<=n10_0+n10_1) {count6_10 = 1;}
                            else if(i>n10_0+n10_1 && i<=n10_0+n10_1+n10_2) {count6_10 = 2;}
                            else if(i>n10_0+n10_1+n10_2 && i<=n10_0+n10_1+n10_2+n10_3) {count6_10 = 3;}
                            else if(i>n10_0+n10_1+n10_2+n10_3 && i<=n10_0+n10_1+n10_2+n10_3+n10_4) {count6_10 = 4;}
                            else if(i>n10_0+n10_1+n10_2+n10_3+n10_4 && i<=n10_0+n10_1+n10_2+n10_3+n10_4+n10_5) {count6_10 = 5;}
                            else if(i>n10_0+n10_1+n10_2+n10_3+n10_4+n10_5) {count6_10 = 6;}
                        }
                    }

                    break;
                case 2:
                    if(count6_1+count6_10+count6_20+count6_30+count6_40 == 6)
                    {
                        set.add(j);
                        break;
                    }

                    _random = new Random();
                    i = _random.nextInt(n20_0+n20_1+n20_2+n20_3+n20_4+n20_5+n20_6) + 1;
                    if (i <= n20_0) {count6_20 = 0;}
                    else if(i>n20_0 && i<=n20_0+n20_1) {count6_20 = 1;}
                    else if(i>n20_0+n20_1 && i<=n20_0+n20_1+n20_2) {count6_20 = 2;}
                    else if(i>n20_0+n20_1+n20_2 && i<=n20_0+n20_1+n20_2+n20_3) {count6_20 = 3;}
                    else if(i>n20_0+n20_1+n20_2+n20_3 && i<=n20_0+n20_1+n20_2+n20_3+n20_4) {count6_20 = 4;}
                    else if(i>n20_0+n20_1+n20_2+n20_3+n20_4 && i<=n20_0+n20_1+n20_2+n20_3+n20_4+n20_5) {count6_20 = 5;}
                    else if(i>n20_0+n20_1+n20_2+n20_3+n20_4+n20_5) {count6_20 = 6;}

                    if(count6_1+count6_10+count6_20+count6_30+count6_40 > 6) {
                        while (count6_1 + count6_10 + count6_20 + count6_30 + count6_40 == 6) {
                            _random = new Random();
                            i = _random.nextInt(n20_0+n20_1+n20_2+n20_3+n20_4+n20_5+n20_6) + 1;
                            if (i <= n20_0) {count6_20 = 0;}
                            else if(i>n20_0 && i<=n20_0+n20_1) {count6_20 = 1;}
                            else if(i>n20_0+n20_1 && i<=n20_0+n20_1+n20_2) {count6_20 = 2;}
                            else if(i>n20_0+n20_1+n20_2 && i<=n20_0+n20_1+n20_2+n20_3) {count6_20 = 3;}
                            else if(i>n20_0+n20_1+n20_2+n20_3 && i<=n20_0+n20_1+n20_2+n20_3+n20_4) {count6_20 = 4;}
                            else if(i>n20_0+n20_1+n20_2+n20_3+n20_4 && i<=n20_0+n20_1+n20_2+n20_3+n20_4+n20_5) {count6_20 = 5;}
                            else if(i>n20_0+n20_1+n20_2+n20_3+n20_4+n20_5) {count6_20 = 6;}
                        }
                    }

                    break;
                case 3:
                    if(count6_1+count6_10+count6_20+count6_30+count6_40 == 6)
                    {
                        set.add(j);
                        break;
                    }

                    _random = new Random();
                    i = _random.nextInt(n30_0+n30_1+n30_2+n30_3+n30_4+n30_5+n30_6) + 1;
                    if (i <= n30_0) {count6_30 = 0;}
                    else if(i>n30_0 && i<=n30_0+n30_1) {count6_30 = 1;}
                    else if(i>n30_0+n30_1 && i<=n30_0+n30_1+n30_2) {count6_30 = 2;}
                    else if(i>n30_0+n30_1+n30_2 && i<=n30_0+n30_1+n30_2+n30_3) {count6_30 = 3;}
                    else if(i>n30_0+n30_1+n30_2+n30_3 && i<=n30_0+n30_1+n30_2+n30_3+n30_4) {count6_30 = 4;}
                    else if(i>n30_0+n30_1+n30_2+n30_3+n30_4 && i<=n30_0+n30_1+n30_2+n30_3+n30_4+n30_5) {count6_30 = 5;}
                    else if(i>n30_0+n30_1+n30_2+n30_3+n30_4+n30_5) {count6_30 = 6;}

                    if(count6_1+count6_10+count6_20+count6_30+count6_40 > 6) {
                        while (count6_1 + count6_10 + count6_20 + count6_30 + count6_40 == 6) {
                            _random = new Random();
                            i = _random.nextInt(n30_0+n30_1+n30_2+n30_3+n30_4+n30_5+n30_6) + 1;
                            if (i <= n30_0) {count6_30 = 0;}
                            else if(i>n30_0 && i<=n30_0+n30_1) {count6_30 = 1;}
                            else if(i>n30_0+n30_1 && i<=n30_0+n30_1+n30_2) {count6_30 = 2;}
                            else if(i>n30_0+n30_1+n30_2 && i<=n30_0+n30_1+n30_2+n30_3) {count6_30 = 3;}
                            else if(i>n30_0+n30_1+n30_2+n30_3 && i<=n30_0+n30_1+n30_2+n30_3+n30_4) {count6_30 = 4;}
                            else if(i>n30_0+n30_1+n30_2+n30_3+n30_4 && i<=n30_0+n30_1+n30_2+n30_3+n30_4+n30_5) {count6_30 = 5;}
                            else if(i>n30_0+n30_1+n30_2+n30_3+n30_4+n30_5) {count6_30 = 6;}
                        }
                    }

                    break;
                case 4:
                    if(count6_1+count6_10+count6_20+count6_30+count6_40 == 6)
                    {
                        set.add(j);
                        break;
                    }

                    _random = new Random();
                    i = _random.nextInt(n40_0+n40_1+n40_2+n40_3+n40_4+n40_5+n40_6) + 1;
                    if (i <= n40_0) {count6_40 = 0;}
                    else if(i>n40_0 && i<=n40_0+n40_1) {count6_40 = 1;}
                    else if(i>n40_0+n40_1 && i<=n40_0+n40_1+n40_2) {count6_40 = 2;}
                    else if(i>n40_0+n40_1+n40_2 && i<=n40_0+n40_1+n40_2+n40_3) {count6_40 = 3;}
                    else if(i>n40_0+n40_1+n40_2+n40_3 && i<=n40_0+n40_1+n40_2+n40_3+n40_4) {count6_40 = 4;}
                    else if(i>n40_0+n40_1+n40_2+n40_3+n40_4 && i<=n40_0+n40_1+n40_2+n40_3+n40_4+n40_5) {count6_40 = 5;}
                    else if(i>n40_0+n40_1+n40_2+n40_3+n40_4+n40_5) {count6_40 = 6;}

                    if(count6_1+count6_10+count6_20+count6_30+count6_40 > 6) {
                        while (count6_1 + count6_10 + count6_20 + count6_30 + count6_40 == 6) {
                            _random = new Random();
                            i = _random.nextInt(n40_0+n40_1+n40_2+n40_3+n40_4+n40_5+n40_6) + 1;
                            if (i <= n40_0) {count6_40 = 0;}
                            else if(i>n40_0 && i<=n40_0+n40_1) {count6_40 = 1;}
                            else if(i>n40_0+n40_1 && i<=n40_0+n40_1+n40_2) {count6_40 = 2;}
                            else if(i>n40_0+n40_1+n40_2 && i<=n40_0+n40_1+n40_2+n40_3) {count6_40 = 3;}
                            else if(i>n40_0+n40_1+n40_2+n40_3 && i<=n40_0+n40_1+n40_2+n40_3+n40_4) {count6_40 = 4;}
                            else if(i>n40_0+n40_1+n40_2+n40_3+n40_4 && i<=n40_0+n40_1+n40_2+n40_3+n40_4+n40_5) {count6_40 = 5;}
                            else if(i>n40_0+n40_1+n40_2+n40_3+n40_4+n40_5) {count6_40 = 6;}
                        }
                    }

                    break;
            }
            set.add(j);
            if(set.size() == 5 )
            {
                if(count6_1+count6_10+count6_20+count6_30+count6_40 != 6)
                {
                    set.clear();
                }
            }
        }

        Map<String,Integer> totalCount = new HashMap<>();
        totalCount.put("1", count6_1);     totalCount.put("10", count6_10);     totalCount.put("20", count6_20);
        totalCount.put("30", count6_30);     totalCount.put("40", count6_40);

        return totalCount;
    }

    private void ButtonEffect()
    {
        ObjectAnimator anim = ObjectAnimator.ofInt(mTxt_num_congratu, "textColor", Color.RED, Color.GREEN, Color.BLUE);
        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();

//        ObjectAnimator anim1 = ObjectAnimator.ofInt(listview, "backgroundColor", Color.RED, Color.GREEN, Color.BLUE);
//        anim1.setDuration(1500);
//        anim1.setEvaluator(new ArgbEvaluator());
//        anim1.setRepeatMode(ValueAnimator.REVERSE);
//        anim1.setRepeatCount(Animation.INFINITE);
//        anim1.start();
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        Toast.makeText(mCtx, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        mInterstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");

                                        Intent intent = new Intent();
                                        intent.putExtra("result","Completed");
                                        setResult(103, intent);
                                        finish();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");

                                        Intent intent = new Intent();
                                        intent.putExtra("result","Completed");
                                        setResult(103, intent);
                                        finish();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                        init();
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                                mCtx, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = new Intent();
                        intent.putExtra("result","Completed");
                        setResult(103, intent);
                        finish();
                    }
                });
    }


    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("result","Completed");
        setResult(103, intent);
        finish();
        return;
    }

}