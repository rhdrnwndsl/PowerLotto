package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 * 번호 추첨화면(30초 광고도 표시) 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class NumberGeneratorActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    int mGeneratorComplete = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_generator);
    }

    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        if(mGeneratorComplete == 1)
        {
            intent.putExtra("result","Completed");
        } else {
            intent.putExtra("result","Cancelled");
        }
        setResult(103, intent);
        finish();
        return;
    }
}