package com.example.wxy.watertest10.View;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.example.wxy.watertest10.Bean.AppManager;
import com.example.wxy.watertest10.Bean.BaseActivity;
import com.example.wxy.watertest10.R;

public class UploadPhotosActivity extends BaseActivity implements View.OnClickListener{

    EditText upload_content;
    TextView uploadAll;
    ImageView upload_photo;
    public static final int CHOOSE_PHOTO = 2;//访问相册请求码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);
        TextView back = (TextView)findViewById(R.id.uploadphoto_back);
        uploadAll = (TextView)findViewById(R.id.uploadphoto_uploadAll);
        upload_photo = (ImageView)findViewById(R.id.upload_photo);
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
                //判断是否有访问相册的权限
                //无则申请，有则访问
                if(ContextCompat.checkSelfPermission(UploadPhotosActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UploadPhotosActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();//打开相册
                }
                //上传图片
                break;
            default:
        }
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"没有权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(requestCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT >=19){
                        //4.4以上系统使用该方法处理图片
                        handleImageOnKitKat(data);
                    }else{
                        //4.4以下系统使用该方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){//版本4.4以上
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri则通过 document_id处理
            String doID = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = doID.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri  = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(doID));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri则通过 普通方式处理
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){//版本4.4以下
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过uri和selection获取真实图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            upload_photo.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
        }
    }
}
