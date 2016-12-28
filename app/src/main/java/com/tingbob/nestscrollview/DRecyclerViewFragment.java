package com.tingbob.nestscrollview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tingbob on 2016/12/26.
 */

public class DRecyclerViewFragment extends Fragment {

    private RecycleViewAdapter<String> mAdapter1;
    private RecycleViewAdapter<ItemSticky> mAdapter2;

    public static DRecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        DRecyclerViewFragment fragment = new DRecyclerViewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_drecyclerview, null);
        final RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setNestedScrollingEnabled(false);
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add("1recyclerView_" + i);
        }
        mAdapter1 = new RecycleViewAdapter<>(getContext(), list1, R.layout.item_string_list);
        recyclerView1.setAdapter(mAdapter1);

        final TextView tv_sticky = (TextView) view.findViewById(R.id.string_textview);
        tv_sticky.setBackgroundResource(R.color.orange);

        final RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setNestedScrollingEnabled(false);
        ArrayList<ItemSticky> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                tv_sticky.setText("1recyclerView_" + i);
            }
            ItemSticky itemSticky = new ItemSticky();
            itemSticky.setType(Constants.ITEM_HEADER);
            itemSticky.setText("1recyclerView_" + i);
            itemSticky.setPos(i);
            list2.add(itemSticky);
            for (int j = 0; j < 10; j++) {
                ItemSticky itemStickyC = new ItemSticky();
                if (j == 0) {
                    itemStickyC.setType(Constants.ITEM_POSTER);
                } else {
                    itemStickyC.setType(Constants.ITEM_DESC);
                }
                itemStickyC.setText("2recyclerView_" + i + j);
                list2.add(itemStickyC);
            }
        }
        mAdapter2 = new RecycleViewAdapter<>(getContext(), list2, R.layout.item_string_list);
        recyclerView2.setAdapter(mAdapter2);

        final NestedScrollView nestedScrollView1 = (NestedScrollView) view.findViewById(R.id.ns_1);

        final NestedScrollView nestedScrollView2 = (NestedScrollView) view.findViewById(R.id.ns_2);
        nestedScrollView2.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View view = v.getChildAt(v.getChildCount() - 1);
                int totalItemCount = linearLayoutManager2.getItemCount();
                int itemHeight = view.getHeight() / totalItemCount;
                int pos = scrollY / itemHeight;
                ItemSticky itemSticky = (ItemSticky) mAdapter2.getItem(pos);
                if (itemSticky.getType() == Constants.ITEM_HEADER) {
                    tv_sticky.setText(itemSticky.getText());
                    nestedScrollView1.smoothScrollTo(0, itemSticky.getPos()*itemHeight);
                }
            }
        });

        mAdapter1.setOnItemClickListener(new RecycleViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int totalItemCount = linearLayoutManager2.getItemCount();
                int itemHeight = recyclerView2.getHeight() / totalItemCount;
                String title = (String) mAdapter1.getItem(position);
                for (int i = 0; i < totalItemCount; i++) {
                    ItemSticky itemSticky = (ItemSticky) mAdapter2.getItem(i);
                    if (title.equals(itemSticky.getText())) {
                        nestedScrollView2.smoothScrollTo(0, i * itemHeight);
                        break;
                    }
                }
            }
        });

        return view;
    }
}
