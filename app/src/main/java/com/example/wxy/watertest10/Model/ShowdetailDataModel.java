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
        waterQualityDataBeens = DataSupport.findAll(WaterQualityDataBean.class);
        /*
        Cursor c = DataSupport.findBySQL("select * from WaterQualityDataBean ");
        int i=0;
        if (c.moveToFirst()) {
            do {//2日期，3Ammonia_nitrogen，4Conductivity，5Dissolved_oxygen，6hour，7minute,8ntu,9p，10ph,11水温，
                WaterQualityDataBean waterQualityDataBean = new WaterQualityDataBean();
                waterQualityDataBean.setTime(c.getString(2));
                waterQualityDataBean.setPh(c.getDouble(10));
                waterQualityDataBean.setConductivity(c.getDouble(4));
                waterQualityDataBean.setWater_temperature(c.getDouble(11));
                waterQualityDataBean.setAmmonia_nitrogen(c.getDouble(3));
                waterQualityDataBean.setDissolved_oxygen(c.getDouble(5));
                waterQualityDataBean.setNtu(c.getDouble(8));
                waterQualityDataBean.setP(c.getDouble(9));
                waterQualityDataBean.setMinute(c.getDouble(7));
                waterQualityDataBean.setHour(c.getDouble(6));
                waterQualityDataBeens.add(waterQualityDataBean);
            } while (c.moveToNext());
        }
        c.close();*/
        // Date d2 = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("当前时间2：" + sdf.format(d));
        return waterQualityDataBeens;
    }
}
