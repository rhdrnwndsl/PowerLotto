package com.jiw.powerlotto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    private static final String DATABASE_NAME = "lottoAlarm.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    /**
     * DB 관리 클래스
     */
    private class DatabaseHelper extends SQLiteOpenHelper {
        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 리스트 테이블 생성
            db.execSQL(getCreateTableList());

            // 상세 정보 테이블 생성
            db.execSQL(getCreateTableDetail());
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS list");
            db.execSQL("DROP TABLE IF EXISTS detail");

            // Create tables again
            onCreate(db);
        }

        public String getCreateTableList() {
            String sql = " create table list (";
            sql += " id integer primary key autoincrement";
            sql += ", url text not null"; // QR 코드 인식된 URL
            sql += ", price text null"; // 총 당첨금액
            sql += ", round int not null"; // 회차 정보
            sql += ", date char(10) not null"; // 추첨 날짜

            sql += ", is_check char(1) not null"; // 추첨 결과 확인하였는지 여부 0 : 미확인, 1 확인
            sql += ", num1 int null"; // 당첨번호 1
            sql += ", num2 int null"; // 당첨번호 2
            sql += ", num3 int null"; // 당첨번호 3
            sql += ", num4 int null"; // 당첨번호 4
            sql += ", num5 int null"; // 당첨번호 5
            sql += ", num6 int null"; // 당첨번호 6
            sql += ", plus int null"; // 추가 번호
            sql += "); ";

            return sql;
        }

        public String getCreateTableDetail() {
            String sql = " create table detail (";

            sql += " id integer primary key autoincrement";
            sql += ", list_idx int not null"; // 리스트 인덱스
            sql += ", seq char(1) not null"; // 순번
            sql += ", result varchar(50) null"; // 당첨결과
            sql += ", num1 int not null"; // 번호 1
            sql += ", num2 int not null"; // 번호 2
            sql += ", num3 int not null"; // 번호 3
            sql += ", num4 int not null"; // 번호 4
            sql += ", num5 int not null"; // 번호 5
            sql += ", num6 int not null"; // 번호 6
            sql += "); ";

            return sql;
        }
    }

    // 생성자
    public Database(Context context){
        this.mCtx = context;
    }

    public Database open() throws SQLException {
        if(mDB == null || !mDB.isOpen()) {
            mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
            mDB = mDBHelper.getWritableDatabase();
        }
        return this;
    }

    private void cursorClose(Cursor cursor) {
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public void close(){
        mDB.close();
    }

//    /** * 데이터 입력 *
//     * @param listModel */
//    public void insertData(ListModel listModel) {
//        open();
//        ContentValues values = new ContentValues();
//        values.put("url", listModel.getUrl());
//        values.put("price", listModel.getPrice());
//        values.put("round", listModel.getRound());
//        values.put("date", listModel.getDate());
//        values.put("is_check", listModel.isCheck());
//        if(listModel.isCheck().equals("1")) {
//            for(int i = 0; i<6; i++) {
//                values.put("num" + (i + 1), String.valueOf(listModel.getAnswerNumberList().get(i)));
//            }
//            values.put("plus", String.valueOf(listModel.getAnswerNumberList().get(6)));
//        }
//
//        // 새로운 Row 추가
//        long id = mDB.insert("list", null, values);
//        listModel.setId(id);
//        close();
//
//        // 상세 정보 입력
//        insertDetailData(listModel);
//    }

//    /** * 상세 정보 입력 *
//     * @param listModel */
//    public void insertDetailData(ListModel listModel) {
//        open();
//        for (DetailModel detailModel : listModel.getDetailModelArrayList()) {
//            ContentValues values = new ContentValues();
//            values.put("list_idx", listModel.getId());
//            values.put("seq", detailModel.getSeq());
//            values.put("result", detailModel.getResult());
//            for(int i=0; i<6; i++) {
//                values.put("num" + (i + 1), String.valueOf(detailModel.getNumberList().get(i)));
//            }
//            // 새로운 Row 추가
//            long id = mDB.insert("detail", null, values);
//            detailModel.setId(id);
//        }
//        close();
//    }


//    /** * 데이터 수정
//     * * @param listModel */
//    public void updateData(ListModel listModel) {
//        ContentValues values = new ContentValues();
//        values.put("url", listModel.getUrl());
//        values.put("price", listModel.getPrice());
//        values.put("round", listModel.getRound());
//        values.put("date", listModel.getDate());
//        values.put("is_check", listModel.isCheck());
//        if(listModel.isCheck().equals("1")) {
//            for(int i=0; i<6; i++) {
//                values.put("num" + (i + 1), listModel.getAnswerNumberList().get(i));
//            }
//            values.put("plus", listModel.getAnswerNumberList().get(6));
//        }
//        open();
//        // 데이터 변경
//        mDB.update("list", values, "id" + " = ?", new String[] {
//                String.valueOf(listModel.getId())
//        });
//        close();
//
//        // 상세 정보 삭제
//        deleteDetailData(listModel);
//        // 상세 정보 입력
//        insertDetailData(listModel);
//    }


    /** * 해당 항목 삭제
     * @param listModel */
    public void deleteData(ListModel listModel) {
        open();
        mDB.delete("list", "id" + " = ?", new String[] {
                String.valueOf(listModel.getId())
        });
        close();
        deleteDetailData(listModel);
    }

    /** * 해당 항목의 상세 데이터들 일괄 삭제
     * @param listModel */
    public void deleteDetailData(ListModel listModel) {
        open();
        mDB.delete("detail", "list_idx" + " = ?", new String[] {
                String.valueOf(listModel.getId())
        });
        close();
    }

//    /** * 중복되는 url 주소 있는지 수량 확인
//     * @param url
//     * @return */
//    public int selectCount(String url) {
//        open();
//        int count = 0;
//        String selectQuery = " SELECT count(*) as cnt FROM list WHERE url = ? ";
//        Cursor cursor = mDB.rawQuery(selectQuery, new String[] { url });
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                count = cursor.getInt(cursor.getColumnIndex("cnt"));
//            }
//            while (cursor.moveToNext());
//        }
//        cursorClose(cursor);
//        close();
//        return count;
//    }
//
//    /** * 전체 항목 리스트 조회
//     * @return */
//    public ArrayList<ListModel> selectList() {
//        open();
//        ArrayList<ListModel> list = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = " SELECT * FROM list ORDER BY round DESC, id DESC ";
//        Cursor cursor = mDB.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ListModel listModel = new ListModel();
//                listModel.setId(cursor.getLong(cursor.getColumnIndex("id")));
//                listModel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
//                listModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
//                listModel.setRound(cursor.getInt(cursor.getColumnIndex("round")));
//                listModel.setPrice(cursor.getString(cursor.getColumnIndex("price")));
//                for(int i=0; i<6; i++) {
//                    listModel.getAnswerNumberList().add(cursor.getInt(cursor.getColumnIndex("num" + (i + 1))));
//                }
//                listModel.getAnswerNumberList().add(cursor.getInt(cursor.getColumnIndex("plus")));
//                // 상세 항목 조회
//                listModel.setDetailModelArrayList(selectDetailList(listModel));
//                list.add(listModel);
//            }
//            while (cursor.moveToNext());
//        }
//        cursorClose(cursor);
//        close();
//        return list;
//    }
//
//    /** * 상세 항목 리스트 조회
//     * @param listModel
//     * @return */
//    public ArrayList<DetailModel> selectDetailList(ListModel listModel) {
//        ArrayList<DetailModel> list = new ArrayList<>();
//        String selectQuery = " SELECT * FROM detail WHERE list_idx = ? ORDER BY seq ";
//        Cursor cursor = mDB.rawQuery(selectQuery, new String[] {String.valueOf(listModel.getId())});
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                DetailModel detailModel = new DetailModel();
//                detailModel.setId(cursor.getLong(cursor.getColumnIndex("id")));
//                detailModel.setSeq(cursor.getString(cursor.getColumnIndex("seq")));
//                detailModel.setResult(cursor.getString(cursor.getColumnIndex("result")));
//                for(int i=0; i<6; i++) {
//                    detailModel.getNumberList().add(cursor.getInt(cursor.getColumnIndex("num" + (i + 1))));
//                }
//                list.add(detailModel);
//            }
//            while (cursor.moveToNext());
//        }
//        cursorClose(cursor);
//        return list;
//    }
//
//    /** * 당첨 조회 확인해야되는 리스트 조회
//     * @return */
//    public ArrayList<ListModel> selectNeedCheckList() {
//        open();
//        ArrayList<ListModel> list = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = " SELECT * FROM list WHERE is_check = '0' AND substr(date, 0, 10) < ? ORDER BY round DESC, id DESC ";
//        String targetTime = CommonUtil.getAlarmTimeToString("yyyy-MM-dd");
//        Cursor cursor = mDB.rawQuery(selectQuery, new String[] { targetTime });
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ListModel listModel = new ListModel();
//                listModel.setId(cursor.getLong(cursor.getColumnIndex("id")));
//                listModel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
//                listModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
//                listModel.setRound(cursor.getInt(cursor.getColumnIndex("round")));
//                listModel.setPrice(cursor.getString(cursor.getColumnIndex("price")));
//                for(int i=0; i<6; i++) {
//                    listModel.getAnswerNumberList().add(cursor.getInt(cursor.getColumnIndex("num" + (i + 1))));
//                }
//                listModel.getAnswerNumberList().add(cursor.getInt(cursor.getColumnIndex("plus")));
//                // 상세 항목 조회
//                listModel.setDetailModelArrayList(selectDetailList(listModel));
//                list.add(listModel);
//            }
//            while (cursor.moveToNext());
//        }
//        cursorClose(cursor);
//        close();
//        return list;
//    }
//
//    public long selectTotalTodayPrice() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        open();
//        ArrayList<ListModel> list = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = " SELECT price FROM list WHERE is_check = '1' AND price IS NOT NULL AND substr(date, 0, 10) = ? ";
//        Cursor cursor = mDB.rawQuery(selectQuery, new String[] { simpleDateFormat.format(new Date()) });
//        long result = 0;
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                long price = Long.parseLong(cursor
//                        .getString(cursor.getColumnIndex("price"))
//                        .replace(",", "")
//                        .replace("원", ""));
//                result += price;
//            }
//            while (cursor.moveToNext());
//        }
//        cursorClose(cursor);
//        close();
//        return result;
//    }

}

