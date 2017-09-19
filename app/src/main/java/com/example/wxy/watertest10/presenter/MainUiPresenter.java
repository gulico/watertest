package com.example.wxy.watertest10.presenter;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.Model.IWaterQualityDataModel;
import com.example.wxy.watertest10.Model.WaterQualityDataModel;
import com.example.wxy.watertest10.View.IMainActivity;

/**
 * Created by WXY on 2017/9/15.
 */

public class MainUiPresenter implements IMainUiPersenter{
    private IMainActivity iMainActivity;
    private IWaterQualityDataModel iWaterQualityDataModel;
    public MainUiPresenter(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;//这边是接口实例
        iWaterQualityDataModel = new WaterQualityDataModel();//这边是new的一个Model实例赋值给接口……暂时不是很懂
    }
    public void loadDate(){
        WaterQualityDataBean waterQualityDataBean = iWaterQualityDataModel.loadWaterQualityData(iMainActivity.getPrefix());
        iMainActivity.setBean(waterQualityDataBean);
    }
}
