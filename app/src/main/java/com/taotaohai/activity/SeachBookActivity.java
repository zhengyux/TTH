package com.taotaohai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.util.util;

import java.net.URLEncoder;

public class SeachBookActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private RelativeLayout back;//返回键
    private EditText edit_search_book;//搜索框
    private TextView search_book_tv;//搜索按钮
    private ListView listview_seach_book; //订单列表
    private Book book;//订单实体类
    private Adapter adapter;//订单适配器

    @Override
    protected void inithttp() {

    }

    @Override
    public void onSuccess(String result, int postcode) {
        Log.e("tag", "onSuccess: "+result );
                book= util.getgson(result,Book.class);

                adapter.notifyDataSetChanged();
                listview_seach_book.setAdapter(adapter);

    }

    @Override
    public void onError(Throwable ex, int postcode) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_book);
        initview();
    }

    private void initview() {
        adapter = new Adapter();
        back= (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        edit_search_book = (EditText) findViewById(R.id.edit_search_book);
        search_book_tv = (TextView) findViewById(R.id.search_book_tv);
        search_book_tv.setOnClickListener(this);
        listview_seach_book = (ListView) findViewById(R.id.listview_seach_book);
        listview_seach_book.setOnItemClickListener(this);
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.back:

                finish();

                break;
            case R.id.search_book_tv:

                if(null==edit_search_book.getText()||"".equals(edit_search_book.getText().toString().trim())){
                   showToast("输入搜索关键字");
                   return;
                }
                hintKbTwo();

                String key = edit_search_book.getText().toString().trim();
                try {

                    get("api/goodsorder/like_list/"+URLEncoder.encode(key,"utf-8"),0);

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        startActivity(new Intent(SeachBookActivity.this, Bookdetial.class)
                .putExtra("data", book.getData2().getData().get(i)));

    }

    public class Adapter extends BaseAdapter{


        @Override
        public int getCount() {
            return book.getData2().getData().size();
        }

        @Override
        public Object getItem(int i) {
            return book.getData2().getData().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_book, null);
                viewHolder.text_title = (TextView) view.findViewById(R.id.text_title);
                viewHolder.text_content = (TextView) view.findViewById(R.id.text_content);
                viewHolder.text_stata = (TextView) view.findViewById(R.id.text_stata);
                viewHolder.btn_1 = (TextView) view.findViewById(R.id.btn_1);
                viewHolder.btn_2 = (TextView) view.findViewById(R.id.btn_2);
                viewHolder.btn_3 = (TextView) view.findViewById(R.id.btn_3);
                viewHolder.tv_all = (TextView) view.findViewById(R.id.tv_all);
                viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_count);
                viewHolder.tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
                viewHolder.tv_guige = (TextView) view.findViewById(R.id.tv_guige);
                viewHolder.image_photo = (ImageView) view.findViewById(R.id.image_photo);



                view.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.text_title.setText(book.getData2().getData().get(i).getExt().getShopName());
            viewHolder.text_content.setText(book.getData2().getData().get(i).getExt().getGoodsName());
            viewHolder.text_stata.setText(getstata(book.getData2().getData().get(i).getOrderStatus(), viewHolder.btn_1, viewHolder.btn_2, viewHolder.btn_3));
            viewHolder.tv_sigalmoney.setText("¥ " + book.getData2().getData().get(i).getExt().getPrice());
            viewHolder.tv_all.setText("¥ " + book.getData2().getData().get(i).getTotalPrice());
            viewHolder.tv_guige.setText(book.getData2().getData().get(i).getRemarks());
            viewHolder.tv_count.setText("x" + book.getData2().getData().get(i).getExt().getAcount());
            book.getData2().getData().get(i).setCount(book.getData2().getData().get(i).getOrderStatus());
            Glide.with(getApplicationContext()).load(book.getData2().getData().get(i).getExt().getImgId()).error(R.mipmap.ic_bac).into(viewHolder.image_photo);




            return view;
        }

        class ViewHolder{
            public  View mView;
            public  TextView text_title;
            public  TextView text_content;
            public  TextView text_stata;
            public  TextView btn_1;
            public  TextView btn_2;
            public  TextView btn_3;
            public  TextView tv_all;
            public  TextView tv_sigalmoney;
            public  TextView tv_guige;
            public  TextView tv_count;
            public  ImageView image_photo;

        }
        String getstata(int i, TextView btn_1, TextView btn_2, TextView btn_3) {
        /*1 未支付
2 待发货
3 待收货
4.	待评价
5.	退款
6.	99 关闭
*/
            btn_1.setVisibility(View.GONE);
            btn_2.setVisibility(View.GONE);
            btn_3.setVisibility(View.GONE);
            switch (i) {
                case 1:
                    btn_1.setText("取消订单");
                    btn_2.setText("立即支付");
                    btn_2.setTextColor(getApplicationContext().getResources().getColor(R.color.them));
                    btn_2.setBackgroundResource(R.drawable.bac_class_them);
                    return "待付款";
                case 2:
                    btn_1.setText("售后/退款");
                    btn_2.setText("提醒发货");
                    return "待发货";
                case 3:
//                    btn_3.setVisibility(View.VISIBLE);
                    btn_3.setText("查看物流");
                    btn_1.setText("售后/退款");
                    btn_2.setText("确认收货");
                    return "待收货";
                case 4:
                    btn_1.setText("再次购买");
                    btn_2.setText("评价");
                    return "待评价";
                case 5:
//                btn_2.setText("退款xiangqi");
                    btn_1.setVisibility(View.INVISIBLE);
                    btn_2.setVisibility(View.INVISIBLE);
//                btn_2.setTextColor(mcontent.getResources().getColor(R.color.cross_them));
//                btn_2.setBackgroundResource(R.drawable.button_r22);
                    return "退款";
//            case 6:
//                btn_1.setVisibility(View.GONE);
//                btn_2.setText("删除");
////                btn_2.setTextColor(mcontent.getResources().getColor(R.color.cross_them));
////                btn_2.setBackgroundResource(R.drawable.button_r22);
//                return "关闭";
                case 99:
                    btn_1.setVisibility(View.GONE);
                    btn_2.setText("删除");
//                btn_2.setTextColor(mcontent.getResources().getColor(R.color.cross_them));
//                btn_2.setBackgroundResource(R.drawable.button_r22);
                    return "交易关闭";
            }
            return "";
        }
    }

}
