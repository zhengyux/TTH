package com.taotaohai.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.photoselector.model.PhotoModel;
import com.photoselector.util.CommonUtils;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Mine;
import com.taotaohai.myview.MyBitmapImageViewTarget;
import com.taotaohai.util.GlideCircleTransform;
import com.taotaohai.util.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MyDataActivity extends BaseActivity {
    String check = "";
    private View choose, sex;
    private ImageView imag_photo;
    private TextView tv_name;
    private TextView tv_sex;
    private Image image;
    private Mine myData;
    private Dialog dialog;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dat);
        setTitle("基础信息");
//        setTitleTwo("保存");
        initview();
    }

    private void initview() {
        sex = findViewById(R.id.sex);
        choose = findViewById(R.id.choose);
        imag_photo = (ImageView) findViewById(R.id.imag_photo);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_name.setText(getintent("name"));
        tv_sex.setText(getsex(getIntent().getIntExtra("sex", 2)));
        Glide.with(getApplicationContext())
                .load(getintent("photo"))
                .asBitmap()
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .error(getResources().getDrawable(R.mipmap.home4))
                .into(new MyBitmapImageViewTarget(imag_photo));//图片不变形
//        inithttp();
    }

    private String getsex(int sex) {
        switch (sex) {
            case 0:
                return "男";
            case 1:
                return "女";
            case 2:
                return "保密";
        }
        return "保密";


    }

//    private void inithttp() {
//        HashMap<String, String> has = new HashMap<>();
//        has.put("account_id", GlobalParams.userid);
//        post("user/index", has, 1);
//    }


    public void onPhoto(View view) {
        choose.setVisibility(View.VISIBLE);
    }

    public void onName(View view) {
        Intent intent = new Intent(this, ReNameActivity.class);
        intent.putExtra("name", tv_name.getText().toString());
        startActivityForResult(intent, 10);
    }

    public void onSex(View view) {
        sex.setVisibility(View.VISIBLE);
    }

    public void onChoose(View view) {
        choose.setVisibility(View.GONE);
    }

    public void onMen(View v) {
        choose.setVisibility(View.GONE);
        tv_sex.setText("男");
        sex.setVisibility(View.GONE);
        put("api/user/gender/0", null, 100);
    }

    public void onWomen(View v) {
        choose.setVisibility(View.GONE);
        tv_sex.setText("女");
        sex.setVisibility(View.GONE);
        put("api/user/gender/1", null, 100);
    }

    public void onScrite(View v) {
        choose.setVisibility(View.GONE);
        tv_sex.setText("保密");
        sex.setVisibility(View.GONE);
        put("api/user/gender/2", null, 100);
    }


    Uri mPhotoUri;

    public void onpaizhao(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
        ContentValues values = new ContentValues();
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        values.put(MediaStore.Video.Media.TITLE, filename);
        mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        CommonUtils.launchActivityForResult(this, intent, 1);
        choose.setVisibility(View.GONE);
    }

    public void onCancle(View view) {

        choose.setVisibility(View.GONE);
        sex.setVisibility(View.GONE);
    }

    public void onZhao(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 2);
        choose.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                tv_name.setText(data.getStringExtra("name"));
                has.clear();
                has.put("nickname", tv_name.getText().toString().trim());
                put("api/user/nickname", has, 11);
            }
            return;
        }
        if (requestCode == 2) {
            if (data != null)
                mPhotoUri = data.getData();
        }
        if (mPhotoUri == null) {
            Toast.makeText(getApplicationContext(), "选择图片文件出错", Toast.LENGTH_SHORT).show();
            return;
        }
        Glide.with(getApplicationContext())
                .load(mPhotoUri)
                .asBitmap()
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .error(getResources().getDrawable(R.mipmap.home4))
                .into(new MyBitmapImageViewTarget(imag_photo));//图片不变形
        HashMap<String, String> has = new HashMap<>();
        has.put("name", "file");

        String imageurl = new PhotoModel(CommonUtils.query(this, mPhotoUri)).getOriginalPath();
        sendImage("api/user/avatar", has, 0, imageurl, getApplicationContext(), 200, 200, "file");
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        BaseBean bean = util.getgson(result, BaseBean.class);
        if (util.isSuccess(bean, getApplication())) {
            switch (postcode) {
                case 0://上传图片
                    showToast("头像上传成功");
                    break;
//                case 11:
//                    showToast("昵称设置成功");
//                    break;

            }
        }
    }

    boolean isfinish = false;
    String password = "";

    @Override
    public void onError(Throwable ex, int code) {
        super.onError(ex, code);
        setObject(myData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
