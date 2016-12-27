package com.tingbob.nestscrollview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by tingbob on 2016/12/26.
 */
public class OtherFragment extends Fragment {

    public static OtherFragment newInstance() {
        Bundle args = new Bundle();
        OtherFragment fragment = new OtherFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, null);
        return view;
    }
}
