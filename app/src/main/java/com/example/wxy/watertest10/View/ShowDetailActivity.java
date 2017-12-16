package com.example.wxy.watertest10.View;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.ShowDetailFrament.FifthFragment;
import com.example.wxy.watertest10.View.ShowDetailFrament.FirstFragment;
import com.example.wxy.watertest10.View.ShowDetailFrament.FourFragment;
import com.example.wxy.watertest10.View.ShowDetailFrament.SecondFragment;
import com.example.wxy.watertest10.View.ShowDetailFrament.SevenFragment;
import com.example.wxy.watertest10.View.ShowDetailFrament.SixFragment;
import com.example.wxy.watertest10.View.ShowDetailFrament.ThridFragment;
import com.example.wxy.watertest10.presenter.MyFragmentPagerAdapter;

import java.util.ArrayList;

public class ShowDetailActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    //fragment的集合，对应每个子页面
    private ArrayList<Fragment> fragments;
    private ViewPager myviewpager;
    //选项卡中的按钮
    private Button btn_first;
    private Button btn_second;
    private Button btn_third;
    private Button btn_four;
    private Button btn_fifth;
    private Button btn_six;
    private Button btn_seven;
    //作为指示标签的按钮
    private ImageView cursor;
    //标志指示标签的横坐标
    float cursorX = 0;
    //所有按钮的宽度的数组
    private int[] widthArgs;
    //所有标题按钮的数组
    private Button[] btnArgs;
    //【在滑动到某个子页面时，指示器需要横向跳到相应的位置】
    //【在滑动到某个子页面时，指示器需要变化到与当前标题一样的大小】
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        initView();
    }

    //初始化布局
    public void initView(){
        back = (ImageView)findViewById(R.id.showdetail_back);
        myviewpager = (ViewPager)this.findViewById(R.id.myviewpager);
        btn_first = (Button)this.findViewById(R.id.btn_first);
        btn_second = (Button)this.findViewById(R.id.btn_second);
        btn_third = (Button)this.findViewById(R.id.btn_third);
        btn_four = (Button)this.findViewById(R.id.btn_four);
        btn_fifth = (Button)this.findViewById(R.id.btn_fifth);
        btn_six = (Button)this.findViewById(R.id.btn_six);
        btn_seven = (Button)this.findViewById(R.id.btn_seven);
        //初始化按钮数组
        btnArgs = new Button[]{btn_first,btn_second,btn_third,btn_four,btn_fifth,btn_six,btn_seven};
        //指示标签设置为红色
        cursor = (ImageView)this.findViewById(R.id.cursor_btn);
        cursor.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTexthighLight));
        btn_first.post(new Runnable(){
            @Override
            public void run() {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
                //减去边距*2，以对齐标题栏文字
                lp.width = btn_first.getWidth()-btn_first.getPaddingLeft()*2;
                cursor.setLayoutParams(lp);
                cursor.setX(btn_first.getPaddingLeft());
            }
        });
        myviewpager.addOnPageChangeListener(this);
        btn_first.setOnClickListener(this);
        btn_second.setOnClickListener(this);
        btn_third.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_fifth.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        back.setOnClickListener(this);

        fragments = new ArrayList<Fragment>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThridFragment());
        fragments.add(new FourFragment());
        fragments.add(new FifthFragment());
        fragments.add(new SixFragment());
        fragments.add(new SevenFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        myviewpager.setAdapter(adapter);
        //先重置所有按钮颜色
        resetButtonColor();
        //再将第一个按钮字体设置为红色，表示默认选中第一个
        btn_first.setTextColor(ContextCompat.getColor(this, R.color.colorTexthighLight));

    }

    @Override
    public void onClick(View v) {
        //只是一句简单的setCurrentItem方法的调用，就能实现跳转到对应的子页面
        switch (v.getId()) {
            case R.id.btn_first:
                myviewpager.setCurrentItem(0);
                cursorAnim(0);
                break;
            case R.id.btn_second:
                myviewpager.setCurrentItem(1);
                cursorAnim(1);
                break;
            case R.id.btn_third:
                myviewpager.setCurrentItem(2);
                cursorAnim(2);
                break;
            case R.id.btn_four:
                myviewpager.setCurrentItem(3);
                cursorAnim(3);
                break;
            case R.id.btn_fifth:
                myviewpager.setCurrentItem(4);
                cursorAnim(4);
                break;
            case R.id.btn_six:
                myviewpager.setCurrentItem(5);
                cursorAnim(5);
                break;
            case R.id.btn_seven:
                myviewpager.setCurrentItem(6);
                cursorAnim(6);
                break;
            case R.id.showdetail_back:
                finish();
                break;
            default:
        }

    }

    //重置所有按钮的颜色
    public void resetButtonColor(){
        btn_first.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_second.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_third.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_four.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_fifth.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_six.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_seven.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn_first.setTextColor(Color.GRAY);
        btn_second.setTextColor(Color.GRAY);
        btn_third.setTextColor(Color.GRAY);
        btn_four.setTextColor(Color.GRAY);
        btn_fifth.setTextColor(Color.GRAY);
        btn_six.setTextColor(Color.GRAY);
        btn_seven.setTextColor(Color.GRAY);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //widthArgs的实例化
        if(widthArgs==null){
            widthArgs = new int[]{btn_first.getWidth(),
                    btn_second.getWidth(),
                    btn_third.getWidth(),
                    btn_four.getWidth(),
                    btn_fifth.getWidth(),
                    btn_six.getWidth(),
            btn_seven.getWidth()};
        }
        //onPageSelected会在每次滑动ViewPager的时候触发，所以所有滑动时的变化都可以在这里面定义，比如标题按钮的颜色随着滑动的变化等
        //每次滑动首先重置所有按钮的颜色
        resetButtonColor();
        //将滑动到的当前按钮颜色设置为蓝色
        btnArgs[position].setTextColor(ContextCompat.getColor(this, R.color.colorTexthighLight));
        cursorAnim(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //指示器的跳转，传入当前所处的页面的下标
    public void cursorAnim(int curItem){
        //每次调用，就将指示器的横坐标设置为0，即开始的位置
        cursorX = 0;
        //再根据当前的curItem来设置指示器的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();
        //减去边距*2，以对齐标题栏文字
        lp.width = widthArgs[curItem]-btnArgs[0].getPaddingLeft()*2;
        cursor.setLayoutParams(lp);
        //循环获取当前页之前的所有页面的宽度
        for(int i=0; i<curItem; i++){
            cursorX = cursorX + btnArgs[i].getWidth();
        }
        //再加上当前页面的左边距，即为指示器当前应处的位置
        cursor.setX(cursorX+btnArgs[curItem].getPaddingLeft());
    }

}
