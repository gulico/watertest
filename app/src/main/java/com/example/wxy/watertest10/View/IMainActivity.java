package com.example.wxy.watertest10.View;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

/**
 * Created by WXY on 2017/9/14.
 */

public interface IMainActivity {
    void saveDate();//保存界面上获取的数据
    void loadData();//取出p层获得的数据反应到ui上
    String getPrefix();
    void setBean(WaterQualityDataBean waterQualityDataBean);
}
