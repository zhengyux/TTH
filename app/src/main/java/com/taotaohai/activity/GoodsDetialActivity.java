package com.taotaohai.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.hedgehog.ratingbar.RatingBar;
import com.hyphenate.easeui.EaseConstant;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Comment;
import com.taotaohai.bean.Focus2;
import com.taotaohai.bean.Goods;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;
import com.taotaohai.widgets.MultipleStatusView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.xutils.http.HttpMethod;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.taotaohai.GlobalParams.LOGINPROBLEM;
import static com.taotaohai.GlobalParams.NONOTICELOGIN;

public class GoodsDetialActivity extends BaseActivity implements View.OnClickListener {
    int stata = 1;
    int buynum = 1;
    int stock = 0;
    TextView tv_defult, tv_title, tv_num, tv_num2, tv_count, tv_util, tv_dis, tv_dis2, tv_name, tv_scor, tv_goods, tv_source, tv_people;
    View view_defult;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private View lin_1;
    private View lin_2;
    private View lin_3;
    private MyAdapter adapter;
    private ImageView image_like, image_photo, image_1, image_3;
    private View rela_buy;
    private TextView tv_gotoshop;
    private Banner banner;
    private Goods goods;
    private View rela_click_2;
    private ListView listview;
    private Comment comment;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv_count_all;
    private Focus2 focus2;
    private LinearLayout lin_10;
    private ImageView shopcar;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detial);
        initview();
        mMsvLayout.loading();
        get("api/goods/" + getintent("id"));
        get("api/comment/goods/" + getintent("id"), 1);
        get("api/follow/" + getintent("id") + "/check/goods", NONOTICELOGIN);
 //       get("/api/shopCar/shop_car_num",20);

    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        if (LOGINPROBLEM) {
            LOGINPROBLEM = false;
            return;
        }
        if (code == 0 && goods == null) {
            get("api/goods/" + getintent("id"));
            return;
        }
        if (code == 1 && comment == null) {
            get("api/comment/goods/" + getintent("id"), 1);
            return;
        }
