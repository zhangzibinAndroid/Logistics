package com.returnlive.wuliu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returnlive.wuliu.R;
import com.returnlive.wuliu.base.MyBaseAdapter;
import com.returnlive.wuliu.entity.CityItemEntity;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 作者： 张梓彬
 * 日期： 2017/5/25 0025
 * 时间： 下午 4:50
 * 描述： liseview城市三级联动适配器
 */

public class CityPopWindowAdapter extends MyBaseAdapter<CityItemEntity> {
    public CityPopWindowAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_goods_pcd, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CityItemEntity cityItemEntity = getItem(position);
        viewHolder.tv_name.setText(cityItemEntity.getCityName());
        if (cityItemEntity.isChoose) {
            viewHolder.rl_name.setBackgroundResource(R.mipmap.notification_green);

        } else {
            viewHolder.rl_name.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        return convertView;
    }


    static class ViewHolder {
        @ViewInject(R.id.tv_name)
        TextView tv_name;
        @ViewInject(R.id.rl_name)
        AutoRelativeLayout rl_name;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
