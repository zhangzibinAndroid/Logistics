package com.returnlive.wuliu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.base.MyBaseAdapter;
import com.returnlive.wuliu.entity.GoodsSourceListEntity;
import com.returnlive.wuliu.utils.DateUtilsTime;
import com.returnlive.wuliu.view.RoundImageView;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/5/31 0031
 * 时间： 下午 2:35
 * 描述： 货源适配器
 */

public class GoodsSourceAdapter extends MyBaseAdapter<GoodsSourceListEntity.GoosdsBean>{
    Context context;
    private OnImgClickListener onImgClickListener = null;
    public GoodsSourceAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarSourceAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_goods_listview, null);
            viewHolder = new CarSourceAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.img_goods_callphone.setTag(position);
            AutoUtils.autoSize(convertView);

        }else {
            viewHolder = (CarSourceAdapter.ViewHolder) convertView.getTag();
        }
        DateUtilsTime dateUtilsTime = new DateUtilsTime();
        //此处设置控件
        GoodsSourceListEntity.GoosdsBean goodsSourceListEntity = getItem(position);
        viewHolder.img_goods_icon.setImageResource(R.mipmap.portrait_sun);
        viewHolder.tv_goods_release_time.setText("发布时间："+dateUtilsTime.getDate(goodsSourceListEntity.getCreate_time()));
        viewHolder.tv_goods_send_number.setText("发货：xxx单");
        viewHolder.tv_deal_number.setText("交易：xxx单");
        viewHolder.tv_goods_name.setText("货主姓名");
        viewHolder.tv_goods_start.setText(goodsSourceListEntity.getStart());
        viewHolder.tv_goods_end.setText(goodsSourceListEntity.getEnd());
        viewHolder.tv_goods_remark.setText("备注说明");
        viewHolder.tv_goods_cartype.setText("车型");
        viewHolder.tv_goods_carlength.setText("车长");
        viewHolder.tv_goods_weight.setText(goodsSourceListEntity.getWeight()+"吨/"+goodsSourceListEntity.getVolume()+"方");
        viewHolder.tv_goods_traveltime.setText(dateUtilsTime.getDay(goodsSourceListEntity.getCar_time())+" 全天可装货");
        viewHolder.img_goods_callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImgClickListener != null) {
                    //注意这里使用getTag方法获取position
                    onImgClickListener.onImgClickListener(v,(int)v.getTag());
                }
            }
        });
        return convertView;
    }

    public void setOnImgClickListener(OnImgClickListener listener) {
        this.onImgClickListener = listener;
    }

    public static interface OnImgClickListener{
        void onImgClickListener(View view, int position);
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
