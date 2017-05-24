package com.returnlive.wuliu.gson;

import com.google.gson.Gson;
import com.returnlive.wuliu.entity.ErrorCodeEntity;
import com.returnlive.wuliu.entity.LoginSuccessEntity;
import com.returnlive.wuliu.entity.UserMessageEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * @author 张梓彬
 * Data : 2017/5/18 0018
 * Time : 上午 10:47
 * Describe : Gson解析json数据封装
 */
public class GsonParsing {
    public static String uid;
    public static String zSesson = "z_session_";
    public static String mSesson;

    //短信请求错误码解析
    public static ErrorCodeEntity sendCodeError(String json) {
        ErrorCodeEntity result = GsonUtils.parseJsonWithGson(json, ErrorCodeEntity.class);
        return result;
    }

    //登入成功uid以及sesson码解析
    public static LoginSuccessEntity gsonLogin(String json) {
        LoginSuccessEntity result = GsonUtils.parseJsonWithGson(json, LoginSuccessEntity.class);
        return result;
    }

    public static String gsonLoginSesson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        LoginSuccessEntity user = new LoginSuccessEntity();
        user.setZ_session_id(jsonObject.getString(zSesson+uid));
        return user.getZ_session_id();
    }


    public static UserMessageEntity.UserBean gsonUserMessage(String json){
        UserMessageEntity userMessage = GsonUtils.parseJsonWithGson(json,UserMessageEntity.class);
        return userMessage.getCode();
    }


}

class GsonUtils {
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }
}
