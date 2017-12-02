package com.example.wxy.watertest10.presenter;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.Model.IShowdetailDataModel;
import com.example.wxy.watertest10.Model.ShowdetailDataModel;
import com.example.wxy.watertest10.View.ShowDetailFrament.IShowdetailFrament;

import java.util.ArrayList;
import java.util.List;

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
        for(WaterQualityDataBean waterQualityDataBean:waterQualityDataBeans){
            AllTimes.add(waterQualityDataBean.getTime());
        }
        return AllTimes;
    }
}
