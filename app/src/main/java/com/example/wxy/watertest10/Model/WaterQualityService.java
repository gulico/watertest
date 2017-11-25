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
        //String dateTime = "2017-04-21 02:24:25.0";


//        List<WaterQualityDataBean> waters= DataSupport.select("Time").find(WaterQualityDataBean.class);

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
                double instrumentID = Double.parseDouble(instrumentId);
                String ph = jsonObject.getString("ph");//ph
                double PH = Double.parseDouble(ph);
                String dissolvedOxygen = jsonObject.getString("dissolvedOxygen");//含氧
                double DissolvedOxygen = Double.parseDouble(dissolvedOxygen);
                String ammoniaNitrogen = jsonObject.getString("ammoniaNitrogen");//氨氮
                double AmmoniaNitrogen = Double.parseDouble(ammoniaNitrogen);
                String conductivity = jsonObject.getString("conductivity");//传导率
                double Conductivity = Double.parseDouble(conductivity);
                String ntu = jsonObject.getString("ntu");//浊度
                double Ntu = Double.parseDouble(ntu);
                String p = jsonObject.getString("p");//磷
                double P = Double.parseDouble(p);
                String waterTemperature = jsonObject.getString("waterTemperature");//水温
                double WaterTemperature = Double.parseDouble(waterTemperature);
              //  Log.d("yxd", "parseJSONWithJSONObject: " + dateTime);
               if(instrumentId.equals("D01")){
                    //if(dateTime!=jsonArray.getJSONObject(i-1).getString("dateTime")) {
                        WaterQualityDataBean water = new WaterQualityDataBean();
                   //          2017-04-19 22:38:19.0
                   String []test1=dateTime.split(" ");
                    //Log.d("sdsa", "parseJSONWithJSONObject: "+test1[1]);
                   // Log.d("xudongwudi", "onStartCommand: "+test2[0]);
                   // Log.d("xudongwudi", "onStartCommand: "+test2[1]);
                   String []test2=test1[1].split(":");

                   double hour = Double.parseDouble(test2[0]);
                   double minuteSix = Double.parseDouble(test2[1]);
                    double minuteTen = minuteSix / 100 * 60;
                        water.setHour(hour);
                        water.setMinute(minuteTen);
                        water.setTime(test1[0]);
                        water.setPh(PH);
                        water.setDissolved_oxygen(DissolvedOxygen);
                        water.setNtu(Ntu);
                        water.setAmmonia_nitrogen(AmmoniaNitrogen);
                        water.setConductivity(Conductivity);
                        water.setP(P);
                        water.setWater_temperature(WaterTemperature);
                        water.save();
                   // }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
