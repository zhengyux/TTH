package com.taotaohai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.myview.WordWrapView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchGoods extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @ViewInject(R.id.tv_search)
    private TextView tv_search;
    @ViewInject(R.id.tv_cancle)
    private TextView tv_cancle;
    @ViewInject(R.id.chooseClick)
    private View chooseClick;
    @ViewInject(R.id.edit_search)
    private EditText edit_search;

    @Event(value = {R.id.tv_search, R.id.shopClick, R.id.goodsClick, R.id.chooseClick, R.id.tv_cancle})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                chooseClick.setVisibility(View.VISIBLE);
                break;
            case R.id.goodsClick:
                chooseClick.setVisibility(View.GONE);
                tv_search.setText("商品");
                break;
            case R.id.shopClick:
                chooseClick.setVisibility(View.GONE);
                tv_search.setText("店家");
                break;
            case R.id.chooseClick:
                chooseClick.setVisibility(View.GONE);
                break;
            case R.id.tv_cancle:
                if (tv_search.getText().toString().equals("商品")) {
                    startActivity(new Intent(SearchGoods.this, Seachend.class)
                            .putExtra("goodsName", edit_search.getText().toString())
                    );
                } else {

                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        x.view().inject(this);//注解绑定
        initview();
    }


    private void initview() {
        TextView tv_search = (TextView) findViewById(R.id.tv_search);

        WordWrapView wordwarp = (WordWrapView) findViewById(R.id.wordwarp);
        for (int i = 0; i < 10; i++) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            text.setText("干活干活" + i);
            wordwarp.addView(text);
        }
        WordWrapView wordwarp2 = (WordWrapView) findViewById(R.id.wordwarp2);
        for (int i = 0; i < 10; i++) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            text.setText("干活" + i);
            wordwarp2.addView(text);
        }
        WordWrapView wordwarp3 = (WordWrapView) findViewById(R.id.wordwarp3);
        for (int i = 0; i < 5; i++) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            text.setText(i * 100 + "~" + i * 100 + 100 + "km");
            wordwarp3.addView(text);
        }

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    tv_cancle.setText("取消");
                } else {
                    tv_cancle.setText("确定");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}
