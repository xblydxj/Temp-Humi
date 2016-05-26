package com.temphumi;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.temphumi.sql.MySQLiteOpenHelper;

public class RecordActivity extends Activity {

    private static final int VERSION = 1;
    private EditText searchFor;
    private TextView result;
    private String resultStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        searchFor = (EditText) findViewById(R.id.searchFor);
        result = (TextView) findViewById(R.id.result);
    }

    public void search(View view) {

        if (TextUtils.isEmpty(searchFor.getText())) {
            Toast.makeText(this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String idstr = searchFor.getText().toString().trim();
                int id = Integer.parseInt(idstr);
                MySQLiteOpenHelper queryDataDB = new MySQLiteOpenHelper(this, VERSION);
                SQLiteDatabase database = queryDataDB.getWritableDatabase();
                Cursor cursor = database.query("data", null, "id=?", new String[]{idstr}, null, null, null);

                while (cursor.moveToNext()) {
                    String currentTime = cursor.getString(cursor.getColumnIndex("currentTime"));
                    int soilTemperature = cursor.getInt(cursor.getColumnIndex("soilTemperature"));
                    int soilHumidity = cursor.getInt(cursor.getColumnIndex("soilHumidity"));
                    int airTemperature = cursor.getInt(cursor.getColumnIndex("airTemperature"));
                    int airHumidity = cursor.getInt(cursor.getColumnIndex("airHumidity"));
                    resultStr = id + ":    \r\n 搜索结果为  " + currentTime
                            + ": \r\n当前空气温度为：" + airTemperature
                            + ", \r\n当前空气湿度为：" + airHumidity
                            + ", \r\n当前土壤温度为：" + soilTemperature
                            + ", \r\n当前土壤湿度为：" + soilHumidity
                            + "\r\n";
                }
                if ("".equals(resultStr)) {
                    result.setText("无搜索结果");
                    Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    return;
                }
                result.setText(resultStr);
                cursor.close();
                database.close();
            }catch (Exception e){
                result.setText("无搜索结果");
                Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
