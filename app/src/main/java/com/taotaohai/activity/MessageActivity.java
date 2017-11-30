package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;

public class MessageActivity extends BaseActivity implements EaseConversationListFragment.EaseConversationListItemClickListener {

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        EaseConversationListFragment easeConversationListFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, easeConversationListFragment).commit();
        easeConversationListFragment.setConversationListItemClickListener(this);

        findViewById(R.id.rela_shopcar).setOnClickListener((l)-> startActivity(new Intent(this,ShopCarActivity.class)));

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
