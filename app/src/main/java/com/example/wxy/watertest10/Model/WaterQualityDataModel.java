package com.example.wxy.watertest10.Model;

/**
 * Created by WXY on 2017/9/12.
 */

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

import java.util.Date;

/**
 * 数据存储的模型层，只需要考虑怎么把数据存起来
 * 以及数据的取出
 */
public class WaterQualityDataModel implements IWaterQualityDataModel{
    public void saveWaterQualityData(){//保存数据到数据库

    }
    public WaterQualityDataBean loadWaterQualityData(String prefix){//从数据库取出数据，传入地域前缀
        Date date =new Date();
        date.getTime();
        WaterQualityDataBean waterQualityDataBean = new WaterQualityDataBean(prefix,date,7,10,25,70,10,20,30);
        return waterQualityDataBean;
    }
}
