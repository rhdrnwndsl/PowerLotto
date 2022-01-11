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
        }
        catch (SQLiteException ex)
        {
            Log.d(TAG,ex.toString());
        }

    }

    public void InsertQRCheckData(String _url, String _result)
    {
        String insert = "INSERT INTO " + DB_QRCHECK_TABLENAME +
                " (url,result)" +
                " values ('"+ _url + "','" + _result +"');";
        mSqliteDB.execSQL(insert);
    }

    public void InsertPreviewData(int _drwNo, String _no1, String _no2, String _no3,
                                  String _no4, String _no5, String _no6, String _bonus)
    {
        String insert = "INSERT INTO " + DB_ALLCOMPILENUMBER_TABLENAME +
                " (drwNo,no1,no2,no3,no4,no5,no6,bonus)" +
                " values ('"+ _drwNo + "','" + _no1 + "','" + _no2 + "','" + _no3 + "'," +
                "'" + _no4 + "','" + _no5 + "','" + _no6 + "','" + _bonus +"');";
        mSqliteDB.execSQL(insert);
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

        return tmp.toArray(new String[0]);
    }

    public void DeleteQRData(String _url)
    {
        //기존 데이터 삭제
        String delete = "DELETE FROM " + DB_QRCHECK_TABLENAME + " where url = '" + _url  + "';";
        mSqliteDB.execSQL(delete);
    }

}

