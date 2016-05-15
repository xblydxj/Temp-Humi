package com.temphumi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.temphumi.sql.MySQLiteOpenHelper_New;


/**
 * 登录首页界面
 */
public class LoginActivity extends Activity {

    private static final int VERSION = 1;
    private EditText et_name;
    private EditText et_password;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_name = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
    }

    // 登录
    public void onClickLogin(View view) {
        // 获得用户名和密码
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        // 判断用户名或密码是否为空
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        } else {
            // 获取数据库
            MySQLiteOpenHelper_New mySQLiteOpenHelper = new MySQLiteOpenHelper_New(
                    this, VERSION);
            SQLiteDatabase database = mySQLiteOpenHelper.getWritableDatabase();
            // 遍历数据库
            Cursor cursor = database.query("user_name", null, null, null, null,
                    null, null);
            while (cursor.moveToNext()) {
                String name_cursor = cursor.getString(cursor.getColumnIndex("name"));
                String password_cursor = cursor.getString(cursor.getColumnIndex("password"));
                if (name.equals(name_cursor) && password.equals(password_cursor)) {
                    flag = 1;
                }
            }
            cursor.close();
            database.close();
            // 判断状态码
            if (flag == 1) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                // 跳转页面
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "用户名或密码错误,请重新登录", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        flag = 0;
    }

    // 注册
    public void onClickSignin(View view) {
        // 跳转页面
        Intent intent = new Intent(this, Signin.class);
        startActivity(intent);
    }
}
