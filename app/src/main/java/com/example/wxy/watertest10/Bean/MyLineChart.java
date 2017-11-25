package com.example.wxy.watertest10.Bean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;

import static android.content.ContentValues.TAG;

/**
 * Created by WXY on 2017/11/19.
 */

public class MyLineChart extends LineChart {
    PointF downPoint = new PointF();
    public MyLineChart(Context context) {
        super(context);
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        switch (evt.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint.x = evt.getX();
                downPoint.y = evt.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScaleX() > 1 && Math.abs(evt.getX() - downPoint.x) > 5) {//getScaleX获取缩放比 && 横向移动距离大于5
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //含义：当传入的参数为true时，表示子组件要自己消费这次事件，告诉父组件不要拦截（抢走）这次的事件。
                }
                break;
        }
        return super.onTouchEvent(evt);
    }

}
