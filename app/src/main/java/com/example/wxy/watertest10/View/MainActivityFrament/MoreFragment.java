package com.example.wxy.watertest10.View.MainActivityFrament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.MainActivity;
import com.example.wxy.watertest10.View.NewsActivity;
import com.example.wxy.watertest10.View.UploadPhotosActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2017/10/4.
 */

public class MoreFragment extends Fragment implements View.OnClickListener{
    MainActivity acticity;
    private View view1,view2,view3;
    private ViewPager viewPager;
    private List<View> viewList;//view数组

    private LinearLayout to_News,to_Comments,to_complain;

    ImageView To_aboutus;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//创建碎片
        super.onCreate(savedInstanceState);
        acticity = (MainActivity)getActivity();
    }

    @Nullable
    @Override//创建碎片视图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moreframent,container,false);
        to_News = (LinearLayout)view.findViewById(R.id.To_news);
        to_News.setOnClickListener(this);
        to_Comments = (LinearLayout)view.findViewById(R.id.To_comments);
        to_Comments.setOnClickListener(this);
        to_complain = (LinearLayout)view.findViewById(R.id.To_complain);
        to_complain.setOnClickListener(this);

        /*----------------------------顶部viewpapers----------------------------*/
        viewPager = (ViewPager) view.findViewById(R.id.de_viewpager);
        LayoutInflater inflater2 = getLayoutInflater(null);
        view1 = inflater2.inflate(R.layout.de_fra_viewpaper1, null);
        view2 = inflater2.inflate(R.layout.de_fra_viewpaper2, null);
        view3 = inflater2.inflate(R.layout.de_fra_viewpaper3, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));


                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);

        /*--------------------------------------------------------*/


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.To_news://新闻动态
                Intent intent2 = new Intent(acticity, NewsActivity.class);
                startActivity(intent2);
                break;
            case R.id.To_comments://通知公告
                Toast.makeText(acticity,"to comments",Toast.LENGTH_SHORT).show();
                break;
            case R.id.To_complain://拍照投诉
                Intent intent = new Intent(acticity, UploadPhotosActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
