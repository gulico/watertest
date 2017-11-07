package com.example.wxy.watertest10.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Bean.UserBean;
import com.example.wxy.watertest10.R;
import com.example.wxy.watertest10.presenter.ILoinpersenter;
import com.example.wxy.watertest10.presenter.Loginpresenter;

public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginActivity{

    UserBean userBean = new UserBean(null,null);
    EditText UsernameView;
    EditText UserpasswordView;
    ILoinpersenter iLoinpersenter;
    String TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        setContentView(R.layout.activity_login);
        ImageView back = (ImageView)findViewById(R.id.Login_back);
        back.setOnClickListener(this);
        Button login_btn = (Button)findViewById(R.id.login_button);
        login_btn.setOnClickListener(this);
        UsernameView = (EditText)findViewById(R.id.login_name);
        UserpasswordView = (EditText)findViewById(R.id.login_password);
        iLoinpersenter = new Loginpresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Login_back:
                finish();
                break;
            case R.id.login_button:
                userBean.setPassword(UserpasswordView.getText().toString().trim());
                userBean.setUsername(UsernameView.getText().toString().trim());
                setUser();
                break;
            default:
        }
    }
    private void setUser(){//保存登录信息
        int ans;//-2用户名或密码为空，-1用户名不存在，0密码错误，1登陆成功
        ans = iLoinpersenter.loginUser();
        if(ans==-2) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.finishActivity(this);
    }

    public UserBean getUserBean() {
        return userBean;
    }
}
