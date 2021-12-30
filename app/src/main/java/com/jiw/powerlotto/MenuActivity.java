package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * 이번주 당첨번호 표시. 하단 버튼 표시. 당신의 길일 체크(로또사는날). 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class MenuActivity extends AppCompatActivity {

    Button mBtnHome, mBtnPre, mBtnQr, mBtnNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    private void init()
    {
        mBtnHome = findViewById(R.id.menu_btn_home);
        mBtnPre = findViewById(R.id.menu_btn_preview);
        mBtnQr = findViewById(R.id.menu_btn_qr);
        mBtnNum = findViewById(R.id.menu_btn_number);

        mBtnHome.setOnClickListener(v->{

        });
        mBtnPre.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        mBtnQr.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), QR_CheckActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        mBtnNum.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), NumberGeneratorActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}