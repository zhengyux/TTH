package com.taotaohai.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.myview.WordWrapView;

public class SearchGoods extends BaseActivity {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);

        initview();
    }

    private void initview() {
        WordWrapView wordwarp = (WordWrapView) findViewById(R.id.wordwarp);
        for (int i = 0; i < 10; i++) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            text.setText("干活干活");
            wordwarp.addView(text);
        }
        EditText editText = (EditText) findViewById(R.id.edit_search);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    showToast("search");
                    return true;
                }
                return false;
            }
        });

    }

    public void onCancle(View view) {
        finish();
    }
}
