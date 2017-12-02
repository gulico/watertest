package com.example.wxy.watertest10.Model;

/**
 * Created by WXY on 2017/9/12.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.MyApplication;
import com.example.wxy.watertest10.Bean.UserBean;
import com.example.wxy.watertest10.Bean.WaterQualityDataBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 数据存储的模型层，只需要考虑怎么把数据存起来
 * 以及数据的取出
 */
public class WaterQualityDataModel extends AppCompatActivity implements IWaterQualityDataModel{
    public void saveWaterQualityData(){//保存数据到数据库

    }
    public WaterQualityDataBean loadWaterQualityData(String prefix){//从数据库取出数据，传入地域前缀
        WaterQualityDataBean waterQualityDataBean = DataSupport.findLast(WaterQualityDataBean.class);
        return waterQualityDataBean;
    }


}
