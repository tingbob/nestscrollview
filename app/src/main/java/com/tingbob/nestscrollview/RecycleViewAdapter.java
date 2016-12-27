package com.tingbob.nestscrollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by nlv on 15/6/8.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ItemHolder> {
    private List<String> mList;
    protected Context mContext;
    private int mLayout;

    public RecycleViewAdapter(Context context, List<String> list, int layout) {
        mContext = context;
        mList = list;
        mLayout = layout;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflateLayout());
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.mStringTextView.setText(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public View inflateLayout() {
        return inflateLayout(mLayout);
    }

    public View inflateLayout(int res) {
        LayoutInflater inf = LayoutInflater.from(mContext);
        return inf.inflate(res, null);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        public TextView mStringTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            mStringTextView = (TextView) itemView.findViewById(R.id.string_textview);
        }
    }
}
