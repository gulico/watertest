package com.example.wxy.watertest10.View.MainActivityFrament;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wxy.watertest10.Bean.News;
import com.example.wxy.watertest10.Bean.SimpleQualityBean;
import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.IMainActivity;
import com.example.wxy.watertest10.presenter.IMainUiPersenter;
import com.example.wxy.watertest10.presenter.MainUiPresenter;
import com.example.wxy.watertest10.presenter.NewsAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by WXY on 2017/10/3.
 */

public class HomeFragment extends Fragment implements IMainActivity {
    WaterQualityDataBean waterQualityDataBean;
    IMainUiPersenter iMainUiPersenter;
    private  PieChart _dissolved_oxygenPie;//溶解氧饼图
    private TextView ph;//ph值
    private TextView conductivity;//导电性
    private TextView water_temperature;//水温
    private TextView ammonia_nitrogen;//氨态氮
    private TextView dissolved_oxygen;//溶解氧含量
    private TextView ntu;//浊度
    private TextView p;//磷
    private Handler handler;
    private TextView uvtext;
    private TextView gmtext;
    private TextView cytext;
    private TextView xctext;
    private TextView kstext;
    private String uv = new String("");//紫外线
    private String gm = new String("");//感冒
    private String cy = new String("");//穿衣
    private String xc = new String("");//洗车
    private String ks = new String("");//扩散

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homeframent, container, false);
        iMainUiPersenter = new MainUiPresenter(this);
        _dissolved_oxygenPie = (PieChart) view.findViewById(R.id.dissolved_oxygenPie);
        ph = (TextView) view.findViewById(R.id.ph);
        conductivity = (TextView) view.findViewById(R.id.conductivity);
        water_temperature = (TextView) view.findViewById(R.id.water_temperature);
        ammonia_nitrogen = (TextView) view.findViewById(R.id.ammonia_nitrogen);
        dissolved_oxygen = (TextView) view.findViewById(R.id.dissolved_oxygen);
        ntu = (TextView) view.findViewById(R.id.ntu);
        p = (TextView) view.findViewById(R.id.p);
        uvtext = (TextView)view.findViewById(R.id.uv);
        gmtext = (TextView)view.findViewById(R.id.gm);
        cytext = (TextView)view.findViewById(R.id.cy);
        xctext = (TextView)view.findViewById(R.id.xc);
        kstext = (TextView)view.findViewById(R.id.ks);
        getsuggest();
        loadData();//加载数据
        setdata();
        DrawPieCharts((float) waterQualityDataBean.getDissolved_oxygen());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    uvtext.setText(uv);
                    gmtext.setText(gm);
                    cytext.setText(cy);
                    xctext.setText(xc);
                    kstext.setText(ks);
                }
            }
        };
        return view;
    }

    public void saveDate() {//保存界面上获取的数据
    }

    public void loadData() {//取出p层获得的数据反应到ui上
        iMainUiPersenter.loadDate();
    }

    public String getPrefix() {//让p层获取默认地区的检测数据
        return "D01";
    }

    public void setBean(WaterQualityDataBean waterQualityDataBean) {//本层获取数据
        this.waterQualityDataBean = waterQualityDataBean;
    }

    public void setdata() {
        ph.setText(waterQualityDataBean.getPh() + "");
        conductivity.setText(waterQualityDataBean.getConductivity() + "");
        water_temperature.setText(waterQualityDataBean.getWater_temperature() + "");
        ammonia_nitrogen.setText(waterQualityDataBean.getAmmonia_nitrogen() + "");
        dissolved_oxygen.setText(waterQualityDataBean.getDissolved_oxygen() + "");
        ntu.setText(waterQualityDataBean.getNtu() + "");
        p.setText(waterQualityDataBean.getP() + "");
    }

    private void DrawPieCharts(float a) {//画饼图
        ArrayList<PieEntry> pieEntryList = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#BCEE68"));//淡绿色
        colors.add(Color.parseColor("#FFEC8B"));//鹅黄色
        //饼图实体 PieEntry
        //在这里只有这两个比例系数是需要加载的

        PieEntry CashBalance = new PieEntry(a, "溶解氧含量");
        PieEntry ConsumptionBalance = new PieEntry(100 - a, "");
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
        _dissolved_oxygenPie.getDescription().setEnabled(false);
        ;
        _dissolved_oxygenPie.invalidate();                    //将图表重绘以显示设置的属性和数据
    }

    private void getsuggest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取中国天气杭州生活指数，紫外线、感冒、洗车、污染
                    Document doc = Jsoup.connect("http://www.weather.com.cn/weather/101210101.shtml").get();
                    Elements uvvalue = doc.getElementsByClass("li1");//紫外线
                    uv = uvvalue.get(0).select("p").text();
                    Elements gmvalue = doc.getElementsByClass("li2");//感冒
                    gm = gmvalue.get(0).select("p").text();
                    Elements cyvalue = doc.getElementsByClass("li3 hot");//穿衣
                    cy = cyvalue.get(0).select("p").text();
                    Elements xcvalue = doc.getElementsByClass("li4");//洗车指数
                    xc = xcvalue.get(0).select("p").text();
                    Elements ksvalue = doc.getElementsByClass("li6");//空气扩散
                    ks = ksvalue.get(0).select("p").text();
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
