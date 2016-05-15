package com.temphumi.charview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.temphumi.R;
import com.temphumi.charview.lineCharData.Common;
import com.temphumi.charview.lineCharData.MyData;
import com.temphumi.charview.lineCharData.XY;
import com.temphumi.charview.lineCharView.AxisXView;
import com.temphumi.charview.lineCharView.AxisYView_NormalType;
import com.temphumi.charview.lineCharView.LineView;
import com.temphumi.charview.lineCharView.TitleView;

import java.util.ArrayList;

public class CharView extends Activity{


    int[] airTempDatas;
    int[] soilTempDatas;
    private LinearLayout title_layout = null;
    private LinearLayout axisXLayout = null;
    private LinearLayout axisYLayout = null;
    private LinearLayout threndLine_Layout = null;

    private TitleView titleView;
    private LineView lineView;
    private AxisYView_NormalType axisY_2;
    private AxisXView axisX;

    private XY xy = new XY();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_linedraw);
        Intent intent = getIntent();
        airTempDatas = intent.getIntArrayExtra("AirTemperatureData");
        soilTempDatas = intent.getIntArrayExtra("SoilTemperatureData");

        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        Common.screenWidth = mDisplayMetrics.widthPixels * 4/6;
        Common.screenHeight = mDisplayMetrics.heightPixels /3;

        //设置图区宽高、内容宽高
        Common.layoutWidth = Common.screenWidth * 8/9;
        Common.layoutHeight = Common.screenHeight ;
        Common.viewWidth = Common.screenWidth * 8/9;
        Common.viewHeight = Common.screenHeight * 4/3;

        init();

        setTitle();
        setKey();
        setAxis();
        setData();
        addView();
    }


    private void init() {
        title_layout = (LinearLayout) findViewById(R.id.titleView);
        axisXLayout = (LinearLayout) findViewById(R.id.axisX);
        RelativeLayout.LayoutParams xParams = (RelativeLayout.LayoutParams) axisXLayout.getLayoutParams();
        xParams.height = Common.layoutHeight;
        xParams.width = Common.layoutWidth;
        xParams.setMargins(xParams.leftMargin+Common.YWidth, xParams.topMargin, xParams.rightMargin, xParams.bottomMargin);
        axisXLayout.setLayoutParams(xParams);

        axisYLayout = (LinearLayout) findViewById(R.id.axisY);
        RelativeLayout.LayoutParams yParams = (RelativeLayout.LayoutParams) axisYLayout.getLayoutParams();
        yParams.height = Common.layoutHeight;
        yParams.setMargins(yParams.leftMargin, yParams.topMargin, yParams.rightMargin, yParams.bottomMargin + Common.XHeight);
        axisYLayout.setLayoutParams(yParams);

        threndLine_Layout = (LinearLayout) findViewById(R.id.thrend_line);
        RelativeLayout.LayoutParams hParams = (RelativeLayout.LayoutParams) threndLine_Layout.getLayoutParams();
        hParams.height = Common.layoutHeight;
        hParams.width = Common.layoutWidth;
        hParams.setMargins(hParams.leftMargin+Common.YWidth, hParams.topMargin, hParams.rightMargin, hParams.bottomMargin + Common.XHeight);
        threndLine_Layout.setLayoutParams(hParams);



        //实例化View
        axisY_2 = new AxisYView_NormalType(this);
        axisX = new AxisXView(this);
        lineView = new LineView(this);
        titleView = new TitleView(this);

    }
    private void setData() {
        MyData data1 = new MyData();
        data1.setName("Air Temperature");
        data1.setData(airTempDatas);
        data1.setColor(0xff8d77ea);

        MyData data2 = new MyData();
		data2.setName("Soil Temperature");
		data2.setData(soilTempDatas);

		data2.setColor(0xff43ce17);
        Common.DataSeries = new ArrayList<MyData>();
        Common.DataSeries.add(data1);
        Common.DataSeries.add(data2);
    }

    private void setTitle(){
        Common.title = "空气土壤温湿度";
        Common.titleX = 160;
        Common.titleY = 55;
        Common.titleColor = Color.RED;
    }

    private void setAxis(){
        //设置轴参数
        Common.xScaleArray = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
		Common.xScaleColor = Color.YELLOW;

        //yScaleArray需要比levelName和color多出一个数
        Common.yScaleArray = new int[]{0,10,20,30,40,50,60};
        Common.levelName = new String[]{"低温","低温","较低","正常","较高","高温"};
        Common.yScaleColors = new int[]{0xff00ff00,0xffffff00,0xffffa500,0xffff4500,0xffdc143c,0xffa52a2a};
    }

    private void setKey(){
        //设置图例参数
        Common.keyWidth = 30;
        Common.keyHeight = 10;
        Common.keyToLeft = 250;
        Common.keyToTop = 10;
        Common.keyToNext = 50;
        Common.keyTextPadding = 5;
    }


    private void addView(){
        int width = 0;
        width = Common.viewWidth;

        //设定初始定位Y坐标
        xy.y = Common.viewHeight - Common.layoutHeight;

        lineView.initValue(width, Common.viewHeight, true);//传入宽、高、是否在折线图上显示数据
        lineView.scrollTo(0, xy.y);

        axisY_2.initValue(Common.viewHeight);//传入高度
        axisY_2.scrollTo(0, xy.y);

        axisX.initValue(width, Common.viewHeight);//传入高度
        axisX.scrollTo(0, xy.y);

        axisYLayout.removeAllViews();
        axisYLayout.addView(axisY_2);

        axisXLayout.removeAllViews();
        axisXLayout.addView(axisX);

        threndLine_Layout.removeAllViews();
        threndLine_Layout.addView(lineView);

        title_layout.removeAllViews();
        title_layout.addView(titleView);
    }
}













