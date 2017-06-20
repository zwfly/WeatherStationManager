package com.yurunsd.weatherstationmanager.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.yurunsd.weatherstationmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/19.
 */

public class RecycAdapter<T> extends RecyclerView.Adapter<RecycAdapter.ViewHolder> {
    protected final List<T> mData;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongClickListener;
    private int times;

    public RecycAdapter(Context ctx, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d("TAG", "onCreateViewHolder() called with: " + "parent = [" + parent + "], viewType = [" + viewType + "]"+",times="+"["+(++times)+"]");
        final ViewHolder holder = new ViewHolder(mInflater.inflate(R.layout.item_devicelist, parent, false));

        //设置点击事件监听
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, holder.getLayoutPosition());
                }
            });

        }
        if (mLongClickListener != null) {

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(v, holder.getLayoutPosition());
                    //返回true，拦截点击事件继续往下传递，不触发单击事件的响应
                    return true;
                }
            });
        }
        return holder;
    }

    /**
     * 数据的绑定与显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder() called with: " + "holder = [" + holder + "], position = [" + position + "]");
        holder.tv.setText((String) mData.get(position));
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }


    protected final static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv_item1);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int pos);
    }
}
