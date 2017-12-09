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
    List<Entry> loadwater_temperature(String Time);
    List<Entry> loadammonia_nitrogen(String Time);
    List<Entry> loadp(String Time);
    List<Entry> loadconductivity(String Time);
    List<Entry> loaddissolved_oxygen(String Time);
    List<Entry> loadntu(String Time);
}
