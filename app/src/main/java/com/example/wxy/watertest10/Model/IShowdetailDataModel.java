package com.example.wxy.watertest10.Model;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

import java.util.List;

/**
 * Created by WXY on 2017/11/26.
 */

public interface IShowdetailDataModel {
    List<WaterQualityDataBean> loadData();
}
