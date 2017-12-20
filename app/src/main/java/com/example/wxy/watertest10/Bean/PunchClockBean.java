package com.example.wxy.watertest10.Bean;

import org.litepal.crud.DataSupport;

/**
 * Created by WXY on 2017/12/20.
 * 记录打卡状况，用户长期储存以及上传
 */

public class PunchClockBean extends DataSupport {
    String ToWorkTime = null;//上班 时间
    String GetOffTime = null;//下班时间
    String Data = null;//打卡日期yyyy-mm-dd
    String ToWorkType = null;//9点am以后算迟到，其余正常
    String GetOffType = null;//18点pm以后算早退，其余正常
    String username = null;//打卡人id

    public void setToWorkTime(String toWorkTime) {
        ToWorkTime = toWorkTime;
    }

    public void setGetOffTime(String getOffTime) {
        GetOffTime = getOffTime;
    }

    public void setData(String data) {
        Data = data;
    }

    public void setToWorkType(String toWorkType) {
        ToWorkType = toWorkType;
    }

    public void setGetOffType(String getOffType) {
        GetOffType = getOffType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToWorkTime() {
        return ToWorkTime;
    }

    public String getGetOffTime() {
        return GetOffTime;
    }

    public String getData() {
        return Data;
    }

    public String getToWorkType() {
        return ToWorkType;
    }

    public String getGetOffType() {
        return GetOffType;
    }

    public String getUsername() {
        return username;
    }
}
