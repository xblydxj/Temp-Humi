package com.temphumi;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.temphumi.sql.MySQLiteOpenHelper_New;

public class Signin extends Activity{
    private static final int VERSION = 1;
    private EditText et_name;
    private EditText et_password;
    private EditText et_newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
    }

    public void register(View view) {
        //获得数据
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String newpassword = et_newpassword.getText().toString().trim();

        //判断用户名或密码是否为空
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(password)||TextUtils.isEmpty(newpassword)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            //判断密码和确认密码是否一致
            if(!password.equals(newpassword)) {
                Toast.makeText(this, "密码不一致,请重新输入密码", Toast.LENGTH_SHORT).show();
                et_password.setText("");
                et_newpassword.setText("");
            }else {
                //获取MySQLiteOpenHelper_New对象
                MySQLiteOpenHelper_New mysqlliteOpenHelper = new MySQLiteOpenHelper_New(this, VERSION);
                //创建数据库对象
                SQLiteDatabase database = mysqlliteOpenHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name",name);
                values.put("password",password);
                database.insert("user_name", null, values);
                database.close();
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

                //跳转到主界面
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }

    }
}