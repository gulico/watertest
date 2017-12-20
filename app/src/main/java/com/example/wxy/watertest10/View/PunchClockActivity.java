package com.example.wxy.watertest10.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Bean.PunchClockBean;
import com.example.wxy.watertest10.Bean.PunchClockSimpleBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.presenter.PunchClockAdapter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PunchClockActivity extends BaseActivity implements View.OnClickListener {

    private List<PunchClockSimpleBean> mPunchClockSilmpeList = new ArrayList<>();
    ImageView back;
    Button punch_btn;
    int PUNCH_TYPE = 0;//0未上班打卡，1未下班打卡 ，2以下班
    String today;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_clock);
        initView();
        initDate();
        recyclerView = (RecyclerView) findViewById(R.id.recyclervView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PunchClockAdapter adapter = new PunchClockAdapter(mPunchClockSilmpeList);
        recyclerView.setAdapter(adapter);
    }

    public void initView() {//初始化布局
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        punch_btn = (Button) this.findViewById(R.id.punch_btn);
        punch_btn.setOnClickListener(this);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");//获取今天的日期
        today = sDateFormat.format(new java.util.Date());
        List<PunchClockBean> punchClockBeens = DataSupport.where("Data = ?", today).find(PunchClockBean.class);
        if (!punchClockBeens.isEmpty()) {//今天已经有打卡了
            if (punchClockBeens.get(0).getGetOffTime() == null) {//还没有打卡下班
                punch_btn.setText("打卡下班");
                PUNCH_TYPE = 1;
            } else {//已经打卡下班
                PUNCH_TYPE = 2;
                punch_btn.setText("辛苦了，好好休息");
                punch_btn.setClickable(false);//禁用按钮
            }
        }
    }

    public void initDate() {
        mPunchClockSilmpeList.clear();
        PunchClockSimpleBean a1 = new PunchClockSimpleBean("9:50", "上班", "迟到", "2017-12-18");
        mPunchClockSilmpeList.add(a1);
        PunchClockSimpleBean b1 = new PunchClockSimpleBean("16:30", "下班", "正常", "2017-12-18");
        mPunchClockSilmpeList.add(b1);
        PunchClockSimpleBean a = new PunchClockSimpleBean("9:00", "上班", "正常", "2017-12-19");
        mPunchClockSilmpeList.add(a);
        PunchClockSimpleBean b = new PunchClockSimpleBean("13:45", "下班", "早退", "2017-12-19");
        mPunchClockSilmpeList.add(b);
        List<PunchClockBean> punchClockBeanList = DataSupport.findAll(PunchClockBean.class);
        for(PunchClockBean punchClockBean:punchClockBeanList){
            PunchClockSimpleBean x1 = new PunchClockSimpleBean(punchClockBean.getToWorkTime(), "上班", punchClockBean.getToWorkType(),punchClockBean.getData());
            mPunchClockSilmpeList.add(x1);
            if(punchClockBean.getGetOffTime()==null)
                continue;
            PunchClockSimpleBean x2 = new PunchClockSimpleBean(punchClockBean.getGetOffTime(), "下班", punchClockBean.getGetOffType(),punchClockBean.getData());
            mPunchClockSilmpeList.add(x2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.punch_btn:
                ClickPunchButton();
                break;
            default:
        }
    }

    public void ClickPunchButton() {
        switch (PUNCH_TYPE) {
            case 0:
                PUNCH_TYPE = 1;//点击之后已上班，未下班
                PunchClockBean punchClockBean = new PunchClockBean();
                punchClockBean.setData(today);
                Time time = new Time();
                time.setToNow();
                int hour = time.hour;
                int minute = time.minute;
                String hourstr = null;
                String minutestr = null;
                if (hour > 9) {
                    hourstr = hour + "";
                } else {
                    hourstr = "0" + hour;
                }
                if (minute > 9) {
                    minutestr = hour + "";
                } else {
                    minutestr = "0" + hour;
                }
                punchClockBean.setToWorkTime(hourstr + ":" + minutestr);
                if (hour > 9) {
                    punchClockBean.setToWorkType("迟到");
                } else {
                    punchClockBean.setToWorkType("正常");
                }
                punchClockBean.setUsername("wangzi");
                punchClockBean.save();
                punch_btn.setText("打卡下班");
                initDate();
                PunchClockAdapter adapter = new PunchClockAdapter(mPunchClockSilmpeList);
                recyclerView.setAdapter(adapter);
                break;
            case 1:
                PUNCH_TYPE = 2;//已下班
                punch_btn.setText("辛苦了，好好休息");
                punch_btn.setClickable(false);//禁用按钮
                PunchClockBean punchClockBean2 = new PunchClockBean();
                Time time2 = new Time();
                time2.setToNow();
                int hour2 = time2.hour;
                int minute2 = time2.minute;
                String hourstr2 = null;
                String minutestr2 = null;
                if (hour2 > 9) {
                    hourstr2 = hour2 + "";
                } else {
                    hourstr2 = "0" + hour2;
                }
                if (minute2 > 9) {
                    minutestr2 = hour2 + "";
                } else {
                    minutestr2 = "0" + hour2;
                }
                punchClockBean2.setGetOffTime(hourstr2 + ":" + minutestr2);
                if (hour2 > 17 && minute2 > 30) {
                    punchClockBean2.setGetOffType("正常");
                } else {
                    punchClockBean2.setGetOffType("早退");
                }
                punchClockBean2.updateAll("Data = ? and username = ?",today,"wangzi");
                initDate();
                PunchClockAdapter adapter2 = new PunchClockAdapter(mPunchClockSilmpeList);
                recyclerView.setAdapter(adapter2);
                break;
            default:
        }
    }
}
