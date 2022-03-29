package com.example.schoolscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class SQLDatabase extends SQLiteOpenHelper {
    String TableName;
    public SQLDatabase(@Nullable Context context
            , @Nullable String dataBaseName
            , @Nullable SQLiteDatabase.CursorFactory factory, int version, String TableName) {
        super(context, dataBaseName, factory, version);
        this.TableName = TableName;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLTable = "CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TaskName TEXT, " +
                "Subject TEXT," +
                "Type TEXT," +
                "Due TEXT," +
                "Details TEXT" +
                ");";
        db.execSQL(SQLTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + TableName;
        db.execSQL(SQL);

    }
    //check if table exists
    public void checkTable(){
        Cursor cursor = getWritableDatabase().rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() == 0)
                getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS " + TableName + "( " +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "TaskName TEXT, " +
                        "Subject TEXT," +
                        "Type TEXT," +
                        "Due TEXT," +
                        "Details TEXT" +
                        ");");
            cursor.close();
       }
    }

    //add data
    public void addData(String name,String sub,String type, String due, String elseInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TaskName", name);
        values.put("Subject", sub);
        values.put("Type", type);
        values.put("Due", due);
        values.put("Details", elseInfo);
        db.insert(TableName, null, values);
    }

    //show task titles
    public ArrayList<ArrayList<String>> showOne() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TableName, null);
        ArrayList<ArrayList<String>> arrayList = new ArrayList<>();
        while (c.moveToNext()) {
            ArrayList<String> list = new ArrayList<String>();
            list.add(c.getString(0));
            list.add(c.getString(1));
            list.add(c.getString(2));
            list.add(c.getString(3));
            list.add(c.getString(4));
            list.add(c.getString(5));
            //list.add(c.getString(1));
            arrayList.add(list);
        }
        return arrayList;
    }

    //modify
    public void modify(String id, String name, String subject, String type, String due, String detail) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" UPDATE " + TableName
                + " SET TaskName=" + "'" + name + "',"
                + "Subject=" + "'" + subject + "',"
                + "Type=" + "'" + type + "',"
                + "Due=" + "'" + due + "',"
                + "Details=" + "'" + detail + "'"
                + " WHERE _id=" + "'" + id + "'");
    }

    //delete
    public void delete(String id){
        SQLiteDatabase dbase = getWritableDatabase();
        dbase.delete(TableName,"_id = " + id,null);
    }
}