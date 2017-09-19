package com.example.wxy.watertest10.presenter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wxy.watertest10.R;

import com.example.wxy.watertest10.Bean.SimpleQualityBean;

import java.util.List;

/**
 * Created by WXY on 2017/9/18.
 */

public class SimpleQualityRecyclerAdapter extends RecyclerView.Adapter<SimpleQualityRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<SimpleQualityBean> msimpleQualityBean;

    static public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        TextView Ename;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            name = (TextView)itemView.findViewById(R.id.simplequality_name);
            Ename = (TextView)itemView.findViewById(R.id.simplequality_Ename);
            date = (TextView)itemView.findViewById(R.id.simplequality_date);
        }
    }
    public SimpleQualityRecyclerAdapter(List<SimpleQualityBean> simpleQualityBean){
        this.msimpleQualityBean = simpleQualityBean;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.simplequalitycard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleQualityBean simpleQualityBean = msimpleQualityBean.get(position);
        holder.name.setText(simpleQualityBean.getName());
        holder.Ename.setText(simpleQualityBean.getEname());
        holder.date.setText(Double.toString(simpleQualityBean.getDate()) );
    }

    @Override
    public int getItemCount() {
        return msimpleQualityBean.size();
    }


}
