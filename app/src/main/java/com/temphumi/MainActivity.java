package com.temphumi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.temphumi.Data.Data;
import com.temphumi.Data.DataUtils;
import com.temphumi.charview.CharView;

import java.util.ArrayList;


public class MainActivity extends Activity{

    private static final int MSG_ERROR = 0;
    private static final int MSG_RIGHT = 1;
    private ListView listView;
    private String path = "http://15054375.nat123.net:26169/";
//    private String path = "http://10.0.3.2/";
    private ArrayList<Data> datas = null;


    DataUtils dataUtils;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MSG_RIGHT:
                    datas = (ArrayList<Data>) msg.obj;
                    updateUI(datas);

                    Toast.makeText(MainActivity.this, "刷新成功",  Toast.LENGTH_SHORT).show();
                    break;
                case MSG_ERROR:
                    Toast.makeText(MainActivity.this, "访问网络失败  " + msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
    }
    private void updateUI(final ArrayList<Data> datas) {
        MyAdapter adapter = new MyAdapter(datas);
        listView.setAdapter(adapter);
    }
    public void load(View view){
        new Thread(){
            @Override
            public void run(){
                try {
                    dataUtils = new DataUtils();
                    Message msg = dataUtils.getData(path,MainActivity.this);
                    handler.sendMessage(msg);
                }catch (Exception e){
                    Message msg = Message.obtain();
                    msg.what = MSG_ERROR;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    public int[] AirData2Integer (){
        if(datas != null) {
            int[] airTempInt = new int[datas.size()+1];
            airTempInt[0] = -1;
            int i = 1;
            for (Data data : datas) {
                airTempInt[i] = data.airTemperature;
                i++;
            }
            return airTempInt;
        }else{
            Toast.makeText(this, "数据为空" , Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public int[] SoilData2Integer (){
        if(datas != null) {
            int[] soilTempInt = new int[datas.size()+1];
            soilTempInt[0] = -1;
            int i = 1;
            for (Data data : datas) {
                soilTempInt[i] = data.soilTemperature;
                i++;
            }
            return soilTempInt;
        }else{
            Toast.makeText(this, "数据为空" , Toast.LENGTH_SHORT).show();
            return null;
        }
    }



    public void charView(View view){
        Intent intent = new Intent(this, CharView.class);
        intent.putExtra("AirTemperatureData", AirData2Integer());
        intent.putExtra("SoilTemperatureData", SoilData2Integer());
        startActivity(intent);
    }


    public void record(View view){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
}




















/*
public class MainActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> data;
    private ListView listView;
    */
/*链接路径*//*

    final String THURL = "http://localhost:80/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        ThreadStart();

    }

    public void onClickLineDraw(View view){
        Intent lineDraw = new Intent();
        lineDraw.setClass(this,LineDraw.class);
        startActivity(lineDraw);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == -1){
                Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
            }else{
                initListview();
            }
        }
    };

    private void initListview() {
        ThreadStart();
        */
/*定义一个动态数组,调用方法获取list集合,即需要显示的数据*//*

        ArrayList<HashMap<String, Object>> listItem = data;
        */
/*创建适配器*//*

        SimpleAdapter simpleAdapter = new SimpleAdapter(this
                */
/*需要绑定的数据*//*

                ,listItem
                */
/*每一行的布局*//*

                ,R.layout.item
                */
/*动态数组中的数据源的键对应到定义布局的View中*//*

                ,new String[] {"ItemImage","ItemTitle","ItemText"}
                */
/*分别显示的控件*//*

                ,new int[] {R.id.ItemImage, R.id.ItemTitle,R.id.ItemText}
        );
        */
/*为ListView绑定适配器*//*

        listView.setAdapter(simpleAdapter);

       */
/* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            *//*
*/
/*创建点击监听*//*
*/
/*
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                *//*
*/
/*设置标题栏显示点击的行*//*
*/
/*
                setTitle(R.id.ItemTitle + ":" + R.id.ItemText);
            }
        });*//*

    }

    private void ThreadStart(){
        new Thread(){
            public void run(){
                try{
                    data = getData();
                    Message msg = Message.obtain();
                    msg.what = data.size();
                    msg.obj=data;
                    handler.sendMessage(msg);
                    Thread.sleep(5000);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    // throw new RuntimeException("数据获取失败");
                }
            }
        }.start();
    }


    */
/**
     * 获取并分析数据
     * @return 数据的集合
     * *//*

    private ArrayList<HashMap<String, Object>> getData() {
        */
/*创建存放数据的集合*//*

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();

        */
/*创建上一个温湿度值字符串*//*

        String nearTemp = "";
        String nearHumi = "";
        */
/*每次listView显示数量为15个*//*


            */
/*创建链接,获取链接中的数据*//*

        String THResult = http_get(THURL);
            */
/*正则匹配温湿度值*//*

        Pattern pattern = Pattern.compile("<td>(.)~~(.)~~(.)~~(.)~~(.)</td>");
        Matcher matcher = pattern.matcher(THResult);
            */
/*创建集合存储当前所有数据*//*

        ArrayList<String> arrayList = new ArrayList<>();
            */
/*添加数据*//*

        while(matcher.find()) {
            String s = matcher.group(1)+","+matcher.group(2);
            arrayList.add(s);
        }
            */
/*遍历集合进行分割存储*//*

        for(String TH : arrayList) {
                */
/*创建map集合存放控件与温湿度值的集合*//*

            HashMap<String, Object> map = new HashMap<>();
            String[] THs = TH.split(",");
            String nowTemp = THs[0];
            String nowHumi = THs[1];

            /*/
/*当首次首次进行比较时即第一行数据将直接显示相等图片*//*
*/
/*
          //  if ("".equals(nearTemp) && "".equals(nearHumi)) {
                map.put("ItemImage", R.drawable.nn);
           */
/* }else {
                //                /比较现在与上一次的值,显示相对应的不同的图片
                int t = Integer.parseInt(nowTemp) - Integer.parseInt(nearTemp);
                int h = Integer.parseInt(nowHumi) - Integer.parseInt(nearHumi);

                if (t > 0 & h > 0) {
                    map.put("ItemImage", R.drawable.uu);
                } else if (t > 0 & h < 0) {
                    map.put("ItemImage", R.drawable.ud);
                } else if (t < 0 & h < 0) {
                    map.put("ItemImage", R.drawable.dd);
                } else if (t < 0 & h > 0) {
                    map.put("ItemImage", R.drawable.du);
                } else if (t == 0 & h == 0) {
                    map.put("ItemImage", R.drawable.nn);
                } else if (t == 0 & h > 0) {
                    map.put("ItemImage", R.drawable.nu);
                } else if (t == 0 & h < 0) {
                    map.put("ItemImage", R.drawable.nd);
                } else if (t < 0 & h == 0) {
                    map.put("ItemImage", R.drawable.dn);
                } else {
                    map.put("ItemImage", R.drawable.un);
                }
            }*//*

                */
/*创建显示的温湿度字符串*//*


            String data = "当前温度为:" + nowTemp +
                    " ,湿度为:" + nowHumi;
                */
/*本次赋值于上一次,用于下一次比较*//*

            nearTemp = nowTemp;
            nearHumi = nowHumi;

                */
/*添加本次标题,即当前时间值*//*

            String time = "12:12:33";
            map.put("ItemTitle", time);
                */
/*添加本次数据*//*

            map.put("ItemText", data);
                */
/*将本次数据的map集合存入*//*

            listItem.add(map);
        }
        return listItem;
    }

    */
/**
     *创建连接,获取数据
     * @return 总数据字符串
     * *//*

    private String http_get(String url){
        */
/*定义数据字符串以及字符读取流*//*

        String result = "";
        BufferedReader in = null;
        */
/*尝试创建链接并获取数据*//*

        try{
            */
/*创建链接对象*//*

            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            */
/*创建开启连接的对象*//*

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            */
/*连接*//*

            int code = conn.getResponseCode();
            if(code == 200){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                */
/*将链接内容存入result*//*

                    result += line;
                }
            }
            */
/*字符流读取链接内容*//*

        } catch (Exception e){
            */
/*当上述步骤出现异常,将显示toast,并抛出运行时异常*//*

            Toast.makeText(MainActivity.this, "链接失败", Toast.LENGTH_SHORT).show();
            e.getStackTrace();
            //throw new RuntimeException("链接错误");
        }finally{
            try{
                */
/*尝试关闭流,为空时不需要关闭,留作下次用*//*

                if(in != null){
                    in.close();
                }
            }catch(Exception e2){
                */
/*关闭流失败时,显示toast,并抛出RuntimeException*//*

                Toast.makeText(MainActivity.this, "关闭流失败", Toast.LENGTH_SHORT).show();
                throw new RuntimeException("关闭流失败");
            }
        }
        */
/*返回链接中的数据*//*

        return result;
    }
}
*/
