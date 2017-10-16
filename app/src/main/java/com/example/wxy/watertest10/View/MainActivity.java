package com.example.wxy.watertest10.View;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wxy.watertest10.Bean.SimpleQualityBean;
import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.Frament.DetailFragment;
import com.example.wxy.watertest10.View.Frament.HomeFragment;
import com.example.wxy.watertest10.View.Frament.MapFragment;
import com.example.wxy.watertest10.presenter.IMainUiPersenter;
import com.example.wxy.watertest10.presenter.MainUiPresenter;
import com.example.wxy.watertest10.presenter.SimpleQualityRecyclerAdapter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView home_btn;
    ImageView map_btn;
    ImageView detail_btn;
    int navisclick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*-------------------------底部导航栏----------------------------*/
        home_btn = (ImageView)findViewById(R.id.home_button);
        home_btn.setOnClickListener(this);
        map_btn = (ImageView)findViewById(R.id.map_button);
        map_btn.setOnClickListener(this);
        detail_btn = (ImageView)findViewById(R.id.detail_button);
        detail_btn.setOnClickListener(this);
        /*----------------------------------------------------------------*/
        replaceFragment(new HomeFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_button:
                if(navisclick!=0){
                    home_btn.setImageResource(R.drawable.home);
                    map_btn.setImageResource(R.drawable.unmap);
                    detail_btn.setImageResource(R.drawable.undata);
                    replaceFragment(new HomeFragment());
                    navisclick=0;
                }
                break;
            case R.id.map_button:
                if(navisclick!=1){
                    map_btn.setImageResource(R.drawable.map);
                    home_btn.setImageResource(R.drawable.unhome);
                    detail_btn.setImageResource(R.drawable.undata);
                    replaceFragment(new MapFragment());
                    navisclick=1;
                }
                break;
            case R.id.detail_button:
                if(navisclick!=2){
                    detail_btn.setImageResource(R.drawable.data);
                    home_btn.setImageResource(R.drawable.unhome);
                    map_btn.setImageResource(R.drawable.unmap);
                    replaceFragment(new DetailFragment());
                    navisclick=2;
                }
                break;
            default:
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Top_fragment,fragment);
        transaction.commit();
    }
}
