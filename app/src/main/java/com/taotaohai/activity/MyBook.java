package com.taotaohai.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.Book;
import com.taotaohai.fragment.ItemBookFragment;
import com.taotaohai.util.ViewFindUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MyBook extends BaseActivity implements OnTabSelectListener, View.OnClickListener, ItemBookFragment.OnListFragmentInteractionListener {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private final String[] mTitles = {
            "全部", "待付款", "代发货"
            , "待收货", "待评价"
    };

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);
        initview();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMsvLayout.loading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMsvLayout.content();
            }
        }, 2000);
    }

    private void initview() {
        for (String title : mTitles) {
            mFragments.add(ItemBookFragment.newInstance(0));
        }
        findViewById(R.id.back).setOnClickListener(this);
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tab = ViewFindUtils.find(decorView, R.id.tab);
        tab.setViewPager(vp);
        tab.setOnTabSelectListener(this);
        vp.setCurrentItem(getIntent().getIntExtra("stata",0));
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onListFragmentInteraction(Book.Data item) {
        startActivity(new Intent(MyBook.this, Bookdetial.class)
                .putExtra("stata", item.getCount())
        );
    }
    @Override
    public void onListFragmentButton3(Book.Data mItem) {//第3个按钮
        startActivity(new Intent(this, LogisActivity.class));
    }
    @Override
    public void onListFragmentButton2(Book.Data item) {//第2个按钮
        switch (item.getCount()) {
            case 1:
                showpay("800");
                break;
            case 2:
                showToast("提醒成功");
                break;
            case 3:
                showDialog2("您确定已收到货？", "确定收货");
                break;
            case 4://再次购买
                startActivity(new Intent(MyBook.this, Evaluation.class));

                break;

        }
    }

    @Override
    public void onListFragmentButton1(Book.Data item) {//第一个按钮
        switch (item.getCount()) {
            case 1:
                showchoose();
                break;
            case 2:
                startActivity(new Intent(MyBook.this, Refund.class));
                break;
            case 3:
                startActivity(new Intent(MyBook.this, Refund.class));
                break;
            case 4://再次购买
//                startActivity(new Intent(MyBook.this, Refund.class));
                break;

        }
    }

    private static final List<String> options1Items = new ArrayList<>();

    private void showchoose() {
        options1Items.clear();
        options1Items.add("托儿索");
        options1Items.add("儿童劫");
        options1Items.add("小学生之手");
        options1Items.add("德玛西亚大保健");
        options1Items.add("面对疾风吧");
        options1Items.add("天王盖地虎");
        options1Items.add("我发一米五");
        options1Items.add("爆刘继芬");

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String s = options1Items.get(options1);
//                button4.setText(s);
            }
        })
                .setSubCalSize(15)//确定和取消文字大小
                .setSubmitColor(R.color.glay_text)//确定按钮文字颜色
                .setCancelColor(R.color.them)//取消按钮文字颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleText("选择退款原因")
                .setTitleSize(15)
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }



    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    /**
     * 一般dialog
     */
    boolean iszfb = true;

    protected void showpay(String st) {
        backgroundAlpha(0.5f);
        final Dialog dialog = new Dialog(this, R.style.MyDialog_holo);
        dialog.setContentView(R.layout.dialog_pay);
        TextView textView = (TextView) dialog.findViewById(R.id.information);
        final View rela_1 = dialog.findViewById(R.id.rela_1);
        final View rela_2 = dialog.findViewById(R.id.rela_2);
        rela_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iszfb = true;
                rela_1.setBackgroundResource(R.drawable.button_r32);
                rela_2.setBackground(null);
            }
        });
        rela_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iszfb = false;
                rela_2.setBackgroundResource(R.drawable.button_r32);
                rela_1.setBackground(null);
            }
        });

        textView.setText(st);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backgroundAlpha(1f);
            }
        });
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.show();
    }
}
