package com.example.locusreminder.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Reminder";


    private HashMap hp;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(

                "create table " + DATABASE_NAME + "(id TEXT,title TEXT,note TEXT,location TEXT,longitude double,latitude double,isDeleted double)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Reminder");
        onCreate(db);
    }




    public boolean insertReminderData(String id, String title, String note, String location, String longitude,String latitude,String isDeleted) {

        String sql = "INSERT INTO " + DATABASE_NAME + " (id,title,note,location,longitude,latitude,isDeleted) VALUES(?,?,?,?,?,?,?)";
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement insertStmt = db.compileStatement(sql);

            insertStmt.bindString(1, id);
            insertStmt.bindString(2, title);
            insertStmt.bindString(3, note);
            insertStmt.bindString(4, location);
            insertStmt.bindDouble(5, Double.parseDouble(longitude));
            insertStmt.bindDouble(6, Double.parseDouble(latitude));
            insertStmt.bindDouble(7,Double.parseDouble(isDeleted));

            insertStmt.executeInsert();
            db.close();

        return true;
    }

    public void updateReminderData(String id, String title, String note, String location, String longitude,String latitude,String isDeleted) {

        String sql = String.format("UPDATE %s SET title=%s,note=%s,location=%s,longitude=%f,latitude=%f,isDeleted=%f WHERE id=%s",DATABASE_NAME,title,note,location,longitude,latitude,isDeleted);

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement updateStmt = db.compileStatement(sql);
        updateStmt.executeInsert();
        db.close();

    }


    public List<ReminderData> getReminderData() {

        List<ReminderData> lstReminderData = new ArrayList<ReminderData>();
        ReminderData rem=null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+DATABASE_NAME,null);

        while(res.moveToNext()) {
            String id = res.getString(0);   //0 is the number of id column in your database table
            String title = res.getString(1);
            String note = res.getString(2);
            String location = res.getString(3);
            String longitude = res.getString(4);
            String latitude = res.getString(5);
            String isDeleted = res.getString(6);

           rem = new ReminderData(id,title,note,location,longitude,latitude,isDeleted);
            lstReminderData.add(rem);
        }
        return lstReminderData;
    }


    public void deleteDatabase()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+DATABASE_NAME);
       // return db.delete(facebook_info, "id" + "=" + title, null) > 0;
    }






}
