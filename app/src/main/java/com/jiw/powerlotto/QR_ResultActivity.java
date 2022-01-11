package com.jiw.powerlotto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

public class QR_ResultActivity extends AppCompatActivity {

    ArrayList<ListModel> listModelArrayList;
    ListAdapter listAdapter;
    int mScanComplete = 0;
    String mUrl = "";
    String mResult = "";
    PowerSDK mPowerSDK;

    String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_result);

        Intent mintent = new Intent(getApplicationContext(), QR_CheckActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(mintent, 104);

        mPowerSDK = PowerSDK.getInstance();
    }


    private void initListView() {
        listModelArrayList = new ArrayList<>();
        listAdapter = new ListAdapter(this, R.layout.layout_list, listModelArrayList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 104) {
            // 스크랩핑 된 데이터를 목적에 맞게 파싱 후 로그로 표시
            String result = data.getStringExtra("result");

            if (result.equals("Cancelled"))
            {
                Intent intent = new Intent();
                intent.putExtra("result","Cancelled");
                setResult(102, intent);
                finish();
                return;
            }

            String url = data.getStringExtra("url");
            int scan = data.getIntExtra("scan", 0);

            mUrl = url;
            mScanComplete = scan;
            mResult = result;

            // 리스트뷰 초기화
            initListView();

            ListModel listModel = ListModel.build(mUrl, mResult);
            listModel.print();

            // 리스트에 해당 항목 추가
            listModelArrayList.add(listModel);
            listAdapter.notifyDataSetChanged();

            mPowerSDK.InsertQRCheckData(mUrl,mResult);

        }

    }

    //뒤로가기 버튼 눌렀을 때
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        if(mScanComplete == 1)
        {
            intent.putExtra("result","Completed");
        } else {
            intent.putExtra("result","Cancelled");
        }
        setResult(102, intent);
        finish();
        return;
    }
}
