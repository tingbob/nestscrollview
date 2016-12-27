package com.tingbob.nestscrollview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by tingbob on 2016/12/26.
 */

public class MenuFragment extends Fragment {

    private RecycleViewAdapter mAdapter1;
    private RecycleViewAdapter mAdapter2;


    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, null);
        RecyclerView listView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        listView1.setLayoutManager(linearLayoutManager);
        listView1.setNestedScrollingEnabled(false);
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list1.add("1listview_" + i);
        }
        mAdapter1 = new RecycleViewAdapter(getContext(), list1, R.layout.item_string_list);
        listView1.setAdapter(mAdapter1);

        RecyclerView listView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        listView2.setLayoutManager(linearLayoutManager2);
        listView2.setNestedScrollingEnabled(false);
        ArrayList<String> list2 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list2.add("2listview_" + i);
        }
        mAdapter2 = new RecycleViewAdapter(getContext(), list2, R.layout.item_string_list);
        listView2.setAdapter(mAdapter2);
        return view;
    }
}
