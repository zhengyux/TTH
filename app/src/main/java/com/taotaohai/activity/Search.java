package com.taotaohai.activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.fragment.VideoFragment_search;

public class Search extends BaseActivity {

    private TextView tv_go;
    private EditText editText;
    private VideoFragment_search f2;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        f2 = new VideoFragment_search();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, f2).commit();
        tv_go = (TextView) findViewById(R.id.textView18);
        editText = (EditText) findViewById(R.id.edit_search);

    }

    public static String key = "";

    public void onCancle(View view) {
        hintKbTwo();
            key = editText.getText().toString();
            f2.gotosearch();

    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    public void onBack(View view) {
        finish();
    }
}
