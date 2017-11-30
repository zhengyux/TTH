package com.taotaohai.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.util.GlideUtil;

import static com.taotaohai.activity.ReFundListActivity.getRefundState;

public class ReFundDetialActivity extends BaseActivity {
    TextView tv_14;
    private Book.Data data;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_fund_detial);
        setTitle("退款详情");
        data = (Book.Data) getIntent().getSerializableExtra("data");
        initview();

    }

    int remainTime = 0;

    private void initview() {
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        TextView tv_stata = (TextView) findViewById(R.id.tv_stata);
        TextView tv_6 = (TextView) findViewById(R.id.tv_6);
        TextView tv_7 = (TextView) findViewById(R.id.tv_7);
        ImageView image_1 = (ImageView) findViewById(R.id.image_1);
        GlideUtil.loadImg(data.getExt().getImgId(), image_1);
        tv_6.setText(data.getExt().getGoodsName());
        tv_7.setText(data.getExt().getRemark());
        tv_time.setText(data.getExt().getGmtCreate());


        tv_stata.setText(getRefundState(data.getRefundStatus()));
        tv_14 = (TextView) findViewById(R.id.tv_14);
        String st = "退款原因：" + data.getExt().getRefundReason() + "<br />退款金额：<font color=#fa8d00>" + data.getTotalPrice() + "</font><br />申请时间：" + data.getGmtRefund() + "<br />退款编号：" + data.getId();
        tv_14.setText(Html.fromHtml(st));

        if (data.getRefundStatus() ==1) {
            remainTime = data.getGmtRemaining();
            tv_time.post(new Runnable() {
                @Override
                public void run() {
                    if (remainTime < 0) {
                        return;
                    }
                    tv_time.setText(getStringtime(remainTime));
                    remainTime--;
                    tv_time.postDelayed(this, 1000);
                }
            });
        } else {
            tv_time.setText("");
        }
    }

    String getStringtime(int remainTime) {
        int day = remainTime / 86400;
        int hour = remainTime % 86400 / 3600;
        int minutes = remainTime % 3600 / 60;
        int seconds = remainTime % 60;
        String time = "还有";
        if (day != 0) {
            time = time + day + "天";
        }
        if (day != 0 || hour != 0) {
            time = time + hour + ":";
        }
        if (day != 0 || hour != 0 || minutes != 0) {
            time = time + minutes + ":";
        }
        time = time + seconds + "交易关闭";
        return time;
    }
}
