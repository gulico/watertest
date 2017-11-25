package com.example.wxy.watertest10.View.MainActivityFrament;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.MainActivity;
import com.example.wxy.watertest10.View.ShowDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2017/10/3.
 */

public class MapFragment extends Fragment implements View.OnClickListener{
    public LocationClient mLocationCient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private SearchView searchView;
    MainActivity acticity;
    private Marker marker;
    private MarkerOptions markerOptions;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        baiduMap.setMyLocationEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        acticity = (MainActivity)getActivity();
        //mLocationCient = new LocationClient(acticity.getApplicationContext());
        //mLocationCient.registerLocationListener(new MyLocationListener());
        List<String>permissionList = new ArrayList<>();
       /* if(ContextCompat.checkSelfPermission(acticity, Manifest.permission.ACCESS_FINE_LOCATION)){

        }*/
        SDKInitializer.initialize(acticity.getApplicationContext());
        View view = inflater.inflate(R.layout.mapframent,container,false);
        mapView =(MapView)view.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        searchView = (SearchView)view.findViewById(R.id.Search_Data);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//搜索框文字变化
            //点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                String data = null;
                if(query.equals("东湖")||query.equals("临安")||query.equals("donghu")) {
                    data = "D01";
                    Intent intent = new Intent(acticity, ShowDetailActivity.class);
                    intent.putExtra("instrumentId",data);
                    startActivity(intent);
                }else {
                    Toast.makeText(acticity,"没有此监测点",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            //搜索框文字变化
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        moveToCenter(30.261586,119.735591);//地图中心移动
        //drawMyLocation();
        drawMark();//画mark
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {//地图marker覆盖物点击监听
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                if (title != null) {
                    //Toast.makeText(acticity, title, Toast.LENGTH_SHORT).show();
                    String data = null;
                    data = "D01";
                    Intent intent = new Intent(acticity, ShowDetailActivity.class);//跳转到东湖页面
                    intent.putExtra("instrumentId",data);
                    startActivity(intent);
                } else {
                    Toast.makeText(acticity, "未知位置", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        return view;
    }
    private void requestLocation(){//开始定位
        mLocationCient.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    public class MyLocationListener implements BDLocationListener{//监听手机的位置变化
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

        }
    }

    private void moveToCenter(double x,double y){
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng(x,y));//东湖坐标
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//移动中心
        baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(18f));//缩放比[3,19]
        baiduMap.setMyLocationEnabled(true);//可以设置手机的位置
    }
    private void drawMyLocation(){
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(30.261586);
        locationBuilder.longitude(119.735591);
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
    private void drawMark(){
        // 构建Marker图标
        BitmapDescriptor bitmapDescriptor = null;
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.location_maker);
        //坐标信息
        LatLng point = null;
        double[][] testNum = {{30.261586,119.735591}};
        //标题
        String[] placeName = {"东湖"};
        Bundle bundle = new Bundle();
        for(int i = 0; i < testNum.length; ++i){
            bundle.clear();
            bundle.putString("placeName",placeName[i]);
            point =new LatLng(testNum[i][0],testNum[i][1]);
            //构建MarkerOption，用于在地图上添加Marker
            //.title()给覆盖物添加标题
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmapDescriptor).title(placeName[i]);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
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
