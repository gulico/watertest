package com.example.wxy.watertest10.Bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by WXY on 2017/9/12.
 */
/**
 * (1)首先我们需要一个UserBean，用来保存单次监测的信息
 */
public class WaterQualityDataBean extends DataSupport {
    private String City;
    private String Time;//测试时间
    private String ph;//ph值
    private String conductivity;//导电性
    private String water_temperature;//水温
    private String ammonia_nitrogen;//氨态氮
    private String dissolved_oxygen;//溶解氧含量
    private String ntu;//浊度
    private String p;//磷


    public String getCity(){ return City; }
    public String getTime(){
        return Time;
    }
    public String getPh(){
        return ph;
    }
    public String getConductivity(){return conductivity;}
    public String getWater_temperature(){
        return water_temperature;
    }
    public String getAmmonia_nitrogen(){return ammonia_nitrogen;}
    public String getDissolved_oxygen(){return dissolved_oxygen;}
    public String getNtu(){return ntu;}
    public String getP(){return p;}

    public void setCity(String city) {
         City = city;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public void setConductivity(String conductivity) {
        this.conductivity = conductivity;
    }

    public void setWater_temperature(String water_temperature) {
        this.water_temperature = water_temperature;
    }

    public void setAmmonia_nitrogen(String ammonia_nitrogen) {
        this.ammonia_nitrogen = ammonia_nitrogen;
    }

    public void setDissolved_oxygen(String dissolved_oxygen) {
        this.dissolved_oxygen = dissolved_oxygen;
    }

    public void setNtu(String ntu) {
        this.ntu = ntu;
    }

    public void setP(String p) {
        this.p = p;
    }

}
