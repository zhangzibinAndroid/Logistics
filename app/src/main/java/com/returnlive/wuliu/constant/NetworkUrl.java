package com.returnlive.wuliu.constant;


import com.returnlive.wuliu.gson.GsonParsing;

/**
 * @author 张梓彬
 * Data : 2017/5/18 0018
 * Time : 下午 3:13
 * Describe : 网络请求接口
 */
public class NetworkUrl {
    public static final String VERSION_UPDATE = "http://192.168.0.14/phpShare";
    public static final String WULIU_URL = "http://wuliu.returnlive.com/mobile/";
    public static final String REGISTER_URL = WULIU_URL+"User/register";
    public static final String SENDCODE_URL = WULIU_URL+"User/sendCode";
    public static final String LOGIN_URL = WULIU_URL+"User/login";
    public static final String SENDCODE_SECOND_URL = WULIU_URL+"User/sendCode";
    public static final String FGORGET_PASSWORD_URL = WULIU_URL+"User/resetPwd";
    public static final String EXIT_SYSTEM = WULIU_URL+"Info/outLogin/uid/"+ GsonParsing.uid+"/"+GsonParsing.zSesson+GsonParsing.uid+"/"+GsonParsing.mSesson;

}
