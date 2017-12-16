package com.example.wxy.watertest10.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.R;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        back = (ImageView)this.findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
