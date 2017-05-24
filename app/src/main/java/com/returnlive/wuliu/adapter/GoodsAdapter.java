package com.returnlive.wuliu.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.base.MyBaseAdapter;
import com.returnlive.wuliu.entity.GoodsAdapterEntity;
import com.returnlive.wuliu.view.RoundImageView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/24 0024
 * 时间： 下午 5:12
 * 描述： 货源适配器
 */
public class GoodsAdapter extends MyBaseAdapter<GoodsAdapterEntity> {
    Context context;

    public GoodsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_goods_listview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //此处设置控件
        GoodsAdapterEntity goodsAdapterEntity = getItem(position);
        viewHolder.tv_goods_name.setText(goodsAdapterEntity.getName());
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.img_goods_icon)
        RoundImageView img_goods_icon;
        @ViewInject(R.id.tv_goods_release_time)
        TextView tv_goods_release_time;
        @ViewInject(R.id.tv_goods_send_number)
        TextView tv_goods_send_number;
        @ViewInject(R.id.tv_deal_number)
        TextView tv_deal_number;
        @ViewInject(R.id.img_goods_callphone)
        ImageView img_goods_callphone;
        @ViewInject(R.id.tv_goods_name)
        TextView tv_goods_name;
        @ViewInject(R.id.tv_goods_start)
        TextView tv_goods_start;
        @ViewInject(R.id.tv_goods_end)
        TextView tv_goods_end;
        @ViewInject(R.id.tv_goods_remark)
        TextView tv_goods_remark;
        @ViewInject(R.id.tv_goods_cartype)
        TextView tv_goods_cartype;
        @ViewInject(R.id.tv_goods_carlength)
        TextView tv_goods_carlength;
        @ViewInject(R.id.tv_goods_weight)
        TextView tv_goods_weight;
        @ViewInject(R.id.tv_goods_traveltime)
        TextView tv_goods_traveltime;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
