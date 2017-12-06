package com.example.wxy.watertest10.presenter;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2017/11/26.
 */

public interface IShowdetailDataPersenter {
    void loadData();
    List<String> loadAllTimes();
    List<Entry> loadPh(String Time);
}
