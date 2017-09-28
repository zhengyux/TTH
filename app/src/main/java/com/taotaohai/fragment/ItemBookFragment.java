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
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyItemBookRecyclerViewAdapter adapter = null;

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
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itembook_list, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        inithttp();
    }

    public void inithttp() {
        switch (stata) {
            case 0:
                get("api/GoodsOrder/allOrderList", 0);
                break;
            case 2:
                get("api/GoodsOrder/orderList/2", 0);
                break;
            case 3:
                get("api/GoodsOrder/orderList/3", 0);
                break;
            case 4:
                get("api/GoodsOrder/orderList/4", 0);
                break;
            case 5:
                get("api/GoodsOrder/orderList/5", 0);
                break;

        }

    }

    @Override
    public void onSuccess(String data, int postcode) {
        super.onSuccess(data, postcode);
        Book book = util.getgson(data, Book.class);
        if (util.isSuccess(book, getActivity())) {
            if (adapter == null) {
                adapter = new MyItemBookRecyclerViewAdapter(book.getData(), mListener).setcontent(getActivity());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setdata(book.getData());
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
