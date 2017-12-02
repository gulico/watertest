package com.example.wxy.watertest10.View.ShowDetailFrament;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wxy.watertest10.Bean.MyLineChart;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.presenter.IShowdetailDataPersenter;
import com.example.wxy.watertest10.presenter.ShowdetailDataPersenter;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;

import cn.forward.androids.views.ScrollPickerView;

import static com.example.wxy.watertest10.Bean.AppManager.currentActivity;

public class FirstFragment extends Fragment implements IShowdetailFrament{

    private MyLineChart mChart;
    private SeekBar seekBar;
    private TextView seekBar_progress;
    private ScrollPickerView datapicker;
    private TextView highlight_data;
    private TextView highlight_time;
    private IShowdetailDataPersenter iShowdetailDataPersenter;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {//被创建
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.fragment_first_fragemnt, container,false);
        iShowdetailDataPersenter = new ShowdetailDataPersenter(this);
        /*----------------------------------初始化滚轮--------------------------------------*/
        datapicker = (ScrollPickerView)v.findViewById(R.id.data_picker);
        /*----------------------------------初始化折线图--------------------------------------*/
        mChart = (MyLineChart)v.findViewById(R.id.linechart);
        /*----------------------------------初始化进度条--------------------------------------*/
        seekBar = (SeekBar)v.findViewById(R.id.seekbar);
        seekBar_progress = (TextView)v.findViewById(R.id.seekbar_progresstext);
        /*----------------------------------初始化被选中的数据--------------------------------------*/
        highlight_data = (TextView)v.findViewById(R.id.highlight_data);
        highlight_time = (TextView)v.findViewById(R.id.hightlight_time);
        return v;
    }
    @Override
    public void onStart() {//显示
        super.onStart();
        iShowdetailDataPersenter.loadData();//加载数据
        initChart();
        //datapicker.setData(iShowdetailDataPersenter.loadAllTimes());//设置选项
        datapicker.setSelectedPosition(0);//设置初始位置
        datapicker.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {//监听滚轮选择器变化
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                Toast.makeText(currentActivity(),""+datapicker.getSelectedItem(),Toast.LENGTH_SHORT).show();
                setData(24,14);
                mChart.invalidate();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar_progress.setText(progress+"个小时");
                mChart.setVisibleXRange(0,progress);
                //setData(progress,14);
                mChart.invalidate();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setData(int count,float range){
        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < 24; i++) {

            float val = (float) (Math.random() * range);
            values.add(new Entry(i,val));
        }
        LineDataSet dataSet = new LineDataSet(values,"ph");
        dataSet.setDrawValues(false); // 是否在点上绘制Value
        dataSet.setHighlightEnabled(true);//选中数据时高亮
        dataSet.setColor(Color.BLUE);//折现的颜色
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(1.5f);//折现粗细
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置圆滑曲线
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet); // add the datasets

        // create a data object with the datasets
        final LineData data = new LineData(dataSets);
        // set data
        mChart.setData(data);
        mChart.setVisibleXRange(0,7);
    }
    private void initChart(){
        mChart.setBackgroundColor(Color.WHITE);//设置背景颜色
        mChart.setDragEnabled(true);//可以拖动图表
        mChart.setScaleXEnabled(true);//启用x轴缩放
        //mChart.setMarker();//设置选中时的浮动窗口

        Legend legend = mChart.getLegend();
        legend.setEnabled(false);//设置图例不可见

        XAxis xAxis = mChart.getXAxis();//X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置在图表底部

        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(50f,"yLimit 测试");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setTextColor(Color.RED);
        // 获得左侧侧坐标轴
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.addLimitLine(yLimitLine);

        setData(24,14);
        mChart.invalidate();

        //请注意， 修改视口的所有方法需要在 为Chart 设置数据之后 调用 。
        mChart.animateY(5000); //折线显示方式
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {//点击高亮时回调
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(currentActivity(),"时间："+e.getX()+" ph:"+e.getY(),Toast.LENGTH_SHORT).show();
                highlight_data.setText("ph " + e.getY());
                highlight_time.setText("具体时间 "+ (int)e.getX()+":00");
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

}
