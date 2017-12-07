package com.example.wxy.watertest10.presenter;

/**
 * 签到日历控件数据适配器
 * Created by E.M on 2016/4/20.
 */
public abstract class CalendarAdapter {
    public abstract com.example.wxy.watertest10.View.SignView.DayType getType(int dayOfMonth);
}
