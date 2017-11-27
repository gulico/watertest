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
        List<WaterQualityDataBean> Waters = DataSupport.findAll(WaterQualityDataBean.class);
        //String dateTime = "2017-04-21 02:24:25.0";
     //   for(WaterQualityDataBean Water:Waters){
       //     Log.d("xudi", "onStartCommand: "+Water.getMinute());
        //}

//        List<WaterQualityDataBean> waters= DataSupport.select("Time").find(WaterQualityDataBean.class);

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        DataSupport.deleteAll(WaterQualityDataBean.class);
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
            double Ten = -10;
            int flag = 1;
            for(int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String dateTime = jsonObject.getString("dateTime");//测试时间
                String instrumentId = jsonObject.getString("instrumentId");//测式机型号
                if (instrumentId.equals("D01")) {

                //   double instrumentID = Double.parseDouble(instrumentId);
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


                String[] test1 = dateTime.split(" ");
                //  Log.d("sdsa", "parseJSONWithJSONObject: "+test1[0]);
                // Log.d("xudongwudi", "onStartCommand: "+test2[0]);
                // Log.d("xudongwudi", "onStartCommand: "+test2[1]);
                String[] test2 = test1[1].split(":");
                //  Log.d("sdsa", "parseJSONWithJSONObject: "+test2[1]);
                //  Log.d("xudongwudi", "onStartCommand: "+test2[1]);
                double hour = Double.parseDouble(test2[0]);//小时
                double minuteSix = Double.parseDouble(test2[1]);//分钟

                double minuteTen = minuteSix / 60 * 1000;

                if (flag == 1 && i != 0) {
                    Ten = minuteSix;
                    flag = 0;
                }

                double minuteTenCheck = 0;
                double minuteSixCheck = 0;
                    JSONObject jsonObjectCheck = jsonArray.getJSONObject(i - 1);
                    String dateTimeCheck = jsonObjectCheck.getString("dateTime");
                    String[] test1Check = dateTimeCheck.split(" ");
                if(i >= 1) {

                    //Log.d("sdsa", "parseJSONWithJSONObject: "+test1Check[0]);
                    // Log.d("xudongwudi", "onStartCommand: "+test2[0]);
                    // Log.d("xudongwudi", "onStartCommand: "+test2[1]);
                    String[] test2Check = test1Check[1].split(":");
                   // Log.d("sdsa", "parseJSONWithJSONObject: " + test1Check[1]);
                    //  Log.d("xudongwudi", "onStartCommand: "+test2Check[1]);
                    double hourCheck = Double.parseDouble(test2Check[0]);
                    minuteSixCheck = Double.parseDouble(test2Check[1]);
                     minuteTenCheck = minuteSixCheck / 100 * 60;
                }


                if (test1Check[0].equals(test1[0]) && flag == 0 && ( Ten-minuteSix) >= 10) {
                    flag = 1;
                    Log.d("sasasg", "parseJSONWithJSONObject: " + (Ten-minuteSix )+"Ten : "+ Ten + "minSix : "+minuteSix);

                    Ten = minuteSix;

                } else if(!test1Check[0].equals(test1[0]) || minuteSix >= minuteSixCheck)
                {
                    Ten = minuteSix;
                }
                else
                    continue;
                //  Log.d("yxd", "parseJSONWithJSONObject: " + dateTime);
                if (instrumentId.equals("D01") && flag == 1) {
                    //if(dateTime!=jsonArray.getJSONObject(i-1).getString("dateTime")) {
                    WaterQualityDataBean water = new WaterQualityDataBean();
                    //          2017-04-19 22:38:19.0
                    water.setCity(instrumentId);
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
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
