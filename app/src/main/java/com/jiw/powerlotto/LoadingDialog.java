package com.jiw.powerlotto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class LoadingDialog extends Dialog {
    private Activity activity;
    private TextView mtxt_msg;
    private String mMsg;

    public LoadingDialog(){
        super(null);
    };
    public LoadingDialog(@NonNull Context context){super(context);};

    public LoadingDialog(@NonNull Context context, String _msg) {
        super(context);
        mMsg = _msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        this.setCancelable(false);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.custom_dialog_loading);

        init();

    }

    private void init()
    {
        mtxt_msg = (TextView)findViewById(R.id.txt_loading_msg);
        mtxt_msg.setText(mMsg);
    }

    public void LoadingTextUpdate(String _msg)
    {
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            mtxt_msg.setText(_msg);
        },500);

    }



    public void dismisDialog()
    {
        dismiss();
    }
}
