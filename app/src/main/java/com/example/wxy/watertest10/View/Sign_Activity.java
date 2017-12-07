package com.example.wxy.watertest10.View;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Bean.ResolutionUtil;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.ShowDetailFrament.SignDialogFragment;
import com.example.wxy.watertest10.presenter.SignAdapter;
import com.example.wxy.watertest10.presenter.SignEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Sign_Activity extends BaseActivity{
    private TextView tvSignDay;
    private TextView tvScore;
    private TextView tvYear;
    private TextView tvMonth;
    private com.example.wxy.watertest10.View.SignView signView;
    private AppCompatButton btnSign;
    private List<SignEntity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        initView();
        onReady();
    }

    private void initView() {
        tvSignDay = (TextView) findViewById(R.id.activity_main_tv_main_day);
        tvScore = (TextView) findViewById(R.id.activity_main_tv_score);
        tvYear = (TextView) findViewById(R.id.activity_main_tv_year);
        tvMonth = (TextView) findViewById(R.id.activity_main_tv_month);
        signView = (com.example.wxy.watertest10.View.SignView) findViewById(R.id.activity_main_cv);
        btnSign = (AppCompatButton) findViewById(R.id.activity_main_btn_sign);
        if (signView != null) {
            signView.setOnTodayClickListener(onTodayClickListener);
        }
        if (btnSign != null) {
            //noinspection deprecation
            btnSign.setSupportBackgroundTintList(getResources().getColorStateList(R.color.color_user_button_submit));
            btnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSign();
                }
            });
        }

        //---------------------------------分辨率适配----------------------------------
        ResolutionUtil resolutionUtil = ResolutionUtil.getInstance();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.topMargin = resolutionUtil.formatVertical(40);
        tvSignDay.setLayoutParams(layoutParams);
        tvSignDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(42));

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.topMargin = resolutionUtil.formatVertical(40);
        tvScore.setLayoutParams(layoutParams);
        tvScore.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(95));

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resolutionUtil.formatVertical(130));
        layoutParams.topMargin = resolutionUtil.formatVertical(54);
        View llDate = findViewById(R.id.activity_main_ll_date);
        if (llDate != null) {
            llDate.setLayoutParams(layoutParams);
        }

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = resolutionUtil.formatHorizontal(43);
        tvYear.setLayoutParams(layoutParams);
        tvYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(43));

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = resolutionUtil.formatHorizontal(44);
        tvMonth.setLayoutParams(layoutParams);
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(43));

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resolutionUtil.formatVertical(818));
        signView.setLayoutParams(layoutParams);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resolutionUtil.formatVertical(142));
        layoutParams.topMargin = resolutionUtil.formatVertical(111);
        layoutParams.leftMargin = layoutParams.rightMargin = resolutionUtil.formatHorizontal(42);
        if (btnSign != null) {
            btnSign.setLayoutParams(layoutParams);
            btnSign.setTextSize(TypedValue.COMPLEX_UNIT_PX, resolutionUtil.formatVertical(54));
        }
    }

    private void onReady() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        tvSignDay.setText(Html.fromHtml(String.format(getString(R.string.you_have_sign), "#999999", "#1B89CD", 3)));
        tvScore.setText(String.valueOf(3015));
        tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        tvMonth.setText(getResources().getStringArray(R.array.month_array)[month]);

        Calendar calendarToday = Calendar.getInstance();
        int dayOfMonthToday = calendarToday.get(Calendar.DAY_OF_MONTH);

        data = new ArrayList<>();
        Random ran = new Random();
        for (int i = 0; i < 30; i++) {
            SignEntity signEntity = new SignEntity();
            if (dayOfMonthToday == i + 1)
                signEntity.setDayType(2);
            else
                signEntity.setDayType((ran.nextInt(1000) % 2 == 0) ? 0 : 1);
            data.add(signEntity);
        }
        SignAdapter signAdapter = new SignAdapter(data);
        signView.setAdapter(signAdapter);
    }

    private void onSign() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SignDialogFragment signDialogFragment = SignDialogFragment.newInstance(15);
        signDialogFragment.setOnConfirmListener(onConfirmListener);
        signDialogFragment.show(fragmentManager, SignDialogFragment.TAG);
    }

    private void signToday() {
        data.get(signView.getDayOfMonthToday() - 1).setDayType(com.example.wxy.watertest10.View.SignView.DayType.SIGNED.getValue());
        signView.notifyDataSetChanged();
        btnSign.setEnabled(false);
        btnSign.setText(R.string.have_signed);

        int score = Integer.valueOf((String) tvScore.getText());
        tvScore.setText(String.valueOf(score + 15));
    }

    private com.example.wxy.watertest10.View.SignView.OnTodayClickListener onTodayClickListener = new com.example.wxy.watertest10.View.SignView.OnTodayClickListener() {
        @Override
        public void onTodayClick() {
            onSign();
        }
    };

    private SignDialogFragment.OnConfirmListener onConfirmListener = new SignDialogFragment.OnConfirmListener() {
        @Override
        public void onConfirm() {
            signToday();
        }
    };
}
