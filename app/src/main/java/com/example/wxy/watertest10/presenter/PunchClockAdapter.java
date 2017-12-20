package com.example.wxy.watertest10.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wxy.watertest10.Bean.PunchClockSimpleBean;
import com.example.wxy.watertest10.R;

import java.util.List;

/**
 * Created by WXY on 2017/12/20.
 */

public class PunchClockAdapter extends RecyclerView.Adapter<PunchClockAdapter.ViewHolder> {
    private List<PunchClockSimpleBean> mPunchClockSilmpeList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView TimeType_icon;//上班或者下班的图片
        TextView Time;//时间
        TextView Data;//日期
        ImageView WorkType;//正常、迟到、早退

        public ViewHolder(View itemView) {
            super(itemView);
            TimeType_icon = (ImageView) itemView.findViewById(R.id.TimeType_icon);
            Time = (TextView) itemView.findViewById(R.id.Time);
            Data = (TextView) itemView.findViewById(R.id.Data);
            WorkType = (ImageView) itemView.findViewById(R.id.WorkType);
        }
    }

    public PunchClockAdapter(List<PunchClockSimpleBean> PunchClockSilmpeList) {
        mPunchClockSilmpeList = PunchClockSilmpeList;
    }

    @Override
    public PunchClockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.punchclock_middle_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PunchClockAdapter.ViewHolder holder, int position) {
        PunchClockSimpleBean punchClockSimpleBean = mPunchClockSilmpeList.get(position);
        if (punchClockSimpleBean.getTimeType().equals("上班")) {
            holder.TimeType_icon.setImageResource(R.drawable.towork_icon);
            holder.Time.setText("上班时间：" + punchClockSimpleBean.getTime());
        } else if (punchClockSimpleBean.getTimeType().equals("下班")) {
            holder.TimeType_icon.setImageResource(R.drawable.getoff_icon);
            holder.Time.setText("下班时间：" + punchClockSimpleBean.getTime());
        }
        holder.Data.setText(punchClockSimpleBean.getData());
        if (punchClockSimpleBean.getWorkType().equals("正常")) {
            holder.WorkType.setImageResource(R.drawable.type_noamal);
        } else if (punchClockSimpleBean.getWorkType().equals("迟到")) {
            holder.WorkType.setImageResource(R.drawable.type_late);
        } else if (punchClockSimpleBean.getWorkType().equals("早退")) {
            holder.WorkType.setImageResource(R.drawable.type_early);
        }

    }

    @Override
    public int getItemCount() {
        return mPunchClockSilmpeList.size();
    }
}
