package com.temphumi.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper_New extends SQLiteOpenHelper {

	public MySQLiteOpenHelper_New(Context context,int version) {
		super(context, "user_name.db", null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table user_name(name varchar(30) primary key,password varchar(30))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
