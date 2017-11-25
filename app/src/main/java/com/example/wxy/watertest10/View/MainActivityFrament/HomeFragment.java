package com.example.wxy.watertest10.View.MainActivityFrament;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wxy.watertest10.Bean.SimpleQualityBean;
import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.IMainActivity;
import com.example.wxy.watertest10.presenter.IMainUiPersenter;
import com.example.wxy.watertest10.presenter.MainUiPresenter;
import com.example.wxy.watertest10.presenter.SimpleQualityRecyclerAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2017/10/3.
 */

public class HomeFragment extends Fragment implements IMainActivity{
    WaterQualityDataBean waterQualityDataBean;
    IMainUiPersenter iMainUiPersenter;
    PieChart _dissolved_oxygenPie;//溶解氧饼图
    private List<SimpleQualityBean> simpleQualityBeanList = new ArrayList<>();
    private SimpleQualityRecyclerAdapter adapter;//适配器
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homeframent,container,false);
        iMainUiPersenter = new MainUiPresenter(this);
        _dissolved_oxygenPie = (PieChart) view.findViewById(R.id.dissolved_oxygenPie);
        loadData();
       // DrawPieCharts((float) waterQualityDataBean.getDissolved_oxygen());
        //initRecycler();
        //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        //recyclerView.setLayoutManager(layoutManager);
        //adapter = new SimpleQualityRecyclerAdapter(simpleQualityBeanList);
        //recyclerView.setAdapter(adapter);
        return view;
    }
    public void saveDate() {//保存界面上获取的数据
    }

    public void loadData() {//取出p层获得的数据反应到ui上
        iMainUiPersenter.loadDate();
    }
    public String getPrefix(){//让p层获取默认地区的检测数据
        return "D01";
    }
    public void setBean(WaterQualityDataBean waterQualityDataBean){//本层获取数据
        this.waterQualityDataBean = waterQualityDataBean;
    }
    private void initRecycler(){
     /*   simpleQualityBeanList.clear();
        SimpleQualityBean bean1 = new SimpleQualityBean("酸碱度","pH",waterQualityDataBean.getPh());
        simpleQualityBeanList.add(bean1);
        SimpleQualityBean bean2 = new SimpleQualityBean("导电性","Conductivity",waterQualityDataBean.getConductivity());
        simpleQualityBeanList.add(bean2);
        SimpleQualityBean bean3 = new SimpleQualityBean("氨态氮","Ammonia nitrogen",waterQualityDataBean.getAmmonia_nitrogen());
        simpleQualityBeanList.add(bean3);
        SimpleQualityBean bean4 = new SimpleQualityBean("水温","Water temperature",waterQualityDataBean.getWater_temperature());
        simpleQualityBeanList.add(bean4);
        SimpleQualityBean bean5 = new SimpleQualityBean("溶解氧含量","Dissolved oxygen",waterQualityDataBean.getDissolved_oxygen());
        simpleQualityBeanList.add(bean5);
        SimpleQualityBean bean6 = new SimpleQualityBean("浊度","Ntu",waterQualityDataBean.getNtu());
        simpleQualityBeanList.add(bean6);
        SimpleQualityBean bean7 = new SimpleQualityBean("磷","P",waterQualityDataBean.getP());
        simpleQualityBeanList.add(bean7);
        */
    }
    private void DrawPieCharts(float a){//画饼图
        ArrayList<PieEntry> pieEntryList = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#BCEE68"));//淡绿色
        colors.add(Color.parseColor("#FFEC8B"));//鹅黄色
        //饼图实体 PieEntry
        //在这里只有这两个比例系数是需要加载的

        PieEntry CashBalance = new PieEntry(a, "溶解氧含量");
        PieEntry ConsumptionBalance = new PieEntry(100-a, "");
        pieEntryList.add(CashBalance);
        pieEntryList.add(ConsumptionBalance);
        //饼状图数据集 PieDataSet
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "");
        pieDataSet.setSliceSpace(3f);           //设置饼状Item之间的间隙
        pieDataSet.setSelectionShift(10f);      //设置饼状Item被选中时变化的距离
        pieDataSet.setColors(colors);           //为DataSet中的数据匹配上颜色集(饼图Item颜色)
        //最终数据 PieData
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);            //设置是否显示数据实体(百分比，true:以下属性才有意义)
        _dissolved_oxygenPie.setDrawEntryLabels(false);  //设置pieChart是否只显示饼图上百分比不显示文字
        //pieData.setValueTextColor(Color.parseColor("#68c6ba"));  //设置所有DataSet内数据实体（百分比）的文本颜色
        //pieData.setValueTextSize(12f);          //设置所有DataSet内数据实体（百分比）的文本字体大小
        //pieData.setValueTypeface(mTfLight);     //设置所有DataSet内数据实体（百分比）的文本字体样式

        _dissolved_oxygenPie.setCenterText(Float.toString(a));                 //设置PieChart内部圆文字的内容
        _dissolved_oxygenPie.setCenterTextSize(20f);                //设置PieChart内部圆文字的大小
        _dissolved_oxygenPie.setCenterTextColor(Color.parseColor("#68c6ba"));         //设置PieChart内部圆文字的颜色
        pieData.setValueFormatter(new PercentFormatter());//设置所有DataSet内数据实体（百分比）的文本字体格式
        _dissolved_oxygenPie.setData(pieData);
        _dissolved_oxygenPie.highlightValues(null);
        _dissolved_oxygenPie.getDescription().setEnabled(false);;
        _dissolved_oxygenPie.invalidate();                    //将图表重绘以显示设置的属性和数据
    }
}
