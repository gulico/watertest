package com.example.wxy.watertest10.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wxy.watertest10.Bean.UserBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.View.LoginActivity;
import com.example.wxy.watertest10.View.MainActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WXY on 2017/10/25.
 */

public class LoginModel implements ILoginModel {
    public static final int SHOW_RESPONSE = 1;
    public String response = null;
    private UserBean takeoutUserbean = null;

       /* if(!userBean.getUsername().equals("wxy")){

            //用不存在
            return -1;
        }
        else if (!userBean.getPassword().equals("123123")) {
            //用户存在，但，密码错误
            return 0;
        } else {
            //将数据存到本地SharePreferences
            SharedPreferences.Editor editor = activityContext.getSharedPreferences("User", Context.MODE_PRIVATE).edit();//MODE_PRIVATE本应用私有文件
            /*
            * getSharedPreferences是依赖于上下文环境的，也就是context，
            * 所以不管你在哪个类中，一定要通过activity类的context才能调用。
            * 你可以这样，比如activity中实例化的你类，在new这个自定义类的时候，将activity的this当做参数传入，
            * 类型是context，然后在自定义类中记录下来，context.getSharedPreferences()就可以在你的类中这样调用了。
            * PS:在activity的setContextView之后再实例化自己的类，这样activity.this才不是空。
            * */
          /*  editor.putString("Username",takeoutUserbean.getUsername());
            editor.putString("Userpassword",takeoutUserbean.getPassword());
            editor.apply();

            return 1;
        }*/

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    response = (String) msg.obj;
                    if(response.equals("true")){
                        //SharedPreferences.Editor editor = activityContext.getSharedPreferences("User", Context.MODE_PRIVATE).edit();//MODE_PRIVATE本应用私有文件
                        /*
                        * getSharedPreferences是依赖于上下文环境的，也就是context，
                        * 所以不管你在哪个类中，一定要通过activity类的context才能调用。
                        * 你可以这样，比如activity中实例化的你类，在new这个自定义类的时候，将activity的this当做参数传入，
                        * 类型是context，然后在自定义类中记录下来，context.getSharedPreferences()就可以在你的类中这样调用了。
                        * PS:在activity的setContextView之后再实例化自己的类，这样activity.this才不是空。
                        * */
                        //editor.putString("Username",takeoutUserbean.getUsername());
                        //editor.putString("Userpassword",takeoutUserbean.getPassword());
                        //editor.apply();
                    }
                    else if(response.equals("false")){

                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void SendByHttpClient(final UserBean userBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://192.168.204.169:8080/Login");
                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id",userBean.getUsername()));
                    params.add(new BasicNameValuePair("pw",userBean.getPassword()));
                    final UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");
                    httpPost.setEntity(entity);
                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    takeoutUserbean.setUsername(userBean.getUsername());
                    takeoutUserbean.setPassword(userBean.getPassword());
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity entity1 = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity1, "utf-8");
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