//        if (code == NONOTICELOGIN && focus2 == null) {
//            get("api/follow/" + getintent("id") + "/check/goods", NONOTICELOGIN);
//            return;
//        }
        mMsvLayout.content();
    }

    @Override
    public void onError(Throwable ex, int postcode) {
        if(postcode==998||postcode==995||postcode==997||postcode==996||postcode==994||postcode==993){
            String[] st = ex.toString().split("result:");
            if (st.length > 1) {
                util.isSuccess(util.getgson(st[1], BaseBean.class));
            }
            try {
                if (ex.toString().contains("401") & postcode != NONOTICELOGIN) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("未登录");
                    dialog.setMessage("是否进入登录页登录?");
                    dialog.setNegativeButton("前往", (dialog1, which) -> {
                        startActivity(new Intent(this, Login.class));
                        finish();
                    });
                    dialog.setNeutralButton("取消", (dialog1, which) -> {
                    });
                    dialog.show();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        if(postcode==993){
            rela_buy.setVisibility(View.VISIBLE);
        }
        if(postcode==994){
            paytype = 0;
            if (goods == null) {
                return;
            }
            rela_buy.setVisibility(View.VISIBLE);
        }
        if(postcode==995){
            get("api/follow/" + getintent("id") + "/goods", 999);
        }
        if(postcode==996){
            startActivity(new Intent(this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, goods.getData().getShopId()));
        }
        if(postcode==998){
            startActivity(new Intent(this, ShopCarActivity.class));
        }
        if(postcode==997){
            startActivity(new Intent(this, MessageActivity.class));
        }

//        if(postcode==20){
//            ShopCarNum shopCarNum = new ShopCarNum();
//            shopCarNum = util.getgson(result,ShopCarNum.class);
//            if(shopCarNum.getData()!="0"){
//                BadgeView badgeView = new BadgeView(getApplicationContext(),shopcar);
//                badgeView.setBadgePosition(BadgeView.POSITION_CENTER);// 设置在右上角
//                badgeView.setTextSize(9);// 设置文本大小
//                badgeView.setText(shopCarNum.getData()); // 设置要显示的文本
//                badgeView.show();// 将角标显示出来
//            }
//
//        }

        if (postcode == 999) {
            if (isLike) {
                image_like.setImageResource(R.mipmap.xinno);
            } else {
                image_like.setImageResource(R.mipmap.xinyes);
            }
            isLike = !isLike;
        }
        if (postcode == 99) {
            if (util.isSuccess(result)) {
//                View view = getLayoutInflater().inflate(R.layout.toast, null);
//                CustomToast.showDiverseToast(this, view, Gravity.TOP);
                int[] local = new int[2];
                image_1.getLocationOnScreen(local);
                DisplayMetrics metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                image_3.setVisibility(View.VISIBLE);
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(image_3, "translationX",
                        local[0], metric.widthPixels - 200);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(image_3, "translationY",
                        local[1], -20);
                ObjectAnimator anim3 = ObjectAnimator.ofFloat(image_3, "alpha",
                        1.0f, 0.2f);
                ObjectAnimator anim4 = ObjectAnimator.ofFloat(image_3, "scaleX",
                        1.0f, 0.2f);
                ObjectAnimator anim5 = ObjectAnimator.ofFloat(image_3, "scaleY",
                        1.0f, 0.2f);
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(image_photo,
//                "x", cx, 0f);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(image_photo,
//                "x", cx);

                /**
                 * anim1，anim2,anim3同时执行
                 * anim4接着执行
                 */
                AnimatorSet animSet = new AnimatorSet();
                animSet.play(anim1).with(anim2).with(anim3).with(anim4).with(anim5);
                animSet.setDuration(1000);
                animSet.start();

            } else {
                showToast("加入商品失败，商品数量不足");
            }
            rela_buy.postDelayed(() -> rela_buy.setVisibility(View.GONE), 1000);
        }
        if (postcode == NONOTICELOGIN) {
            focus2 = util.getgson(result, Focus2.class);
            isLike = focus2.getData();
            if (!isLike) {
                image_like.setImageResource(R.mipmap.xinno);
            } else {
                image_like.setImageResource(R.mipmap.xinyes);
            }

        }

        if (postcode == 1) {
            comment = util.getgson(result, Comment.class);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }

        }
        if (postcode == 0) {
            goods = util.getgson(result, Goods.class);
            if (goods.getSuccess()) {
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                List<String> images = new ArrayList<>();
                for (int i = 0; i < goods.getData().getImagesUrl().size(); i++) {
                    images.add(goods.getData().getImagesUrl().get(i));
                }
                banner.setImages(images);
                banner.setDelayTime(1000000);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                tv_title.setText(goods.getData().getTitle());
                tv_count.setText("￥" + goods.getData().getPrice());
                tv_util.setText("/" + goods.getData().getUnit());
                tv_dis.setText(goods.getData().getRemark());
                tv_dis2.setText(goods.getData().getUnitMin() + goods.getData().getUnit() + "起批");
                tv_name.setText(goods.getData().getShopInfo().getName());
                tv_num.setText("已有" + goods.getData().getSaleVolume() + "人" + "购买");
                tv_scor.setText(goods.getData().getTotalCommonLevel() + "分");
                tv_goods.setText(goods.getData().getShopInfo().getTotalGoods());
                tv_source.setText(goods.getData().getShopInfo().getTotalSourceGoods());
                tv_people.setText(goods.getData().getShopInfo().getTotalLike());
                tv_1.setText("￥" + goods.getData().getPrice());
                tv_2.setText("/" + goods.getData().getUnit());
                tv_3.setText(goods.getData().getUnitMin() + goods.getData().getUnit() + "起批," + goods.getData().getRemark());
                tv_count_all.setText("请选择购买数量  库存" + goods.getData().getStock());
                stock = goods.getData().getStock();
                tv_num2.setText(String.valueOf(goods.getData().getUnitMin()));
                count = goods.getData().getUnitMin();

                for (int i = 0; i < 3 && i < goods.getData().getShopInfo().getShopIdentifies().size(); i++) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.shop_textview, null);
                    textView.setText(goods.getData().getShopInfo().getShopIdentifies().get(i).getName());
                    lin_10.addView(textView);
                }
                GlideUtil.loadImg(goods.getData().getShopInfo().getLogoIdAbsUrl(), image_photo);

                if (goods.getData().getImagesUrl().size() != 0) {
                    GlideUtil.loadImg(goods.getData().getImagesUrl().get(0), image_1);
                    GlideUtil.loadImg(goods.getData().getImagesUrl().get(0), image_3);
                }
                if (goods.getData().getSourceVideo().length() < 5) {
                    rela_click_2.setVisibility(View.GONE);
                }
                adapter = new MyAdapter();
                listview.setAdapter(adapter);
            }
        }


    }

    private void initview() {
        mMsvLayout = (MultipleStatusView) findViewById(R.id.msv_layout);
        listview = (ListView) findViewById(R.id.listview);

        View headview = getLayoutInflater().inflate(R.layout.detial_head, null);

        image_like = (ImageView) findViewById(R.id.image_like);
        rela_buy = findViewById(R.id.rela_buy);
        lin_10 = (LinearLayout) headview.findViewById(R.id.lin_10);
        tv_num = (TextView) headview.findViewById(R.id.tv_num);
        tv_num2 = (TextView) findViewById(R.id.tv_num);
        tv_title = (TextView) headview.findViewById(R.id.tv_title);
        tv_dis = (TextView) headview.findViewById(R.id.tv_dis);
        tv_dis2 = (TextView) headview.findViewById(R.id.tv_dis2);
        tv_name = (TextView) headview.findViewById(R.id.tv_name);
        tv_scor = (TextView) headview.findViewById(R.id.tv_scor);
        tv_goods = (TextView) headview.findViewById(R.id.tv_goods);
        tv_source = (TextView) headview.findViewById(R.id.tv_source);
        tv_people = (TextView) headview.findViewById(R.id.tv_people);
        tv_util = (TextView) headview.findViewById(R.id.tv_util);
        tv_count = (TextView) headview.findViewById(R.id.tv_count);
        image_photo = (ImageView) headview.findViewById(R.id.image_photo);
        image_1 = (ImageView) findViewById(R.id.image_1);
        image_3 = (ImageView) findViewById(R.id.imag_3);

        tv_1 = (TextView) findViewById(R.id.tv_11);
        tv_2 = (TextView) findViewById(R.id.tv_21);
        tv_3 = (TextView) findViewById(R.id.tv_31);
        tv_count_all = (TextView) findViewById(R.id.tv_count);

        headview.findViewById(R.id.tv_jiliang).setOnClickListener(this);
        headview.findViewById(R.id.tv_allgoods).setOnClickListener(this);


        tv_gotoshop = (TextView) headview.findViewById(R.id.tv_gotoshop);
        tv_gotoshop.setOnClickListener(this);
        banner = (Banner) headview.findViewById(R.id.banner);

        tv1 = (TextView) headview.findViewById(R.id.tv_1);
        tv2 = (TextView) headview.findViewById(R.id.tv_2);
        tv3 = (TextView) headview.findViewById(R.id.tv_3);

        lin_1 = headview.findViewById(R.id.lin_1);
        lin_2 = headview.findViewById(R.id.lin_2);
        lin_3 = headview.findViewById(R.id.lin_3);
        tv_defult = tv1;
        view_defult = lin_1;
        headview.findViewById(R.id.rela_click_1).setOnClickListener(this);
        rela_click_2 = headview.findViewById(R.id.rela_click_2);
        rela_click_2.setOnClickListener(this);
        headview.findViewById(R.id.rela_click_3).setOnClickListener(this);
        headview.findViewById(R.id.left_images).setOnClickListener(this);
        shopcar = (ImageView) headview.findViewById(R.id.shopcar);
        shopcar.setOnClickListener(this);
        headview.findViewById(R.id.message).setOnClickListener(this);

        listview.addHeaderView(headview);

    }

    boolean isLike = false;


    //关注
    public void onLike(View view) {

        get("api/user/",995);


    }

    int paytype = -1;


    //加入购物车
    public void onCar(View view) {
        get("api/user/",994);


    }

    //联系客服
    public void onMessage(View view) {
        get("api/user/",996);

    }




    public void onDismis(View view) {
        paytype = 1;
        rela_buy.setVisibility(View.GONE);
    }

    public void onBuy(View view) {
        get("api/user/",993);

    }

    public void onDefult(View view) {
        //zzmkbls
    }

    int count = 1;

    public void onAdd(View view) {
        if (count > stock) {
            showToast("购买量大于库存");
            return;
        }
        count++;
        tv_num2.setText(String.valueOf(count));
    }

    public void onReduc(View view) {
        if (count > goods.getData().getUnitMin()) {
            count--;
            tv_num2.setText(String.valueOf(count));
        }
    }

    public void onOK(View view) {
        if (paytype == 0) {
            JsonObject object = new JsonObject();
            object.addProperty("goodsId", goods.getData().getId());
            object.addProperty("count", "1");
            Http(HttpMethod.POST, "api/shopCar", object.toString(), 99);
            return;
        }

        if (goods.getData().getUnitMin() > goods.getData().getStock()) {
            showToast("库存不足,无法购买");
            return;
        }
        String image = "";
        if (goods.getData().getImagesUrl().size() > 0) {
            image = goods.getData().getImagesUrl().get(0);
        }
        goods.getData().getImagesUrl().get(0);
        List<String> list = Arrays.asList(image, goods.getData().getTitle(), goods.getData().getRemark(), goods.getData().getPrice(),
                goods.getData().getUnit(), String.valueOf(count), goods.getData().getId());
        startActivity(new Intent(this, OrderSureActivity.class)
                .putExtra("list", (Serializable) list)
        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_click_1:
                stata = 1;
                setdefult();
                break;
            case R.id.rela_click_2:
                stata = 2;
                setdefult();
                break;
            case R.id.rela_click_3:
                stata = 3;
                setdefult();
                break;
            case R.id.tv_gotoshop:
                startActivity(new Intent(this, ShopActivity.class)
                        .putExtra("id", goods.getData().getShopId()));
                break;
            case R.id.left_images:
                finish();
                break;
            case R.id.shopcar:
                get("api/user/",998);

                break;
            case R.id.tv_jiliang:
                startActivity(new Intent(this, JiLiangActivity.class)
                        .putExtra("data", goods.getData().getMeasure())
                );
                break;
            case R.id.tv_allgoods:
                startActivity(new Intent(this, ShopActivity.class)
                        .putExtra("id", goods.getData().getShopId()));
                break;
            case R.id.message:
                get("api/user/",997);

                break;

        }

    }

    void setdefult() {
        switch (stata) {
            case 1:
                tv_defult.setTextColor(getResources().getColor(R.color.text_black));
                view_defult.setVisibility(View.GONE);
                tv_defult = tv_1;
                view_defult = lin_1;
                tv_defult.setTextColor(getResources().getColor(R.color.them));
                view_defult.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_defult.setTextColor(getResources().getColor(R.color.text_black));
                view_defult.setVisibility(View.GONE);
                tv_defult = tv_2;
                view_defult = lin_2;
                tv_defult.setTextColor(getResources().getColor(R.color.them));
                view_defult.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_defult.setTextColor(getResources().getColor(R.color.text_black));
                view_defult.setVisibility(View.GONE);
                tv_defult = tv_3;
                view_defult = lin_3;
                tv_defult.setTextColor(getResources().getColor(R.color.them));
                view_defult.setVisibility(View.VISIBLE);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (stata == 3) {
                if (comment != null)
                    return comment.getData2().getData().size();
                else {
                    get("api/comment/goods/" + getintent("id"), 1);
                    return 0;
                }
            }
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (stata == 1) {
                view = getLayoutInflater().inflate(R.layout.item_cpxq, null);

                LinearLayout lin_1 = (LinearLayout) view.findViewById(R.id.lin_1);
                TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
                tv_content.setText(goods.getData().getContentText());
                if (goods.getData().getDescribe() != null && goods.getData().getDescribe().length() > 0) {
                    List<Gson_string> list = jsonToArrayList(goods.getData().getDescribe(), Gson_string.class);
                    for (int i = 0; i < list.size(); i++) {
                        View view2 = getLayoutInflater().inflate(R.layout.item_rela, null);
                        if (i % 2 == 0) {
                            view2.setBackgroundColor(getResources().getColor(R.color.common));
                        }
                        ((TextView) view2.findViewById(R.id.key)).setText(list.get(i).getKey().toString());
                        ((TextView) view2.findViewById(R.id.value)).setText(list.get(i).getValue().toString());
                        lin_1.addView(view2);
                    }
                }
                if (goods.getData().getContentImgsUrl() != null) {
                    for (int i = 0; i < goods.getData().getContentImgsUrl().size(); i++) {
                        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(GoodsDetialActivity.this).inflate(R.layout.layout_img, null);
               //         ImageView scrollView = (ImageView) LayoutInflater.from(GoodsDetialActivity.this).inflate(R.layout.layout_img,null);
                        ImageView image_logo = (ImageView) linearLayout.findViewById(R.id.image_logo);
                        GlideUtil.loadImg(goods.getData().getContentImgsUrl().get(i), image_logo);
                        lin_1.addView(linearLayout);
                    }
                }


            }
            if (stata == 2) {
                view = getLayoutInflater().inflate(R.layout.item_list2, null);
                TextView tv_title = (TextView) view.findViewById(R.id.title);
                tv_title.setText(goods.getData().getSourceText());
                NiceVideoPlayer mNiceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.nice_video_player);
                mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
                mNiceVideoPlayer.setUp(goods.getData().getSourceVideoUrl(), null);
                TxVideoPlayerController controller = new TxVideoPlayerController(GoodsDetialActivity.this);
//                Glide.with(GoodsDetialActivity.this).load()
//                        .error(R.mipmap.tt_mr)
//                        .into(controller.imageView());
                mNiceVideoPlayer.setController(controller);

            }
            if (stata == 3) {
                view = getLayoutInflater().inflate(R.layout.item_devlop, null);
                RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

                ImageView image_photo = (ImageView) view.findViewById(R.id.image_photo);
                View rela_all = view.findViewById(R.id.rela_all);
                LinearLayout lin_message = (LinearLayout) view.findViewById(R.id.lin_message);
                TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
                TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                TextView text1 = (TextView) view.findViewById(R.id.text1);
                TextView text2 = (TextView) view.findViewById(R.id.text2);
                TextView text3 = (TextView) view.findViewById(R.id.text3);
                GlideUtil.loadImg(comment.getData2().getData().get(position).getUser().getImgUrl(), image_photo);
                tv_time.setText(comment.getData2().getData().get(position).getGmtCreate());
                tv_content.setText(comment.getData2().getData().get(position).getComment());
                tv_name.setText(comment.getData2().getData().get(position).getUser().getName());
                ratingBar.setStar(comment.getData2().getData().get(position).getCommentLevel());
                for (int i = 0; i < comment.getData2().getData().get(position).getImgsAbsUrl().size(); i++) {
  //                  ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_image, null);
                    HorizontalScrollView horizontalScrollView = (HorizontalScrollView) getLayoutInflater().inflate(R.layout.item_image, null);
                    ImageView imageView = (ImageView) horizontalScrollView.findViewById(R.id.hs_img);
                    GlideUtil.loadImg(comment.getData2().getData().get(position).getImgsAbsUrl().get(i), imageView);
                    int fin = i;

                    imageView.setOnClickListener((l) -> {
                        PhotoActivity.bitmap.add(comment.getData2().getData().get(position).getImgsAbsUrl().get(fin));
                        startActivity(new Intent(getApplicationContext(), PhotoActivity.class).putExtra("ID", fin)
                        );
                    });
                    lin_message.addView(horizontalScrollView);
                }

                if (comment.getData2().getData().get(position).getReplyComment() == null) {
                    rela_all.setVisibility(View.GONE);
                } else {
                    rela_all.setVisibility(View.VISIBLE);
                    text1.setText(comment.getData2().getData().get(position).getReplyComment().getUser().getName());
                    text2.setText("回复 " + comment.getData2().getData().get(position).getUser().getName());
                    text3.setText(comment.getData2().getData().get(position).getReplyComment().getComment());
                }
            }

            return view;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
            GlideUtil.loadImg((String) path, imageView);
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    class Gson_string {

        @SerializedName("key")
        private String key;
        @SerializedName("value")
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && rela_buy.isShown()) {
            rela_buy.setVisibility(View.GONE);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
