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

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.hedgehog.ratingbar.RatingBar;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;
import com.taotaohai.GlobalParams;
import com.taotaohai.R;
import com.taotaohai.activity.base.BaseActivity;
import com.taotaohai.bean.BaseBean;
import com.taotaohai.bean.Photo;
import com.taotaohai.myview.MyBitmapImageViewTarget;
import com.taotaohai.myview.MyGridView;
import com.taotaohai.util.util;

import org.xutils.http.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Evaluation extends BaseActivity {
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
    private RatingBar ratingBar;
    int count_rating = 0;

    @Override
    protected void inithttp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    protected void init() {
        setContentView(R.layout.activity_public_);
        setTitle("评价");
        /*time*/

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                count_rating = (int) (RatingCount + 0.6);
            }
        });
        gridview = (MyGridView) findViewById(R.id.noScrollgridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        editText = (EditText) findViewById(R.id.editText);

        gridviewInit();

    }

    int count2 = 0;
    int count = 0;


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
                holder.bt.setOnClickListener(v -> {
//                        drr.remove(sign);
                    images.remove(sign);

                    gridviewInit();
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
                        .hideSoftInputFromWindow(Evaluation.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                if (position == photosize && images.size() == photosize) {
                    http();
                } else if (position == 10 && images.size() == photosize) {
                } else {
                    if (position == images.size()) {
//                        imagepicture(51, 52);
//                        showDialogs(view);
                        CommonUtils.launchActivityForResult(Evaluation.this, PhotoSelectorActivity.class, 0, images.size(), images);
                    } else {
                        PhotoActivity.bitmap.add(images.get(position));
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
  //      showSpot();
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
                    sendImage("file/upload", has, GlobalParams.NOSPOT_CODE, images.get(count), getApplicationContext(), 200, 200, "file");
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
                    showToast("评论成功");
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
        if(count_rating==0){
            showToast("请打分");
            return;
        }
        if(editText.getText()==null||"".equals(editText.getText().toString().trim())){
            showToast("请评价");
            return;
        }
        StringBuilder sb = new StringBuilder();
        if(null==image_urls||image_urls.size()==0){
            sb.append(",");
        }
        for (int i = 0; i < image_urls.size(); i++) {
            sb.append(image_urls.get(i)).append(",");
        }
        String st = sb.toString().substring(0, sb.toString().length() - 1);
        JsonObject object = new JsonObject();

        object.addProperty("level", String.valueOf(count_rating));
        object.addProperty("comment", editText.getText().toString().trim());
        object.addProperty("images", st);

        Http(HttpMethod.POST, "api/comment/" + getintent("id") + "/reply", object.toString(), 10);
    }
}
