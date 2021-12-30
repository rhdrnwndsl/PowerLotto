package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 번호 추첨화면(30초 광고도 표시) 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class NumberGeneratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_generator);
    }
}