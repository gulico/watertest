package com.example.wxy.watertest10.Model;

import android.database.Cursor;
import android.util.Log;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by WXY on 2017/11/26.
 */

public class ShowdetailDataModel implements IShowdetailDataModel{
    public List<WaterQualityDataBean> loadData(){

        new Thread(new Runnable(){
            @Override
            public void run() {
                Date d = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("当前时间1：" + sdf1.format(d));
                List<WaterQualityDataBean> waterQualityDataBeens = new ArrayList<WaterQualityDataBean>();
                Cursor c =  DataSupport.findBySQL("select * from WaterQualityDataBean ");
                if(c.moveToFirst()){
                    do{
                        WaterQualityDataBean waterQualityDataBean = null;

                        //c.getString(3);
                        Log.d(TAG, "run: "+c.getString(2));
                    }while (c.moveToNext());
                }
                c.close();
                Date d2 = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("当前时间2：" + sdf.format(d));
            }
        }).start();

        return null;
    }
}
