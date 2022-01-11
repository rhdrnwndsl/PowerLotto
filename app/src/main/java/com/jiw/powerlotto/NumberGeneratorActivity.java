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
        n1 = mPowerSDK.getIntPreference(mCtx,"1");n11 = mPowerSDK.getIntPreference(mCtx,"11");n21 = mPowerSDK.getIntPreference(mCtx,"21");n31 = mPowerSDK.getIntPreference(mCtx,"31");
        n2 = mPowerSDK.getIntPreference(mCtx,"2");n12 = mPowerSDK.getIntPreference(mCtx,"12");n22 = mPowerSDK.getIntPreference(mCtx,"22");n32 = mPowerSDK.getIntPreference(mCtx,"32");
        n3 = mPowerSDK.getIntPreference(mCtx,"3");n13 = mPowerSDK.getIntPreference(mCtx,"13");n23 = mPowerSDK.getIntPreference(mCtx,"23");n33 = mPowerSDK.getIntPreference(mCtx,"33");
        n4 = mPowerSDK.getIntPreference(mCtx,"4");n14 = mPowerSDK.getIntPreference(mCtx,"14");n24 = mPowerSDK.getIntPreference(mCtx,"24");n34 = mPowerSDK.getIntPreference(mCtx,"34");
        n5 = mPowerSDK.getIntPreference(mCtx,"5");n15 = mPowerSDK.getIntPreference(mCtx,"15");n25 = mPowerSDK.getIntPreference(mCtx,"25");n35 = mPowerSDK.getIntPreference(mCtx,"35");
        n6 = mPowerSDK.getIntPreference(mCtx,"6");n16 = mPowerSDK.getIntPreference(mCtx,"16");n26 = mPowerSDK.getIntPreference(mCtx,"26");n36 = mPowerSDK.getIntPreference(mCtx,"36");
        n7 = mPowerSDK.getIntPreference(mCtx,"7");n17 = mPowerSDK.getIntPreference(mCtx,"17");n27 = mPowerSDK.getIntPreference(mCtx,"27");n37 = mPowerSDK.getIntPreference(mCtx,"37");
        n8 = mPowerSDK.getIntPreference(mCtx,"8");n18 = mPowerSDK.getIntPreference(mCtx,"18");n28 = mPowerSDK.getIntPreference(mCtx,"28");n38 = mPowerSDK.getIntPreference(mCtx,"38");
        n9 = mPowerSDK.getIntPreference(mCtx,"9");n19 = mPowerSDK.getIntPreference(mCtx,"19");n29 = mPowerSDK.getIntPreference(mCtx,"29");n39 = mPowerSDK.getIntPreference(mCtx,"39");
        n10 = mPowerSDK.getIntPreference(mCtx,"10");n20 = mPowerSDK.getIntPreference(mCtx,"20");n30 = mPowerSDK.getIntPreference(mCtx,"30");n40 = mPowerSDK.getIntPreference(mCtx,"40");

        n41 = mPowerSDK.getIntPreference(mCtx,"41");
        n42 = mPowerSDK.getIntPreference(mCtx,"42");
        n43 = mPowerSDK.getIntPreference(mCtx,"43");
        n44 = mPowerSDK.getIntPreference(mCtx,"44");
        n45 = mPowerSDK.getIntPreference(mCtx,"45");

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