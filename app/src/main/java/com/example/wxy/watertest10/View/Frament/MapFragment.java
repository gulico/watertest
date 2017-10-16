package com.example.wxy.watertest10.View.Frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by WXY on 2017/10/3.
 */

public class MapFragment extends Fragment {
    public LocationClient mLocationCient;
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MainActivity acticity = (MainActivity)getActivity();
        //mLocationCient = new LocationClient(acticity.getApplicationContext());
        //mLocationCient.registerLocationListener(new MyLocationListener());



        List<String>permissionList = new ArrayList<>();
       /* if(ContextCompat.checkSelfPermission(acticity, Manifest.permission.ACCESS_FINE_LOCATION)){

        }*/
        SDKInitializer.initialize(acticity.getApplicationContext());
        View view = inflater.inflate(R.layout.mapframent,container,false);
        mapView =(MapView)view.findViewById(R.id.bmapView);

        return view;
    }
    private void requestLocation(){//开始定位
        mLocationCient.start();
    }
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
