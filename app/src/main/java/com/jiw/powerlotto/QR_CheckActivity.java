package com.jiw.powerlotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * QR 당첨번호 표시. QR화면이 나오고 QR을 체크하면 바로 해당 번호의 당첨을 확인한다.
 * 하단에 QR당첨번호 버튼을 누르면 다시 QR화면이 나와서 계속 인식 할 수 있게 한다.
 * 제일 하단에는 광고바가 표시된다.
 */
public class QR_CheckActivity extends AppCompatActivity {

    String TAG = this.getClass().getName();
    IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_check);

        intentIntegrator = new IntentIntegrator(QR_CheckActivity.this);
//        intentIntegrator.setOrientationLocked(false);   //세로
        intentIntegrator.setPrompt("QR 코드를 읽어주세요");
        intentIntegrator.initiateScan();

        // 리스트뷰 초기화
//        initListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);


    }


    /**
     * 로또 당첨 결과 스크랩핑 URL 주소 가져오기
     */
    private String getServerUrl(String value) {
        String[] temps = value.split("/?v=");
        if (temps.length != 2) {
            return value;
        }
        return "https://m.dhlottery.co.kr/qr.do?method=winQr&v=" + temps[1];

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
            intentIntegrator = null;

            Log.e(TAG, "URL : " + result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 스캔된 URL 주소 스크랩핑 처리 * @param scanUrl
     */
    private void getServerData(String scanUrl) {
        Log.e(TAG, "Scan Url : " + scanUrl);
        String url = getServerUrl(scanUrl);
        Log.e(TAG, "Scrap Url : " + url);
        RequestQueue queue = Volley.newRequestQueue(this);

        final String finalUrl = url;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document serverDoc = Jsoup.parse(response);
                        String result = serverDoc.getElementById("container").html();

                        // 스크랩핑 결과 데이터 로그로 표시
                        Log.e(TAG, "onResponse: " + result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        queue.add(stringRequest);

    }
}