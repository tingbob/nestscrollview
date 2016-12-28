package com.tingbob.nestscrollview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list1.add("1recyclerView_" + i);
        }
        mAdapter1 = new RecycleViewAdapter<>(getContext(), list1, R.layout.item_string_list);
        recyclerView1.setAdapter(mAdapter1);

        final RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        ArrayList<ItemSticky> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemSticky itemSticky = new ItemSticky();
            itemSticky.setType(Constants.ITEM_HEADER);
            itemSticky.setText("1recyclerView_" + i);
            itemSticky.setGroupPos(i);
            list2.add(itemSticky);
            for (int j = 0; j < 10; j++) {
                ItemSticky itemStickyC = new ItemSticky();
                itemStickyC.setType(Constants.ITEM_DESC);
                itemStickyC.setText("2recyclerView_" + i + j);
                list2.add(itemStickyC);
            }
        }
        mAdapter2 = new RecycleViewAdapter<>(getContext(), list2, R.layout.item_string_list);
        recyclerView2.setAdapter(mAdapter2);

        recyclerView2.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    ItemSticky itemSticky = (ItemSticky) ((RecycleViewAdapter)recyclerView.getAdapter()).getItem(firstItemPosition);
                    if (itemSticky != null && itemSticky.getType() == Constants.ITEM_HEADER) {
                        recyclerView1.smoothScrollToPosition(itemSticky.getGroupPos());
                    }
                }
            }
        });

        mAdapter1.setOnItemClickListener(new RecycleViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int totalItemCount = linearLayoutManager2.getItemCount();
                String title = mAdapter1.getItem(position);
                for (int i = 0; i < totalItemCount; i++) {
                    ItemSticky itemSticky = mAdapter2.getItem(i);
                    if (title.equals(itemSticky.getText())) {
                        linearLayoutManager2.scrollToPositionWithOffset(i, 0);
                        break;
                    }
                }
            }
        });

        PinnedHeaderDecoration pinnedHeaderDecoration = new PinnedHeaderDecoration();
        pinnedHeaderDecoration.setPinnedTypeHeader(Constants.ITEM_HEADER);
        pinnedHeaderDecoration.registerTypePinnedHeader(Constants.ITEM_HEADER, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });

        recyclerView2.addItemDecoration(pinnedHeaderDecoration);

        return view;
    }
}
