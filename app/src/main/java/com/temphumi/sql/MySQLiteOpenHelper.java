package com.temphumi.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context,int version) {
        super(context, "data.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table data(id int primary key, soilTemperature int, soilHumidity int, airTemperature int, airHumidity int, currentTime varchar(30))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
