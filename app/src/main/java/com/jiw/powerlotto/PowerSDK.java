package com.jiw.powerlotto;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PowerSDK {
    private final static String TAG = PowerSDK.Name;
    private static String Name = "PowerSDK";
    private static PowerSDK mInstance;
    private static Context mCtx;
    private Database mSqlite;
    private final static String SharePerfenceceKey = "com.powerlotto.Preference";

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

    /**
     * 안드로이드에 저장되어 있던 설정들(디바이스 등의 정보)을 불러온다
     * @param _ctx
     * @return
     */
    private static SharedPreferences getSharedPreferences(Context _ctx) {
        return _ctx.getSharedPreferences(SharePerfenceceKey, _ctx.MODE_PRIVATE);
    }

    /**
     * 안드로이드에 설정들(디바이스 등의 정보)를 저장한다
     * @param _ctx 해당엑티비티
     * @param _key 값이 저장될 키
     * @param _val 저장할 값
     */
    public static void setStringPreference(Context _ctx, String _key, String _val) {
        SharedPreferences Pref = getSharedPreferences(_ctx);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putString(_key, _val);
        editor.commit();
    }

    public static void setIntPreference(Context _ctx, String _key, int _val) {
        SharedPreferences Pref = getSharedPreferences(_ctx);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putInt(_key, _val);
        editor.commit();
    }

    /**
     * 안드로이드에서 불러온 설정들을 해당 엑티비티로 보낸다
     * @param _ctx 해당엑티비티
     * @param _key 저장된 값들의 키
     * @return
     */
    public static String getStringPreference(Context _ctx, String _key) {
        SharedPreferences Pref = getSharedPreferences(_ctx);
        String tmp = Pref.getString(_key, "");
        return tmp;
    }

    public static int getIntPreference(Context _ctx, String _key) {
        SharedPreferences Pref = getSharedPreferences(_ctx);
        int tmp = Pref.getInt(_key, 0);
        return tmp;
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

    public void DeleteQRData(String _url)
    {
        try{
            mSqlite.DeleteQRData(_url);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }
    }


}
