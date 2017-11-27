package com.example.wxy.watertest10.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Model.WaterQualityService;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.MainActivityFrament.HomeFragment;
import com.example.wxy.watertest10.View.MainActivityFrament.MapFragment;
import com.example.wxy.watertest10.View.MainActivityFrament.MoreFragment;

import org.litepal.LitePal;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ImageView home_btn;
    ImageView map_btn;
    ImageView detail_btn;
    CircleImageView To_Login;
    NavigationView navigationView;//侧拉中的导航
    View nav_header;
    private DrawerLayout drawerLayout;
    int navisclick = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*----------------------------服务--------------------------------*/
       Intent startIntent = new Intent(this,WaterQualityService.class);
        startService(startIntent);
        /*-------------------------创建数据库-----------------------------*/
        LitePal.getDatabase();
        /*-------------------------底部导航栏----------------------------*/
        home_btn = (ImageView)findViewById(R.id.home_button);
        home_btn.setOnClickListener(this);
        map_btn = (ImageView)findViewById(R.id.map_button);
        map_btn.setOnClickListener(this);
        detail_btn = (ImageView)findViewById(R.id.detail_button);
        detail_btn.setOnClickListener(this);
        /*----------------------------------------------------------------*/
        replaceFragment(new HomeFragment());

        /*---------------------------侧拉导航栏------------------------------*/
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.sign_in);//默认选中
        nav_header = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sign_in://签到
                        break;
                    case R.id.settings://设置
                        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.information://关于我们
                        break;
                    default:
                }
                return true;
            }
        });
        /*----------------------------------------------------------------*/
    }
    @Override
    protected void onStart() {//由可见变不可见时
        super.onStart();
        To_Login = (CircleImageView)nav_header.findViewById(R.id.icon_image);//头像
        SharedPreferences pref  = getSharedPreferences("User",MODE_PRIVATE);
        String name = pref.getString("Username","");
        String password = pref.getString("Userpassword","");
        if(name.equals("")||password.equals("")){//未登录
            To_Login.setOnClickListener(this);
            TextView tips = (TextView)nav_header.findViewById(R.id.tips);
            tips.setText("请点击头像进行登录");
            TextView showname = (TextView)nav_header.findViewById(R.id.uesrname);
            showname.setText("登陆");
        }
        else{//已经登录过且有缓存
            TextView tips = (TextView)nav_header.findViewById(R.id.tips);
            tips.setText("");
            TextView showname = (TextView)nav_header.findViewById(R.id.uesrname);
            showname.setText(name);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_button:
                if(navisclick!=0){
                    home_btn.setColorFilter(Color.BLUE);
                    map_btn.setColorFilter(Color.GRAY);
                    detail_btn.setColorFilter(Color.GRAY);
                    replaceFragment(new HomeFragment());
                    navisclick=0;
                }
                break;
            case R.id.map_button:
                if(navisclick!=1){
                    map_btn.setColorFilter(Color.BLUE);
                    home_btn.setColorFilter(Color.GRAY);
                    detail_btn.setColorFilter(Color.GRAY);
                    replaceFragment(new MapFragment());
                    navisclick=1;
                }
                break;
            case R.id.detail_button:
                if(navisclick!=2){
                    detail_btn.setColorFilter(Color.BLUE);
                    home_btn.setColorFilter(Color.GRAY);
                    map_btn.setColorFilter(Color.GRAY);
                    replaceFragment(new MoreFragment());
                    navisclick=2;
                }
                break;
            case R.id.icon_image:
                Intent intent_toLogin = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent_toLogin);//跳转到登录界面
            default:
                break;
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Top_fragment, fragment);
        transaction.commit();
    }

}
