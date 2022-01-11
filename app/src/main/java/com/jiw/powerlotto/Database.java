package com.jiw.powerlotto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    private static final String mDbName = "powerlotto.db";
    private static final int DATABASE_VERSION = 1;
    final static String TAG = "Database";
    public static SQLiteDatabase mSqliteDB;
    private Context mCtx;

    /** 디바이스 무결성 테이블 이름 */
    public static final String DB_QRCHECK_TABLENAME = "TABLE_QRCHECK";
    public static final String DB_ALLCOMPILENUMBER_TABLENAME = "TABLE_ALLCOMPILENUMBER";

    public static final String DB_100COMPILENUMBER_TABLENAME = "TABLE_100COMPILENUMBER";
    public static final String DB_10COMPILENUMBER_TABLENAME = "TABLE_10COMPILENUMBER";

    /**
     * DB 에는 QR당첨확인한 것과 모든당첨회차번호를 저장한다
     */
    public Database(Context _ctx)
    {
        mCtx = _ctx;
        OpenDB();
    }

    private void OpenDB()
    {
        try {
            mSqliteDB = mCtx.openOrCreateDatabase(mDbName, Context.MODE_PRIVATE, null);
            String Create = "CREATE TABLE IF NOT EXISTS " + DB_QRCHECK_TABLENAME + " (ID INTEGER primary key autoincrement," +
                    "url VARCHAR(200), result VARCHAR(2000)); ";
            mSqliteDB.execSQL(Create);
 
            Create = "CREATE TABLE IF NOT EXISTS " + DB_ALLCOMPILENUMBER_TABLENAME + "(ID INTEGER primary key autoincrement," +
                    "drwNo INTEGER,no1 VARCHAR(2),no2 VARCHAR(2),no3 VARCHAR(2),no4 VARCHAR(2),no5 VARCHAR(2),no6 VARCHAR(2)," +
                    "bonus VARCHAR(2)); ";
            mSqliteDB.execSQL(Create);

            Create = "CREATE TABLE IF NOT EXISTS " + DB_100COMPILENUMBER_TABLENAME + "(ID INTEGER primary key autoincrement," +
                    "drwNo INTEGER,no1 VARCHAR(2),no2 VARCHAR(2),no3 VARCHAR(2),no4 VARCHAR(2),no5 VARCHAR(2),no6 VARCHAR(2)," +
                    "bonus VARCHAR(2)); ";
            mSqliteDB.execSQL(Create);

            Create = "CREATE TABLE IF NOT EXISTS " + DB_10COMPILENUMBER_TABLENAME + "(ID INTEGER primary key autoincrement," +
                    "drwNo INTEGER,no1 VARCHAR(2),no2 VARCHAR(2),no3 VARCHAR(2),no4 VARCHAR(2),no5 VARCHAR(2),no6 VARCHAR(2)," +
                    "bonus VARCHAR(2)); ";
            mSqliteDB.execSQL(Create);


        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }

    }

    public void InsertQRCheckData(String _url, String _result)
    {
        try {
            String insert = "INSERT INTO " + DB_QRCHECK_TABLENAME +
                    " (url,result)" +
                    " values ('" + _url + "','" + _result + "');";
            mSqliteDB.execSQL(insert);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }
    }

    public void InsertPreviewData(int _drwNo, String _no1, String _no2, String _no3,
                                  String _no4, String _no5, String _no6, String _bonus)
    {
        try {
            String insert = "INSERT INTO " + DB_ALLCOMPILENUMBER_TABLENAME +
                    " (drwNo,no1,no2,no3,no4,no5,no6,bonus)" +
                    " values ('" + _drwNo + "','" + _no1 + "','" + _no2 + "','" + _no3 + "'," +
                    "'" + _no4 + "','" + _no5 + "','" + _no6 + "','" + _bonus + "');";
            mSqliteDB.execSQL(insert);

            InsertNumberSortingData(_no1, _no2, _no3, _no4, _no5, _no6, _bonus);

            InsertPreview100Data(_drwNo,_no1,_no2,_no3,_no4,_no5,_no6,_bonus);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }
    }

    public void InsertPreview100Data(int _drwNo, String _no1, String _no2, String _no3,
                                  String _no4, String _no5, String _no6, String _bonus)
    {
        try
        {
            DeleteCheckCount(DB_100COMPILENUMBER_TABLENAME, 100);
            String insert = "INSERT INTO " + DB_100COMPILENUMBER_TABLENAME +
                    " (drwNo,no1,no2,no3,no4,no5,no6,bonus)" +
                    " values ('"+ _drwNo + "','" + _no1 + "','" + _no2 + "','" + _no3 + "'," +
                    "'" + _no4 + "','" + _no5 + "','" + _no6 + "','" + _bonus +"');";
            mSqliteDB.execSQL(insert);

            InsertNumber100SortingData(_no1,_no2,_no3,_no4,_no5,_no6,_bonus);

            InsertPreview10Data(_drwNo,_no1,_no2,_no3,_no4,_no5,_no6,_bonus);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }

    }

    public void InsertPreview10Data(int _drwNo, String _no1, String _no2, String _no3,
                                     String _no4, String _no5, String _no6, String _bonus)
    {
        try
        {
            DeleteCheckCount(DB_10COMPILENUMBER_TABLENAME, 10);
            String insert = "INSERT INTO " + DB_10COMPILENUMBER_TABLENAME +
                    " (drwNo,no1,no2,no3,no4,no5,no6,bonus)" +
                    " values ('"+ _drwNo + "','" + _no1 + "','" + _no2 + "','" + _no3 + "'," +
                    "'" + _no4 + "','" + _no5 + "','" + _no6 + "','" + _bonus +"');";
            mSqliteDB.execSQL(insert);

            InsertNumber10SortingData(_no1,_no2,_no3,_no4,_no5,_no6,_bonus);
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }

    }


    public void InsertNumberSortingData(String _no1, String _no2, String _no3, String _no4,
                                        String _no5, String _no6, String _bonus)
    {
        int NO1 = 0;
        int NO2 = 0;
        int NO3 = 0;
        int NO4 = 0;
        int NO5 = 0;
        int NO6 = 0;
        int BONUS = 0;

        NO1 = PowerSDK.getIntPreference(mCtx,_no1);
        PowerSDK.setIntPreference(mCtx, _no1, NO1 + 1);

        NO2 = PowerSDK.getIntPreference(mCtx,_no2);
        PowerSDK.setIntPreference(mCtx, _no2, NO2 + 1);

        NO3 = PowerSDK.getIntPreference(mCtx,_no3);
        PowerSDK.setIntPreference(mCtx, _no3, NO3 + 1);

        NO4 = PowerSDK.getIntPreference(mCtx,_no4);
        PowerSDK.setIntPreference(mCtx, _no4, NO4 + 1);

        NO5 = PowerSDK.getIntPreference(mCtx,_no5);
        PowerSDK.setIntPreference(mCtx, _no5, NO5 + 1);

        NO6 = PowerSDK.getIntPreference(mCtx,_no6);
        PowerSDK.setIntPreference(mCtx, _no6, NO6 + 1);

        BONUS = PowerSDK.getIntPreference(mCtx,_bonus);
        PowerSDK.setIntPreference(mCtx, _bonus, BONUS + 1);

        Log.d(TAG,_no1 + " : " + (NO1+1) + ", " + _no2 + " : " + (NO2+1) + ", " + _no3 + " : " + (NO3+1) + ", " +
                _no4 + " : " + (NO4+1) + ", " + _no5 + " : " + (NO5+1) + ", " + _no6 + " : " + (NO6+1) + ", " + _bonus + " : " + (BONUS+1));
    }

    public void InsertNumber100SortingData(String _no1, String _no2, String _no3, String _no4,
                                        String _no5, String _no6, String _bonus)
    {
        int NO1 = 0;
        int NO2 = 0;
        int NO3 = 0;
        int NO4 = 0;
        int NO5 = 0;
        int NO6 = 0;
        int BONUS = 0;

        NO1 = PowerSDK.getIntPreference(mCtx,"100_" + _no1);
        PowerSDK.setIntPreference(mCtx, "100_" + _no1, NO1 + 1);

        NO2 = PowerSDK.getIntPreference(mCtx,"100_" + _no2);
        PowerSDK.setIntPreference(mCtx, "100_" + _no2, NO2 + 1);

        NO3 = PowerSDK.getIntPreference(mCtx,"100_" + _no3);
        PowerSDK.setIntPreference(mCtx, "100_" + _no3, NO3 + 1);

        NO4 = PowerSDK.getIntPreference(mCtx,"100_" + _no4);
        PowerSDK.setIntPreference(mCtx, "100_" + _no4, NO4 + 1);

        NO5 = PowerSDK.getIntPreference(mCtx,"100_" + _no5);
        PowerSDK.setIntPreference(mCtx, "100_" + _no5, NO5 + 1);

        NO6 = PowerSDK.getIntPreference(mCtx,"100_" + _no6);
        PowerSDK.setIntPreference(mCtx, "100_" + _no6, NO6 + 1);

        BONUS = PowerSDK.getIntPreference(mCtx,"100_" + _bonus);
        PowerSDK.setIntPreference(mCtx, "100_" + _bonus, BONUS + 1);

        Log.d(TAG,"100_" + _no1 + " : " + (NO1+1) + ", " + "100_" + _no2 + " : " + (NO2+1) + ", " + "100_" + _no3 + " : " + (NO3+1) + ", " +
                "100_" + _no4 + " : " + (NO4+1) + ", " + "100_" + _no5 + " : " + (NO5+1) + ", " + "100_" + _no6 + " : " + (NO6+1) + ", " + "100_" + _bonus + " : " + (BONUS+1));
    }

    public void InsertNumber10SortingData(String _no1, String _no2, String _no3, String _no4,
                                        String _no5, String _no6, String _bonus)
    {
        int NO1 = 0;
        int NO2 = 0;
        int NO3 = 0;
        int NO4 = 0;
        int NO5 = 0;
        int NO6 = 0;
        int BONUS = 0;

        NO1 = PowerSDK.getIntPreference(mCtx,"10_" + _no1);
        PowerSDK.setIntPreference(mCtx, "10_" + _no1, NO1 + 1);

        NO2 = PowerSDK.getIntPreference(mCtx,"10_" + _no2);
        PowerSDK.setIntPreference(mCtx, "10_" + _no2, NO2 + 1);

        NO3 = PowerSDK.getIntPreference(mCtx,"10_" + _no3);
        PowerSDK.setIntPreference(mCtx, "10_" + _no3, NO3 + 1);

        NO4 = PowerSDK.getIntPreference(mCtx,"10_" + _no4);
        PowerSDK.setIntPreference(mCtx, "10_" + _no4, NO4 + 1);

        NO5 = PowerSDK.getIntPreference(mCtx,"10_" + _no5);
        PowerSDK.setIntPreference(mCtx, "10_" + _no5, NO5 + 1);

        NO6 = PowerSDK.getIntPreference(mCtx,"10_" + _no6);
        PowerSDK.setIntPreference(mCtx, "10_" + _no6, NO6 + 1);

        BONUS = PowerSDK.getIntPreference(mCtx,"10_" + _bonus);
        PowerSDK.setIntPreference(mCtx, "10_" + _bonus, BONUS + 1);

        Log.d(TAG,"10_" + _no1 + " : " + (NO1+1) + ", " + "10_" + _no2 + " : " + (NO2+1) + ", " + "10_" + _no3 + " : " + (NO3+1) + ", " +
                "10_" + _no4 + " : " + (NO4+1) + ", " + "10_" + _no5 + " : " + (NO5+1) + ", " + "10_" +  _no6 + " : " + (NO6+1) + ", " + "10_" + _bonus + " : " + (BONUS+1));
    }

    public String[] SelectData(String TableName)
    {
        String selectQuery ="";
        ArrayList<String> tmp = new ArrayList<>();
        if(TableName.equals(DB_QRCHECK_TABLENAME))
        {
            selectQuery = "Select * from " + TableName ;
            Cursor c = mSqliteDB.rawQuery(selectQuery,null);
            while (c.moveToNext()) {
                int co1 = c.getColumnIndex("url");
                int co2 = c.getColumnIndex("result");
                String url = c.getString(co1);
                String result = c.getString(co2);
                String data = url + "^" + result;
                tmp.add(data);
            }
        }
        else if(TableName.equals(DB_ALLCOMPILENUMBER_TABLENAME)){
            selectQuery = "Select * from " + TableName + " ORDER BY drwNo DESC" + ";";
            Cursor c = mSqliteDB.rawQuery(selectQuery,null);
            while (c.moveToNext()) {
                int co1 = c.getColumnIndex("drwNo");
                int co2 = c.getColumnIndex("no1");
                int co3 = c.getColumnIndex("no2");
                int co4 = c.getColumnIndex("no3");
                int co5 = c.getColumnIndex("no4");
                int co6 = c.getColumnIndex("no5");
                int co7 = c.getColumnIndex("no6");
                int co8 = c.getColumnIndex("bonus");
                String drwNo = c.getString(co1);
                String no1 = c.getString(co2);
                String no2 = c.getString(co3);
                String no3 = c.getString(co4);
                String no4 = c.getString(co5);
                String no5 = c.getString(co6);
                String no6 = c.getString(co7);
                String bonus = c.getString(co8);

                String data = drwNo + "," + no1 + "," + no2 + "," + no3 + "," + no4 + "," + no5 + "," + no6 + "," + bonus;
                tmp.add(data);
            }
        }
        else if(TableName.equals(DB_100COMPILENUMBER_TABLENAME)){
            selectQuery = "Select * from " + TableName + " ORDER BY drwNo DESC" + ";";
            Cursor c = mSqliteDB.rawQuery(selectQuery,null);
            while (c.moveToNext()) {
                int co1 = c.getColumnIndex("drwNo");
                int co2 = c.getColumnIndex("no1");
                int co3 = c.getColumnIndex("no2");
                int co4 = c.getColumnIndex("no3");
                int co5 = c.getColumnIndex("no4");
                int co6 = c.getColumnIndex("no5");
                int co7 = c.getColumnIndex("no6");
                int co8 = c.getColumnIndex("bonus");
                String drwNo = c.getString(co1);
                String no1 = c.getString(co2);
                String no2 = c.getString(co3);
                String no3 = c.getString(co4);
                String no4 = c.getString(co5);
                String no5 = c.getString(co6);
                String no6 = c.getString(co7);
                String bonus = c.getString(co8);

                String data = drwNo + "," + no1 + "," + no2 + "," + no3 + "," + no4 + "," + no5 + "," + no6 + "," + bonus;
                tmp.add(data);
            }
        }
        else if(TableName.equals(DB_10COMPILENUMBER_TABLENAME)){
            selectQuery = "Select * from " + TableName + " ORDER BY drwNo DESC" + ";";
            Cursor c = mSqliteDB.rawQuery(selectQuery,null);
            while (c.moveToNext()) {
                int co1 = c.getColumnIndex("drwNo");
                int co2 = c.getColumnIndex("no1");
                int co3 = c.getColumnIndex("no2");
                int co4 = c.getColumnIndex("no3");
                int co5 = c.getColumnIndex("no4");
                int co6 = c.getColumnIndex("no5");
                int co7 = c.getColumnIndex("no6");
                int co8 = c.getColumnIndex("bonus");
                String drwNo = c.getString(co1);
                String no1 = c.getString(co2);
                String no2 = c.getString(co3);
                String no3 = c.getString(co4);
                String no4 = c.getString(co5);
                String no5 = c.getString(co6);
                String no6 = c.getString(co7);
                String bonus = c.getString(co8);

                String data = drwNo + "," + no1 + "," + no2 + "," + no3 + "," + no4 + "," + no5 + "," + no6 + "," + bonus;
                tmp.add(data);
            }
        }

        return tmp.toArray(new String[0]);
    }

    public void DeleteQRData(String _url)
    {
        //기존 데이터 삭제
        String delete = "DELETE FROM " + DB_QRCHECK_TABLENAME + " where url = '" + _url  + "';";
        mSqliteDB.execSQL(delete);
    }

    private void DeleteCheckCount(String _tableName, int MaxCount)
    {
        int ColumnsCount = 0;
        String selectQuery = "Select * from " + _tableName ;
        Cursor c = mSqliteDB.rawQuery(selectQuery,null);

        while (c.moveToNext())
        {
            ColumnsCount++;
        }

        if(ColumnsCount> (MaxCount-1))
        {
            String delete = "DELETE FROM " + _tableName + " where drwNo = (SELECT MIN(drwNo) FROM " + _tableName + ");";
            mSqliteDB.execSQL(delete);
        }
    }

}

