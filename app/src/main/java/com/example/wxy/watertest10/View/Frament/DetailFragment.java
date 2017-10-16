package com.example.wxy.watertest10.View.Frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.EditEarlyWarningActivity;
import com.example.wxy.watertest10.View.MainActivity;

/**
 * Created by WXY on 2017/10/4.
 */

public class DetailFragment extends Fragment implements View.OnClickListener{
    MainActivity acticity;
    private DrawerLayout mDrawerLayout;
    FloatingActionButton edit_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//创建碎片
        super.onCreate(savedInstanceState);
        acticity = (MainActivity)getActivity();
    }

    @Nullable
    @Override//创建碎片视图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailframent,container,false);
        mDrawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        ActionBar actionBar = acticity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        edit_btn = (FloatingActionButton)view.findViewById(R.id.edit_earlywarning_btn);
        edit_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setHasOptionsMenu(true);
        acticity.getMenuInflater().inflate(R.menu.detailtoolbar,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutus:
                Toast.makeText(getContext(),"关于我们",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_earlywarning_btn:
                //跳转到修改阈值页面
                Intent intent =new Intent(acticity, EditEarlyWarningActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
