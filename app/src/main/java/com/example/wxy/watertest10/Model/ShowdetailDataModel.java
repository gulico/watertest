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

public class ShowdetailDataModel implements IShowdetailDataModel {
    public List<WaterQualityDataBean> loadData() {

        Date d = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间1：" + sdf1.format(d));
        List<WaterQualityDataBean> waterQualityDataBeens = new ArrayList<WaterQualityDataBean>();
        Cursor c = DataSupport.findBySQL("select * from WaterQualityDataBean ");
        int i=0;
        if (c.moveToFirst()) {
            do {
                //Log.d(TAG, "loadData: "+c.getString(2));
                //i++;
                //Log.d(TAG, "loadData: "+i);
                WaterQualityDataBean waterQualityDataBean = new WaterQualityDataBean();
                waterQualityDataBean.setTime(c.getString(2));
                waterQualityDataBean.setPh(c.getDouble(3));
                waterQualityDataBean.setConductivity(c.getDouble(4));
                waterQualityDataBean.setWater_temperature(c.getDouble(5));
                waterQualityDataBean.setAmmonia_nitrogen(c.getDouble(6));
                waterQualityDataBean.setDissolved_oxygen(c.getDouble(7));
                waterQualityDataBean.setNtu(c.getDouble(8));
                waterQualityDataBean.setP(c.getDouble(9));
                waterQualityDataBean.setMinute(c.getDouble(10));
                waterQualityDataBean.setHour(c.getDouble(11));
                waterQualityDataBeens.add(waterQualityDataBean);

            } while (c.moveToNext());
        }
        c.close();
        // Date d2 = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("当前时间2：" + sdf.format(d));
        return waterQualityDataBeens;
    }
}
