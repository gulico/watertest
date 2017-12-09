package com.example.wxy.watertest10.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.WaterQualityDataBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.MainActivity;
import com.example.wxy.watertest10.View.SplashActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.id.progress;
import static android.content.ContentValues.TAG;

public class WaterQualityService extends Service {

    private WaterQualityDownloadTask waterTask;
    private String waterurl;
    private IWaterServiceListener listener = new IWaterServiceListener() {
        @Override
        public void onSuccsess() {
            waterTask = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Down Success",progress));
            WaterQualityDataBean firstWater = DataSupport.findFirst(WaterQualityDataBean.class);
            Log.d("yxd", "onSuccsess: "+ firstWater.getHour());
            Log.d("yxd", "onSuccsess: "+firstWater.getPh());
            Toast.makeText(WaterQualityService.this,"下载完成！",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AppManager.currentActivity(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            AppManager.finishActivity(SplashActivity.class);
        }

    };
    private WaterQualityBinder mBinder = new WaterQualityBinder();
    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }
    public class WaterQualityBinder extends Binder{
        public void StartQualityDownload(){
                waterTask = new WaterQualityDownloadTask(listener);
                waterTask.execute();
                startForeground(1,getNotification("Downing",0));
        }
    }
    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title, int progress){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        return builder.build();
    }




/*
    @Override
    public IBinder onBind(Intent intent) {

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

*/




}
