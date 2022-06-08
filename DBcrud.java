package com.example.hanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBcrud extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = ".db"; // 데이터베이스 이름
    public static final String TABLE_NAME = ""; // 테이블 이름

    //테이블 항목
    public static final String col_1 = "Name";
    public static final String col_2 = "Size";
    public static final String col_3 = "Memo";
    public static final String col_4 = "";
    public static final String col_5 = "";
    public static final String col_6 = "Tag";

    public DBcrud(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME + "(NAME INTEGER PRIMARY KEY AUTOINCREMRNT, )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    // 데이터베이스 내용 삽입
    public boolean insertData(String name, String size, String memo, , , String Tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, name);
        contentValues.put(col_2, size);
        contentValues.put(col_3, memo);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    // 데이터베이스 읽기
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    // 데이터베이스 삭제
    public Integer deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Name = ?", new String[]{name});
    }

    // 데이터베이스 수정
    public boolean updateData(String name, String size, String memo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, name);
        contentValues.put(col_2, size);
        contentValues.put(col_3, memo);
        db.update(TABLE_NAME, contentValues, "Name = ?", new String[] {name});
        return true;
    }
}
