package com.jiw.powerlotto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

/**
 * 스플레쉬 화면
 */
public class MainActivity extends AppCompatActivity {

    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.splash_animationView);
//        animationView.setOnClickListener(v->{
//            Intent intent = new Intent(this, MenuActivity.class);
//            startActivity(intent);
//            finish();
//        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

    }

}