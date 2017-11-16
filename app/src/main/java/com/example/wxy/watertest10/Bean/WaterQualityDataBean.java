package com.example.wxy.watertest10.Bean;

import java.util.Date;

/**
 * Created by WXY on 2017/9/12.
 */
/**
 * (1)首先我们需要一个UserBean，用来保存单次监测的信息
 */
public class WaterQualityDataBean {
    private String City;
    private Date Time;//测试时间
    private double ph;//ph值
    private double conductivity;//导电性
    private double water_temperature;//水温
    private double ammonia_nitrogen;//氨态氮
    private double dissolved_oxygen;//溶解氧含量
    private double ntu;//浊度
    private double p;//磷

    public WaterQualityDataBean(String City,Date time,double ph,double conductivity,double water_temperature,double ammonia_nitrogen,double dissolved_oxygen,double ntu,double p){
        this.City = City;
        this.Time = time;
        this.ph = ph;
        this.conductivity = conductivity;
        this.water_temperature = water_temperature;
        this.ammonia_nitrogen = ammonia_nitrogen;
        this.dissolved_oxygen = dissolved_oxygen;
        this.ntu = ntu;
        this.p = p;
    }
    public String getCity(){
        return City;
    }
    public Date getTime(){
        return Time;
    }
    public double getPh(){
        return ph;
    }
    public double getConductivity(){return conductivity;}
    public double getWater_temperature(){
        return water_temperature;
    }
    public double getAmmonia_nitrogen(){return ammonia_nitrogen;}
    public double getDissolved_oxygen(){return dissolved_oxygen;}
    public double getNtu(){return ntu;}
    public double getP(){return p;}

    public void setCity(String city) {
        City = city;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public void setConductivity(double conductivity) {
        this.conductivity = conductivity;
    }

    public void setWater_temperature(double water_temperature) {
        this.water_temperature = water_temperature;
    }

    public void setAmmonia_nitrogen(double ammonia_nitrogen) {
        this.ammonia_nitrogen = ammonia_nitrogen;
    }

    public void setDissolved_oxygen(double dissolved_oxygen) {
        this.dissolved_oxygen = dissolved_oxygen;
    }

    public void setNtu(double ntu) {
        this.ntu = ntu;
    }

    public void setP(double p) {
        this.p = p;
    }
}
