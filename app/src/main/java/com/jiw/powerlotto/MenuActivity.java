package com.jiw.powerlotto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

/**
 * 이번주 당첨번호 표시. 하단 버튼 표시. 당신의 길일 체크(로또사는날). 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class MenuActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(), "메뉴 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "메뉴 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }

        }

        else if (requestCode == 102) {
            if (data != null)
            {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(),"메뉴 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "메뉴 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }
        }

        else if (requestCode == 103) {
            if (data != null)
            {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(),"메뉴 화면으로부터 받은 응답 : " + result, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "메뉴 화면으로부터 받은 응답 : ", Toast.LENGTH_LONG).show();
            }
        }
    }
}