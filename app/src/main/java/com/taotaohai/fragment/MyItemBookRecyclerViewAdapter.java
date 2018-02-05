package com.taotaohai.fragment;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taotaohai.R;
import com.taotaohai.bean.Book;


import java.util.List;


public class MyItemBookRecyclerViewAdapter extends RecyclerView.Adapter<MyItemBookRecyclerViewAdapter.ViewHolder> {

    private List<Book.Data> mValues;
    private final ItemBookFragment.OnListFragmentInteractionListener mListener;

    public MyItemBookRecyclerViewAdapter(List<Book.Data> items, ItemBookFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.text_title.setText(mValues.get(position).getExt().getShopName());
        holder.text_content.setText(mValues.get(position).getExt().getGoodsName());
        holder.text_stata.setText(getstata(mValues.get(position).getOrderStatus(), holder.btn_1, holder.btn_2, holder.btn_3));
        holder.tv_sigalmoney.setText("¥ " + mValues.get(position).getExt().getPrice());
        holder.tv_all.setText("¥ " + mValues.get(position).getTotalPrice());
        holder.tv_guige.setText(mValues.get(position).getExt().getRemark());
        holder.tv_count.setText("x" + mValues.get(position).getExt().getAcount());
        holder.mItem.setCount(mValues.get(position).getOrderStatus());
        Glide.with(mcontent).load(mValues.get(position).getExt().getImgId()).error(R.mipmap.ic_bac).into(holder.image_photo);
        holder.btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentButton1(holder.mItem);
            }
        });
        holder.btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentButton2(holder.mItem);
            }
        });
        holder.btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentButton3(holder.mItem);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    Context mcontent;

    public MyItemBookRecyclerViewAdapter setcontent(FragmentActivity content) {
        this.mcontent = content;
        return this;
    }

    public void setdata(List<Book.Data> data) {
        this.mValues = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView text_title;
        public final TextView text_content;
        public final TextView text_stata;
        public final TextView btn_1;
        public final TextView btn_2;
        public final TextView btn_3;
        public final TextView tv_all;
        public final TextView tv_sigalmoney;
        public final TextView tv_guige;
        public final TextView tv_count;
        public final ImageView image_photo;

        public Book.Data mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            text_title = (TextView) view.findViewById(R.id.text_title);
            text_content = (TextView) view.findViewById(R.id.text_content);
            text_stata = (TextView) view.findViewById(R.id.text_stata);
            btn_1 = (TextView) view.findViewById(R.id.btn_1);
            btn_2 = (TextView) view.findViewById(R.id.btn_2);
            btn_3 = (TextView) view.findViewById(R.id.btn_3);
            tv_all = (TextView) view.findViewById(R.id.tv_all);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
            tv_sigalmoney = (TextView) view.findViewById(R.id.tv_sigalmoney);
            tv_guige = (TextView) view.findViewById(R.id.tv_guige);
            image_photo = (ImageView) view.findViewById(R.id.image_photo);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + text_content.getText() + "'";
        }
    }

    String getstata(int i, TextView btn_1, TextView btn_2, TextView btn_3) {
        /*1 未支付
2 待发货
3 待收货
4.	待评价
5.	退款
6.	99 关闭
*/
        btn_1.setVisibility(View.VISIBLE);
        btn_2.setVisibility(View.VISIBLE);
        switch (i) {
            case 1:
                btn_1.setText("取消订单");
                btn_2.setText("立即支付");
                btn_2.setTextColor(mcontent.getResources().getColor(R.color.them));
                btn_2.setBackgroundResource(R.drawable.bac_class_them);
                return "待付款";
            case 2:
                btn_1.setText("售后/退款");
                btn_2.setText("提醒发货");
                return "待发货";
            case 3:
                btn_3.setVisibility(View.VISIBLE);
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
            case 6:

                btn_1.setVisibility(View.GONE);
                btn_2.setText("删除");
                return "交易完成";

            case 7:

                btn_1.setVisibility(View.GONE);
                btn_2.setText("删除");

                return "退款完成";
            case 99:
                btn_1.setVisibility(View.GONE);
                btn_2.setText("删除");
                return "交易关闭";
        }
        return "";
    }
}
