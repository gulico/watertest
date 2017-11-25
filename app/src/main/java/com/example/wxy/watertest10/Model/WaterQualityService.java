package com.example.wxy.watertest10.Model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WaterQualityService extends Service {
    public WaterQualityService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }

    //每次服务启动的时候立即执行
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("yxd", "onStartCommand: " + "服务可以执行");
        sendRequestWithOkHttp();
       // List<WaterQualityDataBean> Waters = DataSupport.findAll(WaterQualityDataBean.class);
       WaterQualityDataBean water = null;
        List<WaterQualityDataBean> waters= DataSupport.findAll(WaterQualityDataBean.class);
for(WaterQualityDataBean waterl:waters) {
    Log.d("xiaotiancai", "Time: " + waterl.getTime());
    Log.d("xiaotiancai", "nitrogen: " + waterl.getAmmonia_nitrogen());
    Log.d("xiaotiancai", "Conductivity: " + waterl.getConductivity());
    Log.d("xiaotiancai", "Ntu: " + waterl.getNtu());
    Log.d("xiaotiancai", "P: " + waterl.getP());
    Log.d("xiaotiancai", "oxygen: " + waterl.getDissolved_oxygen());
    Log.d("xiaotiancai", "temperature: " + waterl.getWater_temperature());
    Log.d("xiaotiancai", "Ph: " + waterl.getPh());
}

        //for (WaterQualityDataBean Water: Waters){
         //   Log.d("wxy", "onStartCommand: " + Water.getTime());
        //}
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://120.55.47.216:8060/ideaWater02/YascmfDatasController/findYascmfDatasByPojo.do")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{

           //
            JSONArray jsonArray = new JSONArray(jsonData);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String dateTime = jsonObject.getString("dateTime");//测试时间
                String instrumentId = jsonObject.getString("instrumentId");//测式机型号
                String ph = jsonObject.getString("ph");//ph
                String dissolvedOxygen = jsonObject.getString("dissolvedOxygen");//含氧
                String ammoniaNitrogen = jsonObject.getString("ammoniaNitrogen");//氨氮
                String conductivity = jsonObject.getString("conductivity");//传导率
                String ntu = jsonObject.getString("ntu");//浊度
                String p = jsonObject.getString("p");//磷
                String waterTemperature = jsonObject.getString("waterTemperature");//水温
              //  Log.d("yxd", "parseJSONWithJSONObject: " + dateTime);
               if(instrumentId.equals("D01")){
                    //if(dateTime!=jsonArray.getJSONObject(i-1).getString("dateTime")) {
                        WaterQualityDataBean water = new WaterQualityDataBean();
                        water.setTime(dateTime);
                        water.setPh(ph);
                        water.setDissolved_oxygen(dissolvedOxygen);
                        water.setNtu(ntu);
                        water.setAmmonia_nitrogen(ammoniaNitrogen);
                        water.setConductivity(conductivity);
                        water.setP(p);
                        water.setWater_temperature(waterTemperature);
                        water.save();
                   // }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
