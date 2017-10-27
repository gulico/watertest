package com.example.wxy.watertest10.Model;

import android.content.Context;

import com.example.wxy.watertest10.Bean.UserBean;

/**
 * Created by WXY on 2017/10/25.
 */

public interface ILoginModel {
    int loginUser(UserBean userBean,Context activityContext);
}
