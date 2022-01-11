package com.jiw.powerlotto;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class PowerSDK {
    private final static String TAG = PowerSDK.Name;
    private static String Name = "PowerSDK";
    private static PowerSDK mInstance;
    private static Context mCtx;
    private Database mSqlite;

    public PowerSDK(Context _ctx) {
        mCtx = _ctx;
        mSqlite = new Database(mCtx); // sqlite 생성

    }

    public static PowerSDK getInstance() {
        if (mInstance == null) {
            mInstance = new PowerSDK(mCtx);
        }
        return mInstance;
    }

    public Context getContext() {
        return mCtx;
    }

    public void InsertQRCheckData(String _url, String _result)
    {
        try{
            mSqlite.InsertQRCheckData(_url, _result);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }
    }

    public void DeleteQRCheckData()
    {

    }

    public void InsertPreviewData(int _drwNo, String _no1, String _no2, String _no3,
                                  String _no4, String _no5, String _no6, String _bonus)
    {
        try{
            mSqlite.InsertPreviewData(_drwNo, _no1,_no2,_no3,_no4,_no5,_no6,_bonus);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }
    }

    public String[] SelectData(String TableName)
    {
        String[] selectQuery = new String[]{};
        try{
            selectQuery = mSqlite.SelectData(TableName);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }

        return selectQuery;
    }
}
