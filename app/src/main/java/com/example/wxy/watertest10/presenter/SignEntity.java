package com.example.wxy.watertest10.presenter;

/**
 * SignEntity
 * Created by E.M on 2016/4/21.
 */
public class SignEntity {
    private int dayOfMonth;//本月有几天
    private int dayType;//这天的状态

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayType() {
        return dayType;
    }

    public void setDayType(int dayType) {
        this.dayType = dayType;
    }
}
