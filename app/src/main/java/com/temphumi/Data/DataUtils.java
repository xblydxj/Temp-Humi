package com.temphumi.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;

import com.temphumi.sql.MySQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataUtils {
    private static final int MSG_RIGHT = 1;
    private static final int MSG_ERROR = 0;
    private static final int VERSION = 1;

    private static int id = 0;

    public Message getData(String path, Context context) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (200 == responseCode) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ArrayList<Data> Datas = pattrenResult(bufferedReader, context);
                Message msg = new Message();
                msg.obj = Datas;
                msg.what = MSG_RIGHT;
                return msg;
            } else {
                Message msg = Message.obtain();
                msg.obj = responseCode;
                msg.what = MSG_ERROR;
                return msg;
            }
        } catch (IOException e) {
            Message msg = Message.obtain();
            msg.obj = e;
            msg.what = MSG_ERROR;
            return msg;
        }
    }

    public ArrayList<Data> pattrenResult(BufferedReader bufferedReader, Context context) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String result = stringBuilder.toString().trim();
        Pattern pattern = Pattern.compile("<td>([0-9]{1,2})~~([0-9]{1,3})~~([0-9]{1,2})~~([0-9]{1,2})~~([0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})</td>");
        Matcher matcher = pattern.matcher(result);
        ArrayList<Data> arrayList = new ArrayList<>();
        while (matcher.find()) {
            Data data =  new Data();
            data.soilTemperature = Integer.parseInt(matcher.group(1));
            data.soilHumidity = Integer.parseInt(matcher.group(2));
            data.airTemperature = Integer.parseInt(matcher.group(3));
            data.airHumidity = Integer.parseInt(matcher.group(4));
            data.currentTime = matcher.group(5);
            arrayList.add(data);
            recordData2DB(data, context);
        }
        return arrayList;
    }

    public void recordData2DB(Data data, Context context) {
        MySQLiteOpenHelper mysqliteOpenHelper = new MySQLiteOpenHelper(context, VERSION);
        SQLiteDatabase database = mysqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        id++;
        values.put("id", id);
        values.put("soilTemperature", data.soilTemperature);
        values.put("soilHumidity", data.soilHumidity);
        values.put("airTemperature", data.airTemperature);
        values.put("airHumidity", data.airHumidity);
        values.put("currentTime", data.currentTime);
        database.insert("data", null, values);
        database.close();
    }
}
