package com.example.wxy.watertest10.presenter;

import android.util.Log;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.Model.IShowdetailDataModel;
import com.example.wxy.watertest10.Model.ShowdetailDataModel;
import com.example.wxy.watertest10.View.ShowDetailFrament.IShowdetailFrament;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by WXY on 2017/11/26.
 */

public class ShowdetailDataPersenter implements IShowdetailDataPersenter {
    private IShowdetailDataModel iShowdetailDataModel;
    private IShowdetailFrament iShowdetailFrament;
    private List<WaterQualityDataBean> waterQualityDataBeans;

    public ShowdetailDataPersenter(IShowdetailFrament iShowdetailFrament) {
        this.iShowdetailFrament = iShowdetailFrament;//这边是接口实例
        iShowdetailDataModel = new ShowdetailDataModel();//这边是new的一个Model实例赋值给接口……暂时不是很懂
    }

    public void loadData() {
        waterQualityDataBeans = iShowdetailDataModel.loadData();//加载全部数据
    }

    public List<String> loadAllTimes() {//获取全部日期
        List<String> AllTimes = new ArrayList<>();
        String temp = new String("");
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            Log.d(TAG, "loadAllTimes: " + waterQualityDataBean.getTime());
            if (waterQualityDataBean.getTime().equals(temp)) {
                continue;
            } else {
                AllTimes.add(waterQualityDataBean.getTime());
                temp = waterQualityDataBean.getTime();
            }
        }
        return AllTimes;
    }

    public List<Entry> loadPh(String Time) {
        List<Entry> Ph = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getPh());
                int x = (int)waterQualityDataBean.getHour();
                        //new Integer((int) waterQualityDataBean.getHour());
                Ph.add(new Entry(x, val));
            }
        }
        Collections.sort(Ph,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return Ph;
    }
    public List<Entry> loadwater_temperature(String Time) {
        List<Entry> water_temperature = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getWater_temperature());
                Float x = new Float(waterQualityDataBean.getHour()  );
                water_temperature.add(new Entry(x, val));
            }
        }
        Collections.sort(water_temperature,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return water_temperature;
    }
    //ammonia_nitrogen;
    public List<Entry> loadammonia_nitrogen(String Time) {
        List<Entry> ammonia_nitrogen = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getAmmonia_nitrogen());
                Float x = new Float(waterQualityDataBean.getHour()  );
                ammonia_nitrogen.add(new Entry(x, val));
            }
        }
        Collections.sort(ammonia_nitrogen,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return ammonia_nitrogen;
    }
    public List<Entry> loadp(String Time) {
        List<Entry> p = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getP());
                Float x = new Float(waterQualityDataBean.getHour()  );
                p.add(new Entry(x, val));
            }
        }
        Collections.sort(p,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return p;
    }
    //conductivity
    public List<Entry> loadconductivity(String Time) {
        List<Entry> conductivity = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getConductivity());
                Float x = new Float(waterQualityDataBean.getHour()  );
                conductivity.add(new Entry(x, val));
            }
        }
        Collections.sort(conductivity,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return conductivity;
    }
    //dissolved_oxygen
    public List<Entry> loaddissolved_oxygen(String Time) {
        List<Entry> dissolved_oxygen = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getDissolved_oxygen());
                Float x = new Float(waterQualityDataBean.getHour()  );
                dissolved_oxygen.add(new Entry(x, val));
            }
        }
        Collections.sort(dissolved_oxygen,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return dissolved_oxygen;
    }
    //ntu
    public List<Entry> loadntu(String Time) {
        List<Entry> ntu = new ArrayList<>();
        for (WaterQualityDataBean waterQualityDataBean : waterQualityDataBeans) {
            if (waterQualityDataBean.getTime().equals(Time)) {
                Float val = new Float(waterQualityDataBean.getNtu());
                Float x = new Float(waterQualityDataBean.getHour()  );
                ntu.add(new Entry(x, val));
            }
        }
        Collections.sort(ntu,new Comparator<Entry>(){
            @Override
            public int compare(Entry o1, Entry o2) {
                if(o1.getX() > o2.getX()){
                    return 1;
                }
                if(o1.getX() == o2.getX()){
                    return 0;
                }
                return -1;
            }
        });
        return ntu;
    }
}
