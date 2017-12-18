package com.example.wxy.watertest10.Bean;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by YXD on 2017/11/1.
 */

public class MyApplication extends Application{
    private static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
        ResolutionUtil.getInstance().init(this);
    }
    public static Context getContext(){
        return context;
    }
}
