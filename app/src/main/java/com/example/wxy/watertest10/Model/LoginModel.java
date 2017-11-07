package com.example.wxy.watertest10.Model;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wxy.watertest10.Bean.MyApplication;
import com.example.wxy.watertest10.Bean.UserBean;

import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WXY on 2017/10/25.
 */

public class LoginModel extends AppCompatActivity implements ILoginModel {
    public static final int SHOW_RESPONSE = 1;
    public String response = null;
    private UserBean takeoutUserbean = new UserBean("","");
    String TAG = "LoginModel";


    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    response = (String) msg.obj ;
                    String data = "["+response+"]";
                    //Toast.makeText(MyApplication.getContext(), response, Toast.LENGTH_SHORT).show();
                   parseJSONWithJSONObject(data);
                     if(response.equals("false")){

                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void SendByHttpClient(final UserBean userBean) {
        takeoutUserbean.setUsername(userBean.getUsername());
        takeoutUserbean.setPassword(userBean.getPassword());
        Log.d("yeyeyyeye", "parseJSONWithJSONObject: " +userBean.getPassword());
        Log.d("yeyeyyeye", "parseJSONWithJSONObject: " +userBean.getUsername());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                      HttpClient httpclient=new DefaultHttpClient();
                      HttpPost httpPost=new HttpPost("http://120.55.47.216:8060/ideaWater02/UserController/login.do");
                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("username",userBean.getUsername()));
                    params.add(new BasicNameValuePair("password",userBean.getPassword()));
                    final UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
                      httpPost.setEntity(entity);
                    HttpResponse httpResponse= httpclient.execute(httpPost);
                    if(httpResponse.getStatusLine().getStatusCode()==200)
                    {
                        HttpEntity entity1=httpResponse.getEntity();
                        String response=EntityUtils.toString(entity1, "utf-8");
                        Message message=new Message();
                        message.what=SHOW_RESPONSE;
                        message.obj=response;
                        handler.sendMessage(message);
                    }/*
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://120.55.47.216:8060/ideaWater02/UserController/login.do")
                            .build();
                    Response response = client.newCall(request).execute();*/
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("isLogined");
                String pw = jsonObject.getString("isHaveRight");
                String ok = jsonObject.getString("isSucced");
                String msg = jsonObject.getString("msg");
              //String version = jsonObject.getString("version");
                Log.d("yeyeyyeye", "parseJSONWithJSONObject: " +id);
                Log.d("yeyeyyeye", "parseJSONWithJSONObject: " +pw);
                Log.d("yeyeyyeye", "parseJSONWithJSONObject: " +ok);
                Log.d("yeyeyyeye", "parseJSONWithJSONObject: " +msg);
                Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_SHORT).show();
                if(msg.equals("登录成功")){
                    SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("User", Context.MODE_PRIVATE).edit();//MODE_PRIVATE本应用私有文件
                        /*
                        * getSharedPreferences是依赖于上下文环境的，也就是context，
                        * 所以不管你在哪个类中，一定要通过activity类的context才能调用。
                        * 你可以这样，比如activity中实例化的你类，在new这个自定义类的时候，将activity的this当做参数传入，
                        * 类型是context，然后在自定义类中记录下来，context.getSharedPreferences()就可以在你的类中这样调用了。
                        * PS:在activity的setContextView之后再实例化自己的类，这样activity.this才不是空。
                        * */
                    editor.putString("Username",takeoutUserbean.getUsername());
                    editor.putString("Userpassword",takeoutUserbean.getPassword());
                    editor.apply();

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
