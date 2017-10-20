package com.taotaohai.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taotaohai.R;
import com.taotaohai.activity.base.BaseFragment;
import com.taotaohai.bean.Book;
import com.taotaohai.util.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemBookFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyItemBookRecyclerViewAdapter adapter = null;
    private Book book;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemBookFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemBookFragment newInstance(int columnCount) {
        ItemBookFragment fragment = new ItemBookFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            stata = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itembook_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        inithttp();
        return view;
    }


    public void inithttp() {
        switch (stata) {
            case 0:
                get("api/GoodsOrder", 0);
                break;
            case 1:
                get("api/GoodsOrder/list/1", 0);
                break;
            case 2:
                get("api/GoodsOrder/list/2", 0);
                break;
            case 3:
                get("api/GoodsOrder/list/3", 0);
                break;
            case 4:
                get("api/GoodsOrder/list/4", 0);
                break;

        }

    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
//        data = "{\"code\":200,\"data2\":{\"data\":[{\"ext\":{\"acount\":4,\"dealTime\":null,\"gmtCreate\":\"2017-10-12 17:18:27\",\"gmtDelivery\":null,\"goodsName\":\"野生三文鱼\",\"id\":\"\",\"linkAddress\":\"\",\"linkName\":\"\",\"linkTel\":\"\",\"orderExpressCompany\":\"\",\"orderExpressNo\":\"\",\"orderId\":\"vlP9tTb6\",\"orderStatus\":0,\"payType\":0,\"price\":\"59.92\",\"remark\":\"\",\"shopName\":\"中南海\",\"totalPrice\":0,\"unit\":\"箱\"},\"gmtCreate\":\"2017-10-09 10:04:04\",\"gmtModify\":\"2017-10-09 10:04:08\",\"gmtRefund\":null,\"goodsId\":\"0ub70x\",\"id\":\"vlP9tTb6\",\"lastChangeUser\":\"\",\"orderStatus\":3,\"payType\":2,\"refundStatus\":0,\"remarks\":\"备注\",\"shopId\":\"SSP777\",\"submitTime\":\"2017-10-11 10:11:11\",\"totalPrice\":\"59.99\",\"userId\":\"PSD939\"}],\"total\":11},\"message\":\"\",\"success\":true}";
        if (util.isSuccess(data)) {
            book = util.getgson(data, Book.class);
            if (recyclerView.getAdapter() == null) {
                if (adapter == null)
                    adapter = new MyItemBookRecyclerViewAdapter(book.getData2().getData(), mListener).setcontent(getActivity());
                recyclerView.setAdapter(adapter);
            } else {
//                recyclerView.setAdapter(adapter);
                adapter.setdata(book.getData2().getData());
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    int stata = 0;

    public ItemBookFragment setstata(int stata) {
        this.stata = stata;
        return this;
    }

    public void refresh() {
        inithttp();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Book.Data item);

        void onListFragmentButton2(Book.Data item);

        void onListFragmentButton1(Book.Data item);

        void onListFragmentButton3(Book.Data mItem);
    }
}
