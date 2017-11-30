package com.taotaohai.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hedgehog.ratingbar.RatingBar;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;
import com.taotaohai.GlobalParams;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Book;
import com.taotaohai.bean.Photo;
import com.taotaohai.myview.MyBitmapImageViewTarget;
import com.taotaohai.myview.MyGridView;
import com.taotaohai.util.GlideUtil;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Refund extends BaseActivity {
    int photosize = 4;

    private EditText editText;
    private MyGridView gridview;
    private GridAdapter adapter;
    //    public List<ImageHasSmall> drr = new ArrayList<>();
    public ArrayList<String> images = new ArrayList<String>();
    public ArrayList<String> images_url = new ArrayList<String>();
    private float dp;
    private String coverimgurl;
    public List<List<String>> drrlist = new ArrayList<List<String>>();
    //    private PopHuaTi popHuaTi;
    String coliyi = "";
    private String content;
    Book.Data data;
    private TextView tv_refund;
    private RadioButton radio1;
    private RadioButton radio2;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = (Book.Data) getIntent().getSerializableExtra("data");
        init();

    }

    protected void init() {
        setContentView(R.layout.activity_refound);
        setTitle("评价");
        /*time*/


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        gridview = (MyGridView) findViewById(R.id.noScrollgridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        editText = (EditText) findViewById(R.id.editText);

        ImageView image_photo = (ImageView) findViewById(R.id.image_photo);
        TextView text_content = (TextView) findViewById(R.id.text_content);
        TextView tv_guige = (TextView) findViewById(R.id.tv_guige);
        TextView tv_sigalmoney = (TextView) findViewById(R.id.tv_sigalmoney);
        TextView tv_28 = (TextView) findViewById(R.id.tv_28);
        TextView tv_count = (TextView) findViewById(R.id.tv_count);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        TextView tv_money = (TextView) findViewById(R.id.tv_money);
        tv_refund = (TextView) findViewById(R.id.tv_refund);
        tv_refund.setOnClickListener(v -> showchoose());

        GlideUtil.loadImg(data.getExt().getImgId(), image_photo);
        text_content.setText(data.getExt().getGoodsName());
        tv_guige.setText(data.getExt().getRemark());
        tv_sigalmoney.setText("￥：" + data.getExt().getPrice());
        tv_28.setText("/" + data.getExt().getUnit());
        tv_count.setText("x" + data.getExt().getAcount());
        tv_money.setText("￥" + data.getExt().getTotalPrice());


        gridviewInit();

    }

    int count2 = 0;
    int count = 0;

//    @Override
//    protected void imageSucceedHasSmall(String result, String which) {
//        super.imageSucceedHasSmall(result, which);
//        Gson gson = new Gson();
//        ImageHasSmall imageHasSmall = gson.fromJson(result, ImageHasSmall.class);
//        drr.add(imageHasSmall);
//        count1++;
//        if (count1 == 3) {
//            count1 = 0;
//            isOK = true;
//        }
//        if (drr.size() == images.size()) {
//            pot();
//        }
//    }

//
//    @Override
//    protected void imageSucceed(String image, String which) {
//        super.imageSucceed(image, which);
//        images_url.add(image);
//
//    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater listContainer;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public class ViewHolder {
            public ImageView image;
            public Button bt;
        }

        public GridAdapter(Context context) {
            listContainer = LayoutInflater.from(context);
        }

        public int getCount() {
            if (images.size() < photosize) {
                return images.size() + 1;
            } else {
                return images.size();
            }
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int sign = position;
            // 自定义视图
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                // 获取list_item布局文件的视图

                convertView = listContainer.inflate(
                        R.layout.grid_item_launchproduct2, null);

                // 获取控件对象
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                holder.bt = (Button) convertView
                        .findViewById(R.id.item_grida_bt);
                // 设置控件集到convertView
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 10) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.pj_add));
                holder.bt.setVisibility(View.GONE);
            } else if (position == images.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.pj_add));
                holder.bt.setVisibility(View.GONE);
                if (position == photosize) {
                    holder.image.setImageBitmap(BitmapFactory.decodeResource(
                            getResources(), R.mipmap.pj_add));
                }
            } else {
                Glide.with(getApplicationContext())
                        .load(images.get(position) + "")
                        .asBitmap()
                        .centerCrop()
                        .error(getResources().getDrawable(R.mipmap.pj_add))
                        .into(new MyBitmapImageViewTarget(holder.image));//图片不变形
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                        .into(holder.image);
                holder.bt.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
//                        drr.remove(sign);
                        images.remove(sign);
                        PhotoActivity.bitmap.remove(sign);
                        gridviewInit();
                    }
                });
            }
            return convertView;
        }
    }

    public void gridviewInit() {
        adapter = new GridAdapter(this);
        adapter.setSelectedPosition(0);
        int size = 0;
        if (images.size() < photosize) {
            size = images.size() + 1;
        } else {
            size = images.size();
        }
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(Refund.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                if (position == photosize && images.size() == photosize) {
                    http();
                } else if (position == 10 && images.size() == photosize) {
                } else {
                    if (position == images.size()) {
//                        imagepicture(51, 52);
//                        showDialogs(view);
                        CommonUtils.launchActivityForResult(Refund.this, PhotoSelectorActivity.class, 0, images.size(), images);
                    } else {
                        Intent intent = new Intent(getApplicationContext(),
                                PhotoActivity.class);
                        intent.putExtra("ID", position);
                        startActivity(intent);
                    }
                }

            }
        });
    }

    private int width;

    void http() {
//        RequestParams p = new RequestParams(ConstantValue.URL + "dynamic/topic");
//        p.setConnectTimeout(1000 * 10);
//        p.addBodyParameter("username", username);
//        p.addBodyParameter("password", password);
//        p.addBodyParameter(GlobalParams.keypackage, GlobalParams.package_);
//        cancelable = x.http().post(p, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Gson gson = new Gson();
//                        popHuaTi = gson.fromJson(result, PopHuaTi.class);
//                        try {
//                            if ("1".equals(new JSONObject(result).getString("rtcode"))) {
//                                pop();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//                    }
//
//                    @Override
//                    public void onFinished() {
//                    }
//                }
//        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                @SuppressWarnings("unchecked")
                List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
                if (photos == null || photos.isEmpty())
                    return;
                StringBuffer sb = new StringBuffer();
                for (PhotoModel photo : photos) {
//                    drr.add((photo.getOriginalPath()));
                    images.add((photo.getOriginalPath()));
                    PhotoActivity.bitmap.add(photo.getOriginalPath());
                }
                gridviewInit();
                adapter.notifyDataSetChanged();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoActivity.bitmap.clear();
    }


    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

    boolean isOK = true;

    public void onComment(View view) {
        image_urls.clear();
        showSpot();
        final HashMap<String, String> has = new HashMap<>();
        has.put("name", "file");
        if (images.size() == 0) {
            pot();
            return;
        }
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOK) {
                    isOK = false;
                    if (count == images.size()) return;
                    sendImage("file/singleUpload", has, GlobalParams.NOSPOT_CODE, images.get(count), getApplicationContext(), 200, 200, "file");
                    count++;
                }
                editText.postDelayed(this, 10);
            }
        }, 10);
    }

    List<String> image_urls = new ArrayList<>();

    @Override
    public void onSuccess(String result, int postcode) {
        super.onSuccess(result, postcode);
        switch (postcode) {
            case GlobalParams.NOSPOT_CODE:
                Photo image = util.getgson(result, Photo.class);
                if (util.isSuccess(image, getApplicationContext())) {
                    image_urls.add(image.getData().getId());
                }
                break;
            case 10:
                BaseBean bean = util.getgson(result, BaseBean.class);
                if (util.isSuccess(bean, getApplication())) {
                    showToast("退款请求成功");
                    finish();
                }
                break;
        }

    }

    @Override
    public void onError(Throwable ex, int code) {
        super.onError(ex, code);
    }

    @Override
    public void onFinished(int code) {
        super.onFinished(code);
        if (code == 10) {
            setSpotNull();
            return;
        }
        count2++;
        isOK = true;
        if (count2 == images.size()) {
            pot();
        }
    }

    private void pot() {
        if (!radio1.isChecked() && !radio2.isChecked()) {
            showToast("请选择退款类型");
            return;
        }
        if (!ischoose) {
            showToast("请选择退款原因");
            return;
        }
        HashMap<String, String> has = new HashMap<>();
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < image_urls.size(); i++) {
            jsonArray.add(image_urls.get(i));
        }
        JsonObject object = new JsonObject();
        object.add("refundImgIds", jsonArray);
        object.addProperty("refundReason", tv_refund.getText().toString());
        object.addProperty("refundReasonDetail", editText.getText().toString().trim());
        object.addProperty("refundType", editText.getText().toString().trim());
        if (radio1.isChecked()) {
            object.addProperty("refundType", "0");
        } else {
            object.addProperty("refundType", "1");
        }
        object.addProperty("orderId", getintent("id"));

        if (getintent("goods").length() > 0) {
            object.addProperty("goodsspcInfo", getintent("goods"));
        } else {
            object.addProperty("goodsspcInfo", "红色，xl");
        }

        Http(HttpMethod.PUT, "api/goodsorder/refund/" + data.getId(), object.toString(), 10);
    }

    private static final List<String> options1Items = new ArrayList<>();
    boolean ischoose = false;

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
                ischoose = true;
                tv_refund.setText(s);

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
}
