package com.temphumi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.temphumi.Data.Data;

import java.util.ArrayList;


public class MyAdapter extends BaseAdapter{
    private ArrayList<Data> datas;
    public MyAdapter(ArrayList<Data> arrayList){
        this.datas = arrayList;
    }

    @Override
    public int getCount(){
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = View.inflate(parent.getContext(), R.layout.item, null);
        }else{
            view = convertView;
        }

        TextView textClock =  (TextView) view.findViewById(R.id.Itemclock);
        TextView textTemp = (TextView) view.findViewById(R.id.ItemTextTemp);
        TextView textHumi = (TextView) view.findViewById(R.id.ItemTextHumi);
        Data data = datas.get(position);
        textClock.setText("当前时间：" + data.currentTime);
        textTemp.setText("空气温度为：" + data.airTemperature + " ，土壤温度为：" + data.airTemperature);
        textHumi.setText("空气湿度为：" + data.airHumidity + " ，土壤湿度为：" + data.soilHumidity);
        return view;
    }
}

