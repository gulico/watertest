package com.example.wxy.watertest10.Bean;

/**
 * Created by WXY on 2017/12/20.
 * 储存单个打卡信息用于recyclerview暂时储存
 */

public class PunchClockSimpleBean {
    String Time = null;//时间
    String TimeType = null;//上班或者下班
    String WorkType = null;//迟到、早退或者正常
    String Data = null;//日期

    public PunchClockSimpleBean(String time, String timeType, String workType, String data) {
        Time = time;
        TimeType = timeType;
        WorkType = workType;
        Data = data;
    }

    public String getTime() {
        return Time;
    }

    public String getTimeType() {
        return TimeType;
    }

    public String getWorkType() {
        return WorkType;
    }

    public String getData() {
        return Data;
    }

    public void setTime(String time) {

        Time = time;
    }

    public void setTimeType(String timeType) {
        TimeType = timeType;
    }

    public void setWorkType(String workType) {
        WorkType = workType;
    }

    public void setData(String data) {
        Data = data;
    }
}
