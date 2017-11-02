package com.example.wxy.watertest10.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.wxy.watertest10.Model.ILoginModel;
import com.example.wxy.watertest10.Model.LoginModel;
import com.example.wxy.watertest10.View.ILoginActivity;

/**
 * Created by WXY on 2017/10/25.
 */

public class Loginpresenter implements ILoinpersenter{
    private ILoginActivity iLoginActivity;
    private ILoginModel iLoginModel;
    public Loginpresenter(ILoginActivity iLoginActivity){
        this.iLoginActivity = iLoginActivity;//这边是接口实例
        iLoginModel = new LoginModel();//这边是new的一个Model实例赋值给接口……暂时不是很懂
    }
    public int loginUser(){
        if(iLoginActivity.getUserBean().getUsername()==null||iLoginActivity.getUserBean().getUsername().equals("")
                ||iLoginActivity.getUserBean().getPassword()==null||iLoginActivity.getUserBean().getPassword()==("")){
            return -2;//用户名或者密码为空
        }else{
            iLoginModel.SendByHttpClient(iLoginActivity.getUserBean());
            return -3;//-3表示已经传入下层
        }
    }
}
