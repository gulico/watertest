package com.example.wxy.watertest10.presenter;

import android.util.Log;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.Model.IShowdetailDataModel;
import com.example.wxy.watertest10.Model.ShowdetailDataModel;
import com.example.wxy.watertest10.View.ShowDetailFrament.IShowdetailFrament;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by WXY on 2017/11/26.
 */

public class ShowdetailDataPersenter implements IShowdetailDataPersenter{
    private IShowdetailDataModel iShowdetailDataModel;
    private IShowdetailFrament iShowdetailFrament;
    private List<WaterQualityDataBean> waterQualityDataBeans;
    public ShowdetailDataPersenter(IShowdetailFrament iShowdetailFrament){
        this.iShowdetailFrament = iShowdetailFrament;//这边是接口实例
        iShowdetailDataModel = new ShowdetailDataModel();//这边是new的一个Model实例赋值给接口……暂时不是很懂
    }
    public void loadData(){
        waterQualityDataBeans = iShowdetailDataModel.loadData();//加载全部数据
    }
    public List<String> loadAllTimes(){//获取全部日期
        List<String> AllTimes = new ArrayList<>();
        String temp = new String("");
        for(WaterQualityDataBean waterQualityDataBean:waterQualityDataBeans){
            Log.d(TAG, "loadAllTimes: "+waterQualityDataBean.getTime());
            if(waterQualityDataBean.getTime().equals(temp)){
                continue;
            }else {
                AllTimes.add(waterQualityDataBean.getTime());
                temp = waterQualityDataBean.getTime();
            }
        }
        return AllTimes;
    }
    public List<Entry> loadPh(String Time){
        List<Entry> Ph = new ArrayList<>();
        for(WaterQualityDataBean waterQualityDataBean:waterQualityDataBeans){
            Log.d(TAG, "loadPh: "+waterQualityDataBean.getTime()+"     "+waterQualityDataBean.getHour()+"   "+waterQualityDataBean.getMinute());
           if(waterQualityDataBean.getTime().equals(Time)){
               //Ph.add()
               //Log.d(TAG, "loadPh: "+waterQualityDataBean.getTime()+"     "+waterQualityDataBean.getHour()+"   "+waterQualityDataBean.getMinute());
           }
        }
        return null;
    }
}
