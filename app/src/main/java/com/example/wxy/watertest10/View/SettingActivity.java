package com.example.wxy.watertest10.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wxy.watertest10.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ImageView back = (ImageView)findViewById(R.id.Setting_back);
        back.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences  pref = getSharedPreferences("User", MODE_PRIVATE);
        if(pref.getString("Username","").equals("")||pref.getString("Userpassword","").equals("")){//未登录
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.logoff_Layout);
            linearLayout.setVisibility(View.GONE);
        }
        else {//登陆了才可以不登录
            TextView log_off = (TextView)findViewById(R.id.log_off);
            log_off.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Setting_back:
                finish();
                break;
            case  R.id.log_off://退出登录
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.logoff_PB);
                progressBar.setVisibility(View.VISIBLE);//进度条转动
                SharedPreferences.Editor editor = this.getSharedPreferences("User", Context.MODE_PRIVATE).edit();
                editor.putString("Username","");
                editor.putString("Userpassword","");
                editor.apply();
                progressBar.setVisibility(View.INVISIBLE);//进度条消失
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
        }
    }
}
