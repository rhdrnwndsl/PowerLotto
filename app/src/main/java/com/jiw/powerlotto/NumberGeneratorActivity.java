package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

/**
 * 번호 추첨화면(30초 광고도 표시) 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class NumberGeneratorActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    PowerSDK mPowerSDK;
    Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_generator);
        mCtx = this;
        init();
    }

    private void init()
    {
        mPowerSDK = PowerSDK.getInstance();
        int max = 45;

        int no1 = 0;
        int no2 = 0;
        int no3 = 0;
        int no4 = 0;
        int no5 = 0;
        int no6 = 0;


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

        Set<Integer> set = new HashSet<>();
        int i = 0;
        while (set.size() < 6) {
            Random _random = new Random();
            i = _random.nextInt(max) + 1;
            i = SwitchNumber(i);
            set.add(i);
        }

        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        System.out.println(list);
        no1 = list.get(0);
        no2 = list.get(1);
        no3 = list.get(2);
        no4 = list.get(3);
        no5 = list.get(4);
        no6 = list.get(5);

        final String _msg = "번호 생성 : " + no1 + ", " + no2 + ", " + no3 + ", " + no4 + ", " + no5 + ", " + no6;
        Log.d(TAG, _msg);
        Toast.makeText(this, _msg, Toast.LENGTH_LONG).show();
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