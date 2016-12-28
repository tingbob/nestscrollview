package com.tingbob.nestscrollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by nlv on 15/6/8.
 */
public class RecycleViewAdapter<T> extends RecyclerView.Adapter<RecycleViewAdapter.ItemHolder> {
    private List<T> mList;
    protected Context mContext;
    private int mLayout;

    public RecycleViewAdapter(Context context, List<T> list, int layout) {
        mContext = context;
        mList = list;
        mLayout = layout;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        if (mList.get(position) instanceof String) {
            holder.mStringTextView.setText((String)mList.get(position));
            holder.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
        } else if (mList.get(position) instanceof ItemSticky) {
            ItemSticky itemSticky = (ItemSticky) mList.get(position);
            holder.mStringTextView.setText(itemSticky.getText());
            if (itemSticky.getType() == Constants.ITEM_HEADER) {
                holder.mStringTextView.setBackgroundResource(R.color.orange);
            } else {
                holder.mStringTextView.setBackgroundResource(R.color.white);
            }
        }

    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) instanceof ItemSticky) {
            return ((ItemSticky) mList.get(position)).getType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        public TextView mStringTextView;
        private View rootView;

        public ItemHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mStringTextView = (TextView) itemView.findViewById(R.id.string_textview);
        }

        public View getRootView() {
            return rootView;
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View v, int position);
    }
}
