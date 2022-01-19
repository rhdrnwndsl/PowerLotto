package com.jiw.powerlotto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NumberSortingActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    Button m_btn_sort_exit;
    PowerSDK mPowerSDK;
    Context mCtx;
    /** sqlite에 저장된 최근무결성검사 10개를 리스트로 화면에 구성한다 */
    ListView listview ;
    /** 위 listview 에 연계되는 adapter 해당 리스트들의 각각의 데이터들의 위치 리스트추가 등의 기능을 수행한다다*/
    ListSortAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);
        mCtx = this;
        init();
    }

    private void init()
    {
        adapter = new ListSortAdapter();
        listview = (ListView) findViewById(R.id.list_sort_all);
        listview.setAdapter(adapter);
        mPowerSDK = PowerSDK.getInstance();
        m_btn_sort_exit = findViewById(R.id.btn_sort_exit);
        m_btn_sort_exit.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.putExtra("result","Completed");
            setResult(104, intent);
            finish();
            return;
        });

        NumberSortingDATAResult();
    }

    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result","Completed");
        setResult(104, intent);
        finish();
        return;
    }

    public void NumberSortingDATAResult()
    {
        adapter = new ListSortAdapter();
//        int n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19,n20,n21,n22,n23,n24,n25,
//                n26,n27,n28,n29,n30,n31,n32,n33,n34,n35,n36,n37,n38,n39,n40,n41,n42,n43,n44,n45;
//        n1 = mPowerSDK.getIntPreference(mCtx,"1");n11 = mPowerSDK.getIntPreference(mCtx,"11");n21 = mPowerSDK.getIntPreference(mCtx,"21");n31 = mPowerSDK.getIntPreference(mCtx,"31");
//        n2 = mPowerSDK.getIntPreference(mCtx,"2");n12 = mPowerSDK.getIntPreference(mCtx,"12");n22 = mPowerSDK.getIntPreference(mCtx,"22");n32 = mPowerSDK.getIntPreference(mCtx,"32");
//        n3 = mPowerSDK.getIntPreference(mCtx,"3");n13 = mPowerSDK.getIntPreference(mCtx,"13");n23 = mPowerSDK.getIntPreference(mCtx,"23");n33 = mPowerSDK.getIntPreference(mCtx,"33");
//        n4 = mPowerSDK.getIntPreference(mCtx,"4");n14 = mPowerSDK.getIntPreference(mCtx,"14");n24 = mPowerSDK.getIntPreference(mCtx,"24");n34 = mPowerSDK.getIntPreference(mCtx,"34");
//        n5 = mPowerSDK.getIntPreference(mCtx,"5");n15 = mPowerSDK.getIntPreference(mCtx,"15");n25 = mPowerSDK.getIntPreference(mCtx,"25");n35 = mPowerSDK.getIntPreference(mCtx,"35");
//        n6 = mPowerSDK.getIntPreference(mCtx,"6");n16 = mPowerSDK.getIntPreference(mCtx,"16");n26 = mPowerSDK.getIntPreference(mCtx,"26");n36 = mPowerSDK.getIntPreference(mCtx,"36");
//        n7 = mPowerSDK.getIntPreference(mCtx,"7");n17 = mPowerSDK.getIntPreference(mCtx,"17");n27 = mPowerSDK.getIntPreference(mCtx,"27");n37 = mPowerSDK.getIntPreference(mCtx,"37");
//        n8 = mPowerSDK.getIntPreference(mCtx,"8");n18 = mPowerSDK.getIntPreference(mCtx,"18");n28 = mPowerSDK.getIntPreference(mCtx,"28");n38 = mPowerSDK.getIntPreference(mCtx,"38");
//        n9 = mPowerSDK.getIntPreference(mCtx,"9");n19 = mPowerSDK.getIntPreference(mCtx,"19");n29 = mPowerSDK.getIntPreference(mCtx,"29");n39 = mPowerSDK.getIntPreference(mCtx,"39");
//        n10 = mPowerSDK.getIntPreference(mCtx,"10");n20 = mPowerSDK.getIntPreference(mCtx,"20");n30 = mPowerSDK.getIntPreference(mCtx,"30");n40 = mPowerSDK.getIntPreference(mCtx,"40");
//
//        n41 = mPowerSDK.getIntPreference(mCtx,"41");
//        n42 = mPowerSDK.getIntPreference(mCtx,"42");
//        n43 = mPowerSDK.getIntPreference(mCtx,"43");
//        n44 = mPowerSDK.getIntPreference(mCtx,"44");
//        n45 = mPowerSDK.getIntPreference(mCtx,"45");

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
            adapter.addItem(entry.getKey(),entry.getValue());
        }

        listview.setAdapter(adapter);
    }
}
