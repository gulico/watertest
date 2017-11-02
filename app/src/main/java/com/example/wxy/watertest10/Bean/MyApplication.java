package com.example.wxy.watertest10.Bean;

import android.app.Application;
import android.content.Context;

/**
 * Created by YXD on 2017/11/1.
 */

public class MyApplication extends Application{
    private static Context context;
    @Override
    public void onCreate(){
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
