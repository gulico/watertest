package com.example.wxy.watertest10.View;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.Bean.CommomDialog;
import com.example.wxy.watertest10.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import android.os.Handler;


import cn.forward.androids.views.ScrollPickerView;
import cn.forward.androids.views.StringScrollPicker;

import static com.example.wxy.watertest10.Bean.AppManager.currentActivity;

public class UploadPhotosActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "UploadPhotosActivity";
    EditText upload_content;
    TextView uploadAll;
    ImageView upload_photo;
    public static final int CHOOSE_PHOTO = 2;//访问相册请求码
    LinearLayout choose_place;
    TextView place_name;
    Bitmap bitmap;
    String getplace_name = null;//存监测点的名字



    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    Toast.makeText(UploadPhotosActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);
        TextView back = (TextView) findViewById(R.id.uploadphoto_back);
        uploadAll = (TextView) findViewById(R.id.uploadphoto_uploadAll);
        upload_photo = (ImageView) findViewById(R.id.upload_photo);
        back.setOnClickListener(this);
        upload_photo.setOnClickListener(this);
        upload_content = (EditText) findViewById(R.id.uploadphoto_content);
        upload_content.addTextChangedListener(textWatcher);
        choose_place = (LinearLayout) this.findViewById(R.id.choose_place);
        choose_place.setOnClickListener(this);
        place_name = (TextView) this.findViewById(R.id.place_name);
        //uploadAll.setOnClickListener(this);
    }

    private TextWatcher textWatcher = new TextWatcher() {//监听内容输入变化
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (upload_content.getText().toString().trim().length() > 0) {
                uploadAll.setTextColor(Color.parseColor("#0a89ff"));
                uploadAll.setOnClickListener(UploadPhotosActivity.this);
            } else {
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
        switch (v.getId()) {
            case R.id.uploadphoto_back:
                finish();
                break;
            case R.id.uploadphoto_uploadAll:
                //发布
                SendByHttpClient(bitmap);
                Toast.makeText(UploadPhotosActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.upload_photo:
                //判断是否有访问相册的权限
                //无则申请，有则访问
                if (ContextCompat.checkSelfPermission(UploadPhotosActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UploadPhotosActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();//打开相册
                }
                //上传图片
                break;
            case R.id.choose_place://选择监测点，弹出对话框
                String[] DATA = {"东湖"};
                View view = View.inflate(this, R.layout.dialog_scoll_content, null);
                StringScrollPicker datapicker = view.findViewById(R.id.Stringcontent);
                datapicker.setData(new ArrayList<CharSequence>(Arrays.asList(DATA)));//设置选项
                datapicker.setSelectedPosition(0);//设置初始位置

                final CommomDialog commomDialog = new CommomDialog(this, view, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(CommomDialog dialog, boolean confirm) {
                        final View contentView;
                        contentView = dialog.getContentView();
                        StringScrollPicker selected = contentView.findViewById(R.id.Stringcontent);
                        getplace_name = selected.getSelectedItem().toString();
                        Toast.makeText(UploadPhotosActivity.this, getplace_name, Toast.LENGTH_SHORT).show();
                        place_name.setText(getplace_name);
                        dialog.dismiss();
                    }
                }).setTitle("请选择监测点");
                commomDialog.show();
                break;
            default:
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {//判断手机版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上系统使用该方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下系统使用该方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {//版本4.4以上
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri则通过 document_id处理
            String doID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = doID.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(doID));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri则通过 普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {//版本4.4以下
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection获取真实图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            bitmap = BitmapFactory.decodeFile(imagePath);
            upload_photo.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void SendByHttpClient(final Bitmap photodata){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpclient=new DefaultHttpClient();
                    HttpPost httpPost=new HttpPost("http://120.55.47.216:8060/ideaWater02/PhotoController/insertPhoto.do");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                    photodata.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    baos.close();
                    byte[] buffer = baos.toByteArray();
                    System.out.println("图片的大小："+buffer.length);

                    //将图片的字节流数据加密成base64字符输出
                    String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);

                    String temp_str="";
                    Date dt = new Date();
                    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    temp_str=sdf.format(dt);



                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    //params.add(new BasicNameValuePair("str",photo));
                    String[] strings = new String[]{"data:image/png;base64,"+photo};
                    String stringJson = JSON.toJSONString(strings);
                    params.add(new BasicNameValuePair("base64Array",stringJson));
                    params.add(new BasicNameValuePair("dateTime",temp_str));
                    params.add(new BasicNameValuePair("position","testPostion"));
                    params.add(new BasicNameValuePair("introduce","testIntroduce"));



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
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
