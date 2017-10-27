package com.example.wxy.watertest10.View;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wxy.watertest10.R;

public class UploadPhotosActivity extends AppCompatActivity implements View.OnClickListener{

    EditText upload_content;
    TextView uploadAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);
        TextView back = (TextView)findViewById(R.id.uploadphoto_back);
        uploadAll = (TextView)findViewById(R.id.uploadphoto_uploadAll);
        Button upload_photo = (Button)findViewById(R.id.upload_photo);
        back.setOnClickListener(this);
        upload_photo.setOnClickListener(this);
        upload_content = (EditText)findViewById(R.id.uploadphoto_content);
        upload_content.addTextChangedListener(textWatcher);
        //uploadAll.setOnClickListener(this);
    }
    private TextWatcher textWatcher= new TextWatcher(){//监听内容输入变化
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(upload_content.getText().toString().trim().length()>0){
                uploadAll.setTextColor(Color.parseColor("#0a89ff"));
                uploadAll.setOnClickListener(UploadPhotosActivity.this);
            }
            else{
                uploadAll.setTextColor(Color.parseColor("#a9a9a9"));
                uploadAll.setOnClickListener(null);//取消监听
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadphoto_back:
                finish();
                break;
            case R.id.uploadphoto_uploadAll:
                //发布
                break;
            case R.id.upload_photo:
                //上传图片
                break;
            default:
        }
    }
}
