package com.jiw.powerlotto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    private void init()
    {
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

        if(data!=null)
        {
            for(String n:data)
            {
                String[] tmp = n.split("\\^");
                ListModel listModel = ListModel.build(tmp[0], tmp[1]);
                listModel.print();

                // 리스트에 해당 항목 추가
                listModelArrayList.add(listModel);
                listAdapter.notifyDataSetChanged();
            }
        }

        listView.setAdapter(listAdapter);
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