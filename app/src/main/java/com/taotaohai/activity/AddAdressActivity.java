package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.HashMap;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class AddAdressActivity extends BaseActivity implements CityPickerListener {
    TextView tv_area, et_name, et_phone, et_address;
    ImageView img_choose;
    boolean checked = true;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);

        initview();
    }

    private void initview() {
        setTitle("新增地址");
        setTitleTwo("保存");
        tv_area = (TextView) findViewById(R.id.tv_area);
        img_choose = (ImageView) findViewById(R.id.img_choose);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (EditText) findViewById(R.id.et_address);
        if (getintent("name") != null && getintent("name").length() > 0) {
            tv_area.setText(getintent("city"));
            et_name.setText(getintent("name"));
            et_phone.setText(getintent("tel"));
            et_address.setText(getintent("area"));
        }

    }

    public void onChooseCity(View v) {
        View view = getWindow().peekDecorView();
        if (view != null) {
//隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager)
                    getSystemService(AddAdressActivity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
                    0);

            CityPicker cityPicker = new CityPicker(this, this);
            cityPicker.show();
        }
    }

    public void onChooseImg(View v) {
        if (checked) {
            img_choose.setImageResource(R.mipmap.aixinwu);
        } else {
            img_choose.setImageResource(R.mipmap.aixin);
        }
        checked = !checked;
    }


    @Override
    protected void OnTow() {
        super.OnTow();
        if (et_name.length() == 0) {
            showToast("收货人姓名不能为空");
            return;
        }
        if (et_phone.length() == 0) {
            showToast("收货人电话不能为空");
            return;
        }
        if (tv_area.length() == 0) {
            showToast("请选择区域");
            return;
        }
        if (et_address.length() == 0) {
            showToast("收货人地址不能为空");
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("isdefault", checked + "");
        jsonObject.addProperty("linkName", et_name.getText().toString().trim());
        jsonObject.addProperty("linkTel", et_phone.getText().toString().trim());
        jsonObject.addProperty("linkAddress", et_address.getText().toString().toString());
        String[] addresss = tv_area.getText().toString().trim().split(" ");
        if (addresss.length == 0) {
            addresss = tv_area.getText().toString().split(" ");
        }
        jsonObject.addProperty("linkProvince", addresss[0]);
        jsonObject.addProperty("linkCity", addresss[1]);
        if (addresss.length > 2) {
            jsonObject.addProperty("linkArea", addresss[2]);
        } else {
            jsonObject.addProperty("linkArea", "");
        }
        if (getintent("id").length() > 0) {
            Http(HttpMethod.POST, "api/user/userAddress/" + getintent("id"), jsonObject.toString(), 1);
        } else {
            Http(HttpMethod.POST, "api/user/userAddress", jsonObject.toString(), 0);
        }
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
//        if (postcode == 15) {
//            UseAdress useadress = util.getgson(result, UseAdress.class);
//            if (util.isSuccess(useadress, getApplicationContext())) {
//                Intent intent = new Intent(this, AdressActivity.class);
//                intent.putExtra("id", useadress.getData().getId());
//                setResult(0, intent);
//                finish();
//            }
//            return;
//        }
//
        if (util.isSuccess(result)) {
            if (postcode == 0) {
                showToast("添加成功");
            } else {
                showToast("修改成功");
            }
            finish();
        }
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
    }

    @Override
    public void getCity(String s) {
        tv_area.setText(s);
    }
}
