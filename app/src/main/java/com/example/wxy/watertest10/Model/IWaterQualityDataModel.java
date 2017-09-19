package com.example.wxy.watertest10.Model;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

/**
 * Created by WXY on 2017/9/12.
 */
/**
 * (3)Model接口：
 * 同样，Model也需要对这Bean中的4个字段进行读写操作，并存储在某个载体内(这不是我们所关心的，
 * 可以存在内存、文件、数据库或者远程服务器，但对于Presenter及View无影响),定义IUserModel接口：
 */
public interface IWaterQualityDataModel {
    void saveWaterQualityData();
    WaterQualityDataBean loadWaterQualityData(String prefix);
}
