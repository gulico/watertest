package com.example.wxy.watertest10.Bean;

/**
 * Created by WXY on 2017/9/18.
 */

public class SimpleQualityBean {//单个属性描述
    private String name;//中文名
    private String Ename;//英文名
    private double date;//数据
    public SimpleQualityBean(String name,String Ename,double date){
        this.name = name;
        this.Ename = Ename;
        this.date = date;
    }
    public String getName(){
        return name;
    }
    public String getEname(){
        return Ename;
    }
    public double getDate(){
        return date;
    }
}
