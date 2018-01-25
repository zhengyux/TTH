package com.taotaohai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Check;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import static com.taotaohai.util.util.isMobileNO;

public class ShopIn extends BaseActivity {

    private EditText edit_old;
    private EditText edit_new;
    private EditText edit_code;
    private EditText edit_phone;
    private View rela_num;
    private TextView tv_num;
    private TextView btn_verify;
    String username;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopin);
        setTitle("申请入驻");
        edit_old = (EditText) findViewById(R.id.edit_old);
        btn_verify = (TextView) findViewById(R.id.btn_verify);
        edit_new = (EditText) findViewById(R.id.edit_new);
        edit_code = (EditText) findViewById(R.id.edit_code);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        tv_num = (TextView) findViewById(R.id.tv_num);
        rela_num = findViewById(R.id.rela_num);
        findViewById(R.id.btn_save).setOnClickListener(v -> {
            if (edit_new.getText().toString().trim().length() < 1) {
                showToast("姓名不能为空");
                return;
            }
            if (!util.isMobileNO(edit_phone.getText().toString().trim())) {
                showToast("请输入正确手机号");
                return;
            }
            if (!scheck.equals(edit_code.getText().toString().trim())) {
                showToast("输入验证码错误");
                return;
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", edit_old.getText().toString());
            jsonObject.addProperty("principal", edit_new.getText().toString());
            jsonObject.addProperty("telephone", edit_phone.getText().toString());
            jsonObject.addProperty("verifyCode", scheck);

            Http(HttpMethod.POST, "api/shopPre", jsonObject.toString(), 0);
        });
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edit_phone.getText().toString().trim();
                if (!isMobileNO(username)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                onyanzheng();
                btn_verify.setClickable(false);
                btn_verify.post(new Runnable() {
                    int time = 0;

                    @Override
                    public void run() {
                        time++;
                        btn_verify.setText(60 - time + "s后可重发");
                        btn_verify.setTextColor(getResources().getColor(R.color.text_gray));
                        btn_verify.setBackgroundResource(R.drawable.button_r2_glay);
                        if (time == 60) {
                            btn_verify.setText("重新发送");
                            btn_verify.setTextColor(getResources().getColor(R.color.white));
                            btn_verify.setBackgroundResource(R.drawable.button_r2);
                            btn_verify.setClickable(true);
                            return;
                        }
                        btn_verify.postDelayed(this, 1000);
                    }
                });
            }
        });
    }

    int count = 3;

    public void onyanzheng() {

        get("api/auth/sms/" + edit_phone.getText().toString().trim()+"/2", 100);
    }

    String scheck = "";

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);

        if (postcode == 100) {
            Check check = util.getgson(result, Check.class);
            if (check.getSuccess()) {
                scheck = check.getData().getVerifyCode();

            }
            return;
        }
        if (util.isSuccess(result)) {
            rela_num.setVisibility(View.VISIBLE);
            rela_num.post(new Runnable() {
                @Override
                public void run() {
                    tv_num.setText(String.valueOf(count));
                    if (count == 0) {
                        finish();
                        return;
                    }
                    count--;
                    rela_num.postDelayed(this, 1000);
                }
            });

        } else {
            showToast("提交失败");
        }
    }
}
