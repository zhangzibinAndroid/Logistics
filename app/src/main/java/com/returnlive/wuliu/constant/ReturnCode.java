package com.returnlive.wuliu.constant;

/**
 * 作者：张梓彬
 * 日期：2017/5/17 0017
 * 时间：下午 8:36
 * 描述：后台给的返回码
 */
public class ReturnCode {
    public static final String SUCCESS = "success";//成功状态
    public static final String NAME_EMPITY = "-11101";//用户名为空
    public static final String NAME_ALREADY_SAVE = "-11102";//手机号码已注册
    public static final String PHONE_ERROR = "-11103";//手机号格式错误
    public static final String PWED_EMPITY = "-11104";//密码为空
    public static final String PWED_ERROR = "-11105";//密码应在6-30位之间
    public static final String SEND_CODE_ERROR = "-11106";//验证码错误
    public static final String SEND_CODE_START = "-11107";//短信已发送
    public static final String SEND_CORD_URL_ERROR = "-11108";//短信接口错误
    public static final String SEND_CODE_RECEIVER_ERROR = "-11109";//短信码获取失败
    public static final String PHONE_NOT_REGISTERED = "-11110";//用户名未注册
    public static final String PWDS_ERROR = "-11111";//密码错误
    public static final String SHIPPER_AUTHENTICATION_FAILED = "-11112";//货主认证失败
    public static final String OPPTIONS_AUTHENTICATION_FAILED = "-11113";//车源认证失败
    public static final String USER_DETAILS_OBTAIN_FAILED= "-11114";//用户详情获取失败
    public static final String ILLEGAL_REQUEST= "-11201";//非法请求
    public static final String LOGIN_TIMEOUT= "-11202";//登录超时 24小时内无任何操作
    public static final String ANOTHER_PLACE_LOGIN= "-11203";//异地登录
    public static final String EXIT_FAILED= "-11204";//退出失败
    public static final String GOODS_OPTIONS_INFORMATION_ADDFAILED = "-11301";//货源OR车源信息添加失败
    public static final String GOODS_OPTIONS_LIST_OBTAIN_FAILED= "-11302";//货源OR车源列表获取失败
    public static final String GOODS_OPTIONS_IDEMPITY= "-11303";//	货源OR车源id不可为空
    public static final String GOODS_OPTIONS_DETAILS_OBBTAIN_FAILED= "-11304";//货源OR车源详情获取失败
}
