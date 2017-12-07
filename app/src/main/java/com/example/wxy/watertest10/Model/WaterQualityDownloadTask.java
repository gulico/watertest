package com.example.wxy.watertest10.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YXD on 2017/11/30.
 */

public class WaterQualityDownloadTask extends AsyncTask<Object, Object, Integer> {
    private static final int TYPE_SUCCESS = 0;
    private IWaterServiceListener listener;
    private boolean success = false;
    public WaterQualityDownloadTask(IWaterServiceListener listener){
        this.listener = listener;
    }


    @Override
    protected Integer doInBackground(Object... params) {
        InputStream is = null;
        RandomAccessFile saveFile =null;
        File file = null;
        try{
            //DataSupport.deleteAll(WaterQualityDataBean.class);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://120.55.47.216:8060/ideaWater02/YascmfDatasController/findYascmfDatasByPojo.do?"+"instrumentId=D01")
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            //parseJSONWithJSONObject(responseData);

            JSONArray jsonArray = new JSONArray(responseData);

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

                    double minuteTen = minuteSix / 60 * 100;

                    if (flag == 1 && i != 0) {
                        Ten = minuteSix;
                        flag = 0;
                    }

                    double minuteTenCheck = 0;
                    double minuteSixCheck = 0;
                    if(i != 0) {
                        JSONObject jsonObjectCheck = jsonArray.getJSONObject(i - 1);
                        String dateTimeCheck = jsonObjectCheck.getString("dateTime");
                        String[] test1Check = dateTimeCheck.split(" ");
                        if (i >= 1) {

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


                        if (test1Check[0].equals(test1[0]) && flag == 0 && (Ten - minuteSix) >= 10) {
                            flag = 1;
                            Log.d("sasasg", "parseJSONWithJSONObject: " + (Ten - minuteSix) + "Ten : " + Ten + "minSix : " + minuteSix);

                            Ten = minuteSix;

                        } else if (!test1Check[0].equals(test1[0]) || minuteSix >= minuteSixCheck) {
                            Ten = minuteSix;
                        } else
                            continue;
                    }
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

        }catch (Exception e) {
            e.printStackTrace();
        }
      return TYPE_SUCCESS;
    }

    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccsess();

                break;
            default:
                break;
        }
    }


}
