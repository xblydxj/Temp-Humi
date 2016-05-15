package com.temphumi.Data;

import android.os.Message;

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


    public Message getData(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if(200 == responseCode){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ArrayList<Data> Datas = pattrenResult(bufferedReader);
                Message msg = new Message();
                msg.obj = Datas;
                msg.what = MSG_RIGHT;
                return msg;
            }else{
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
    public ArrayList<Data> pattrenResult(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        String result = stringBuilder.toString().trim();
        Pattern pattern = Pattern.compile("<td>([0-9]{1,2})~~([0-9]{1,3})~~([0-9]{1,2})~~([0-9]{1,2})~~([0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})</td>");
        Matcher matcher = pattern.matcher(result);
        ArrayList<Data> arrayList = new ArrayList<>();
        while(matcher.find()) {
            Data data = new Data();
            data.soilTemperature = Integer.parseInt(matcher.group(1));
            data.soilHumidity = Integer.parseInt(matcher.group(2));
            data.airTemperature = Integer.parseInt(matcher.group(3));
            data.airHumidity = Integer.parseInt(matcher.group(4));
            data.currentTime = matcher.group(5);
            arrayList.add(data);
        }
        return arrayList;
    }
}
