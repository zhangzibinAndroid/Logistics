package com.returnlive.wuliu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 张梓彬 on 2017/5/19 0019.
 */

public class SharedPreferencesUtils {
    public boolean loginState = true;
    Context context;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
    }

    public void sharedPreferenceSave(boolean value){
        SharedPreferences preferences=context.getSharedPreferences("loginState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("isOwner",value);
        editor.commit();
    }

    public void sharedPreferenceRead(){
        SharedPreferences preferences=context.getSharedPreferences("loginState", Context.MODE_PRIVATE);
        loginState = preferences.getBoolean("isOwner",false);
    }
}
