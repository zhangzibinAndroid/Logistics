package com.returnlive.wuliu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.base.MyBaseAdapter;
import com.returnlive.wuliu.entity.OrderDetailsAllEntity;
import com.returnlive.wuliu.utils.DateUtilsTime;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/19 0019
 * 时间： 下午 3:12
 * 描述： 订单详情全部页面的适配器
 */

public class OrderDetailsAllAdapter extends MyBaseAdapter<OrderDetailsAllEntity> {
    public OrderDetailsAllAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_details_items, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrderDetailsAllEntity orderDetailsAllEntity = getItem(position);
        viewHolder.tv_isfinish.setText(orderDetailsAllEntity.getIsFinish());

        DateUtilsTime utils = new DateUtilsTime();
        //将时间戳转换成date
        try {
            viewHolder.tv_order_time.setText(utils.getGoodsTimeStamp(orderDetailsAllEntity.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.tv_order_start_place.setText(orderDetailsAllEntity.getStartPlace());
        viewHolder.tv_order_end_place.setText(orderDetailsAllEntity.getEndPlace());
        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.tv_isfinish)
        TextView tv_isfinish;
        @ViewInject(R.id.tv_order_time)
        TextView tv_order_time;
        @ViewInject(R.id.tv_order_start_place)
        TextView tv_order_start_place;
        @ViewInject(R.id.tv_order_end_place)
        TextView tv_order_end_place;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
