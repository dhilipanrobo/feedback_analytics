package com.example.sathya.bistro;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String TABLE_NAME = "question";
    static final String DATABASE_NAME = "Bistro";
    private static final int DATABASE_VERSION = 25;
    private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(sno INTEGER PRIMARY KEY AUTOINCREMENT,ques_id INTEGER(20)unique,answer VARCHAR(20),remark VARCHAR(20))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public long insert(String quesid, String ans, String remark) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("ques_id",quesid);
        content.put("answer",ans);
        content.put("remark",remark);
        return db.insert(TABLE_NAME,null,content);
    }

    public int deletertoken() {
        SQLiteDatabase arg0=this.getReadableDatabase();
        return arg0.delete(TABLE_NAME,null,null);

    }
}