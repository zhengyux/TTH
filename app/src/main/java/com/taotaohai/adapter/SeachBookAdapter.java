package com.taotaohai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.taotaohai.bean.BookDet;

import java.util.List;

/**
 * Created by 2panda on 2018/1/17.
 */

public class SeachBookAdapter extends BaseAdapter{
    private List<BookDet> list = null;
    private LayoutInflater inflater = null;
    private Context context = null;

    public SeachBookAdapter(List<BookDet> list, Context context) {
        this.list=list;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return view;
    }
}
