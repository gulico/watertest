package com.example.wxy.watertest10.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.R;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(R.layout.activity_about_us);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.finishActivity(this);
    }
}
