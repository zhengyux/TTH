package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Check;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import static com.taotaohai.util.util.isMobileNO;

public class ForgetPassword extends BaseActivity implements View.OnClickListener{

    private TextView button;
    String scheck = "";

    @Override
    protected void inithttp() {

    }

    EditText ed_1, ed_2, ed_3, ed_4;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        button = (TextView) findViewById(R.id.button);
        ed_1 = (EditText) findViewById(R.id.ed_1);
        ed_2 = (EditText) findViewById(R.id.ed_2);
        ed_3 = (EditText) findViewById(R.id.ed_3);
        ed_4 = (EditText) findViewById(R.id.ed_4);
        findViewById(R.id.btn_login).setOnClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ed_1.getText().toString().trim();
                if (!isMobileNO(username)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                onyanzheng();
                button.setClickable(false);
                button.post(new Runnable() {
                    int time = 0;

                    @Override
                    public void run() {
                        time++;
                        button.setText(60 - time + "s后可重发");
                        button.setTextColor(getResources().getColor(R.color.text_gray));
                        button.setBackgroundResource(R.drawable.button_r2_glay);
                        if (time == 60) {
                            button.setText("重新发送");
                            button.setTextColor(getResources().getColor(R.color.white));
                            button.setBackgroundResource(R.drawable.button_r2);
                            button.setClickable(true);
                            return;
                        }
                        button.postDelayed(this, 1000);
                    }
                });
            }
        });
    }

    public void onyanzheng() {

        get("api/auth/sms/" + ed_1.getText().toString().trim()+"/1", 100);
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if (postcode == 1) {
            BaseBean baseBean = util.getgson(result, BaseBean.class);
            if (util.isSuccess(baseBean, this)) {
                showToast("修改成功");
                startActivity(new Intent(this, Login.class));
                finish();

            }

            return;
        }
        Check check = util.getgson(result, Check.class);
        if (check.getSuccess()) {
            scheck = check.getData().getVerifyCode();
        }
    }


    public void onFinish(View v) {
        finish();
    }

    @Override
    public void onClick(View view) {

        username = ed_1.getText().toString().trim();
        if (!isMobileNO(username)) {
            showToast("请输入正确的手机号");
            return;
        }
        if (!scheck.equals(ed_2.getText().toString().trim())) {
            showToast("输入验证码错误");
            return;
        }
        if(null==ed_3.getText()||"".equals(ed_3.getText().toString().trim())){
            showToast("密码不能为空");
            return;
        }
        if (!ed_3.getText().toString().trim().equals(ed_4.getText().toString().trim())) {
            showToast("两次密码不一样");
            return;
        }
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("username", ed_1.getText().toString().trim());
        jsonObject.addProperty("password", ed_3.getText().toString().trim());
        jsonObject.addProperty("code", scheck);

        Http(HttpMethod.POST,"api/user/forgePassword",jsonObject.toString(),1);

    }

    @Override
    public void onError(Throwable ex, int postcode) {
        super.onError(ex, postcode);

    }
}
