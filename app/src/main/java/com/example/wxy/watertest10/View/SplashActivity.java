package com.example.wxy.watertest10.View;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.Model.WaterQualityService;
import com.example.wxy.watertest10.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

public class SplashActivity extends BaseActivity {
    //private final int SPLASH_DISPLAY_LENGHT = 3000;//开启动画时间设置为3秒
    private Handler handler;
    private WaterQualityService.WaterQualityBinder waterQualityBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            waterQualityBinder = (WaterQualityService.WaterQualityBinder) service;
            waterQualityBinder.StartQualityDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        /*-------------------------创建数据库-----------------------------*/
        LitePal.getDatabase();
        /*----------------------------服务--------------------------------*/
        //DataSupport.deleteAll(WaterQualityDataBean.class);
        Intent startIntent = new Intent(this,WaterQualityService.class);
        startService(startIntent);
        bindService(startIntent, connection, BIND_AUTO_CREATE); // 绑定服务
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        }
       /* handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);*/
    }
    //禁用返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK){
        return  true;
    }
    return  super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(connection);
    }
}
