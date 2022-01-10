package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 이전회차들(모든회차) 당첨번호 표시. 하단 광고바(모든화면에 하단광고바가 표시)
 */
public class PreviewActivity extends AppCompatActivity {

    String TAG = this.getClass().getName();
    /** sqlite에 저장된 최근무결성검사 10개를 리스트로 화면에 구성한다 */
    ListView listview ;
    /** 위 listview 에 연계되는 adapter 해당 리스트들의 각각의 데이터들의 위치 리스트추가 등의 기능을 수행한다다*/
    ListPreviewAdapter adapter;
    PowerSDK mPowerSdk;

    Context _ctx;

    boolean loop = true;    //에러가 나올 때까지 루프를 돌아서 당첨번호를 모은다

    Button m_btn_preview_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        _ctx = this;
        init();
    }

    private void init()
    {
        adapter = new ListPreviewAdapter();
        listview = (ListView) findViewById(R.id.list_preview_all);
        listview.setAdapter(adapter);
        mPowerSdk = PowerSDK.getInstance();

        m_btn_preview_exit = findViewById(R.id.btn_preview_exit);
        m_btn_preview_exit.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.putExtra("result","Completed");
            setResult(101, intent);
            finish();
            return;
        });

        UpdateList();
    }

    /**
     * DB에서 로또 회차정보를 가져와서 ListView 에 업데이트 시킨다.
     */
    private void UpdateList()
    {
        adapter = new ListPreviewAdapter();
        String [] data = mPowerSdk.SelectData(Database.DB_ALLCOMPILENUMBER_TABLENAME); /* sqlite 에서 무결성 검사 데이터 가져오기 */

        if(data!=null)
        {
            for(String n:data)
            {
                String[] tmp = n.split(",");
                adapter.addItem(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6],tmp[7]);
            }
        }

        listview.setAdapter(adapter);

    }



    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result","Completed");
        setResult(101, intent);
        finish();
        return;
    }
}