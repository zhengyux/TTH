package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.util.util;

public class MessageActivity extends BaseActivity implements EaseConversationListFragment.EaseConversationListItemClickListener {

    private RelativeLayout rela_shopcar;

    @Override
    protected void inithttp() {
        get("/api/shopCar/shop_car_num",20);
    }

    @Override
    public void onSuccess(String result, int postcode) {
        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                BadgeView badgeView = new BadgeView(getApplicationContext(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        EaseConversationListFragment easeConversationListFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, easeConversationListFragment).commit();
        easeConversationListFragment.setConversationListItemClickListener(this);

        rela_shopcar= (RelativeLayout) findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener((l)-> startActivity(new Intent(this,ShopCarActivity.class)));
        inithttp();
    }

    @Override
    public void onListItemClicked(EMConversation conversation) {
        startActivity(new Intent(this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
    }

    public void onNotice(View view) {
        startActivity(new Intent(this,message_all.class).putExtra("type","1"));
    }

    public void onSystem(View view) {
        startActivity(new Intent(this,message_all.class).putExtra("type","0"));
    }

    public void onBack(View view) {
        finish();
    }
}
