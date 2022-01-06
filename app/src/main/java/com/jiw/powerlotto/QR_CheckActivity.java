package com.jiw.powerlotto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

import java.util.ArrayList;

/**
 * QR 당첨번호 표시. QR화면이 나오고 QR을 체크하면 바로 해당 번호의 당첨을 확인한다.
 * 하단에 QR당첨번호 버튼을 누르면 다시 QR화면이 나와서 계속 인식 할 수 있게 한다.
 * 제일 하단에는 광고바가 표시된다.
 */
public class QR_CheckActivity extends AppCompatActivity {

    String TAG = this.getClass().getName();
    IntentIntegrator intentIntegrator;
    ArrayList<ListModel> listModelArrayList;
    ListAdapter listAdapter;
    int mScanComplete = 0;
    String mUrl = "";
    String mResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_check);

        // 리스트뷰 초기화
//        initListView();

        intentIntegrator = new IntentIntegrator(QR_CheckActivity.this);
        intentIntegrator.setOrientationLocked(false);   //세로
        intentIntegrator.setPrompt("QR 코드를 읽어주세요");
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.initiateScan();
    }


//    private void initListView() {
//        listModelArrayList = new ArrayList<>();
//        listAdapter = new ListAdapter(this, R.layout.layout_list, listModelArrayList);
//        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter(listAdapter);
//    }

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

    public void ScanComplete(String _url, String _result)
    {
        Intent intent = new Intent();
        intent.putExtra("scan", 1);
        intent.putExtra("url", _url);
        intent.putExtra("result", _result);
        setResult(104, intent);
        finishAndRemoveTask();
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("result","Cancelled");
                setResult(104, intent);
                finishAndRemoveTask();
                return;
            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }

            Log.e(TAG, "URL : " + result.getContents());
            // 스크랩핑 처리
            getServerData(result.getContents());

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 로또 당첨 결과 스크랩핑 URL 주소 가져오기
     */
    private String getServerUrl(String value) {
        if (value == null || value.equals(""))
        {
            return "";
        }
        String[] temps = value.split("v=");
        if (temps.length != 2) {
            return value;
        }
        return "https://m.dhlottery.co.kr/qr.do?method=winQr&v=" + temps[1];

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

                        // 스크랩핑 된 데이터를 목적에 맞게 파싱 후 로그로 표시
//                        ListModel listModel = ListModel.build(finalUrl, result);
//                        listModel.print();

                        // 리스트에 해당 항목 추가
//                        listModelArrayList.add(listModel);
//                        listAdapter.notifyDataSetChanged();
                        ScanComplete(finalUrl, result);
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