package com.returnlive.wuliu.utils;

import android.content.Context;
import android.widget.ImageView;

import com.returnlive.wuliu.R;
import com.youth.banner.loader.ImageLoader;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 作者： 张梓彬
 * 日期： 2017/6/1 0001
 * 时间： 下午 3:58
 * 描述： 轮播图重写图片加载器
 */

public class GlideImageLoader extends ImageLoader{
    ImageOptions imageOptions;
    public GlideImageLoader() {
        imageOptions = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setFailureDrawableId(R.mipmap.loadfailed)//设置加载失败后的图片
                .setLoadingDrawableId(R.mipmap.loading)//设置加载过程中的图片
                .build();
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        x.image().bind(imageView, (String) path,imageOptions);
    }


}
