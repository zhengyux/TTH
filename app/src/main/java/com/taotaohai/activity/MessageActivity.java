package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.adapter.ConversationAdapter;
import com.taotaohai.bean.ShopCarNum;
import com.taotaohai.model.Conversation;
import com.taotaohai.model.MessageFactory;
import com.taotaohai.model.NomalConversation;
import com.taotaohai.myview.BadgeView;
import com.taotaohai.ui.ConversationPresenter;
import com.taotaohai.ui.ConversationView;
import com.taotaohai.util.util;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMMessage;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MessageActivity extends BaseActivity implements ConversationView {

    private RelativeLayout rela_shopcar;
    private RelativeLayout imageView25;//系统消息
    private RelativeLayout imageView26;//通知消息
    private ListView listView;
    private ConversationAdapter adapter;
    private ConversationPresenter presenter;
    private List<Conversation> conversationList = new LinkedList<>();
    BadgeView badgeView;
    BadgeView badgeView25;
    BadgeView badgeView26;
    @Override
    protected void inithttp() {
        get("/api/shopCar/shop_car_num",20);
        get("/api/message/notReadList/0",50);
        get("/api/message/notReadList/1",51);

    }

    @Override
    public void onSuccess(String result, int postcode) {
        if(postcode==20){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                badgeView = new BadgeView(getApplicationContext(),rela_shopcar);
                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView.setTextSize(9);// 设置文本大小
                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
                badgeView.show();// 将角标显示出来
            }else {
                badgeView.hide();
            }
        }
        if(postcode==50){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                badgeView25 = new BadgeView(getApplicationContext(),imageView25);
                badgeView25.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView25.setTextSize(6);// 设置文本大小
                badgeView25.setText(""); // 设置要显示的文本
                badgeView25.show();// 将角标显示出来
            }else {
                badgeView25.hide();
            }

        }
        if(postcode==51){
            ShopCarNum shopCarNum = new ShopCarNum();
            shopCarNum = util.getgson(result,ShopCarNum.class);
            if(shopCarNum.getData()!="0"){
                badgeView26 = new BadgeView(getApplicationContext(),imageView26);
                badgeView26.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 设置在右上角
                badgeView26.setTextSize(6);// 设置文本大小
                badgeView26.setText(""); // 设置要显示的文本
                badgeView26.show();// 将角标显示出来
            }else {
                badgeView26.hide();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        rela_shopcar= (RelativeLayout) findViewById(R.id.rela_shopcar);
        rela_shopcar.setOnClickListener((l)-> startActivity(new Intent(this,ShopCarActivity.class)));
        imageView25 = (RelativeLayout) findViewById(R.id.imageView25);
        imageView26 = (RelativeLayout) findViewById(R.id.imageView26);
        listView = (ListView) findViewById(R.id.msg_list);
        adapter = new ConversationAdapter(getApplicationContext(), R.layout.item_conversation, conversationList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conversationList.get(position).navToDetail(MessageActivity.this);


            }
        });



        presenter = new ConversationPresenter(this);
        presenter.getConversation();

        adapter.notifyDataSetChanged();

        inithttp();


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

    @Override
    protected void onResume() {
        inithttp();
        super.onResume();
    }

    @Override
    public void initView(List<TIMConversation> conversationList) {

    }

    @Override
    public void updateMessage(TIMMessage message) {

        if (message == null){
            adapter.notifyDataSetChanged();
            return;

        }

        NomalConversation conversation = new NomalConversation(message.getConversation());
        Iterator<Conversation> iterator =conversationList.iterator();
        while (iterator.hasNext()){
            Conversation c = iterator.next();
            if (conversation.equals(c)){
                conversation = (NomalConversation) c;
                iterator.remove();
                break;
            }
        }
        conversation.setLastMessage(MessageFactory.getMessage(message));
        conversationList.add(conversation);
        Collections.sort(conversationList);
        refresh();



    }

    @Override
    public void updateFriendshipMessage() {


    }

    @Override
    public void removeConversation(String identify) {

        Iterator<Conversation> iterator = conversationList.iterator();
        while(iterator.hasNext()){
            Conversation conversation = iterator.next();
            if (conversation.getIdentify()!=null&&conversation.getIdentify().equals(identify)){
                iterator.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }

    }

    @Override
    public void updateGroupInfo(TIMGroupCacheInfo info) {

    }

    @Override
    public void refresh() {

        Collections.sort(conversationList);
        adapter.notifyDataSetChanged();


    }
}
