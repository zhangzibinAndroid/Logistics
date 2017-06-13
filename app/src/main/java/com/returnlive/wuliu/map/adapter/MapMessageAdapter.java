package com.returnlive.wuliu.map.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.map.utils.MapMessageUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by 张梓彬 on 2017/6/9 0009.
 */

public class MapMessageAdapter extends RecyclerView.Adapter<MapMessageAdapter.ViewHolder> implements View.OnClickListener {
    private List<MapMessageEntity> list;
    private OnItemClickListener mOnItemClickListener = null;
    public MapMessageAdapter(List<MapMessageEntity> list) {
        this.list = list;
    }
    int mSelect = 0;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_show_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        AutoUtils.autoSize(view);
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MapMessageEntity mapMessageEntity = list.get(position);
        holder.title.setText(mapMessageEntity.getTitle());
        holder.tvTimeOne.setText(MapMessageUtils.getTime(mapMessageEntity.getTime()));
        holder.tvMileageOne.setText(MapMessageUtils.getLength(mapMessageEntity.getLength()));
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
        if(mSelect==position){
            holder.title.setBackgroundResource(R.drawable.tv_top_conner_checked);
            holder.lay_message.setBackgroundResource(R.drawable.tv_bottom_conner_checked);
            holder.title.setTextColor(0XFFFFFFFF);
            holder.tvTimeOne.setTextColor(0XFF4287FF);
            holder.tvMileageOne.setTextColor(0XFF4287FF);
        }else{
            holder.title.setBackgroundResource(R.drawable.tv_top_conner);
            holder.lay_message.setBackgroundResource(R.drawable.tv_bottom_conner);
            holder.title.setTextColor(0XFF747474);
            holder.tvTimeOne.setTextColor(0XFF000000);
            holder.tvMileageOne.setTextColor(0XFF747474);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }


    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView tvTimeOne;
        TextView tvMileageOne;
        AutoLinearLayout lay_message;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.function_title_one);
            tvTimeOne = (TextView) itemView.findViewById(R.id.tv_time_one);
            tvMileageOne = (TextView) itemView.findViewById(R.id.tv_mileage_one);
            lay_message = (AutoLinearLayout) itemView.findViewById(R.id.lay_message);

        }


    }
}
