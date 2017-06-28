package com.yurunsd.weatherstationmanager.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yurunsd.weatherstationmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/19.
 */

public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.ViewHolder> {
    protected List<Map<String, Object>> mList;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongClickListener;

    public RecycAdapter(Context ctx, List<Map<String, Object>> list) {
        mList = (list != null) ? list : new ArrayList<Map<String, Object>>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        Map<String, Object> map = mList.get(position);

        switch ((String) map.get("deviceType")) {
            case "2":

                holder.tvItem1.setText("温度: " + map.get("temperature") + " ℃");
                holder.tvItem2.setText("湿度: " + map.get("humidity") + " %");
                holder.tvItem3.setText("PM2.5: " + ((Double) map.get("PM2d5")).intValue() + " ug/m3");
                holder.tvItem4.setText("风速: " + map.get("windSpeed") + " m/s");
                holder.tvAddr.setText("" + map.get("deviceAddr"));

                break;
            default:

                break;
        }


    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }

    public void add(int pos, Map<String, Object> item) {
        mList.add(pos, item);
        notifyItemInserted(pos);
    }

    public void remove(int pos) {
        mList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clear() {
        for (int i = 0; i < this.getItemCount(); i++) {
            notifyItemRemoved(i);
        }
        mList.clear();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }


    protected final static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHeaderImg;
        TextView tvItem1;
        TextView tvItem2;
        TextView tvItem3;
        TextView tvItem4;
        TextView tvAddr;
        com.kyleduo.switchbutton.SwitchButton sbWarm;

        public ViewHolder(View itemView) {
            super(itemView);

            ivHeaderImg = (ImageView) itemView.findViewById(R.id.iv_header);
            tvItem1 = (TextView) itemView.findViewById(R.id.tv_item1);
            tvItem2 = (TextView) itemView.findViewById(R.id.tv_item2);
            tvItem3 = (TextView) itemView.findViewById(R.id.tv_item3);
            tvItem4 = (TextView) itemView.findViewById(R.id.tv_item4);
            tvAddr = (TextView) itemView.findViewById(R.id.tv_addr);
            sbWarm = (com.kyleduo.switchbutton.SwitchButton) itemView.findViewById(R.id.sb_warm);

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int pos);
    }
}
