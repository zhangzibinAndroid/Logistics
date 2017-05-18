package com.returnlive.wuliu.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.returnlive.wuliu.R;
import com.returnlive.wuliu.constant.ReturnCode;

/**
 * @author 张梓彬
 * Data : 2017/5/18 0018
 * Time : 上午 11:39
 * Describe : 返回的错误码判断
 */
public class ErrorCode extends Activity{
    Context context;
    public ErrorCode(Context context) {
        this.context = context;
    }

    private void runOnUiShowToast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void judge(String result){
        switch (result){
            case ReturnCode.NAME_EMPITY:
                runOnUiShowToast(context.getResources().getString(R.string.name_empity));
                break;
            case ReturnCode.NAME_ALREADY_SAVE:
                runOnUiShowToast(context.getResources().getString(R.string.name_already_save));
                break;
            case ReturnCode.PHONE_ERROR:
                runOnUiShowToast(context.getResources().getString(R.string.phone_error));
                break;
            case ReturnCode.PWED_EMPITY:
                runOnUiShowToast(context.getResources().getString(R.string.pwed_empity));
                break;
            case ReturnCode.PWED_ERROR:
                runOnUiShowToast(context.getResources().getString(R.string.pwed_error));
                break;
            case ReturnCode.SEND_CODE_ERROR:
                runOnUiShowToast(context.getResources().getString(R.string.send_code_error));
                break;
            case ReturnCode.SEND_CODE_START:
                runOnUiShowToast(context.getResources().getString(R.string.send_code_start));
                break;
            case ReturnCode.SEND_CORD_URL_ERROR:
                runOnUiShowToast(context.getResources().getString(R.string.send_cord_url_error));
                break;
            case ReturnCode.SEND_CODE_RECEIVER_ERROR:
                runOnUiShowToast(context.getResources().getString(R.string.send_code_receiver_error));
                break;
            case ReturnCode.PHONE_NOT_REGISTERED:
                runOnUiShowToast(context.getResources().getString(R.string.phone_not_registered));
                break;
            case ReturnCode.PWDS_ERROR:
                runOnUiShowToast(context.getResources().getString(R.string.pwds_error));
                break;
            case ReturnCode.SHIPPER_AUTHENTICATION_FAILED:
                runOnUiShowToast(context.getResources().getString(R.string.shipper_authentication_failed));
                break;
            case ReturnCode.OPPTIONS_AUTHENTICATION_FAILED:
                runOnUiShowToast(context.getResources().getString(R.string.opptions_authentication_failed));
                break;
            case ReturnCode.USER_DETAILS_OBTAIN_FAILED:
                runOnUiShowToast(context.getResources().getString(R.string.user_details_obtain_failed));
                break;
            case ReturnCode.ILLEGAL_REQUEST:
                runOnUiShowToast(context.getResources().getString(R.string.illegal_request));
                break;
            case ReturnCode.LOGIN_TIMEOUT:
                runOnUiShowToast(context.getResources().getString(R.string.login_timeout));
                break;
            case ReturnCode.ANOTHER_PLACE_LOGIN:
                runOnUiShowToast(context.getResources().getString(R.string.another_place_login));
                break;
            case ReturnCode.EXIT_FAILED:
                runOnUiShowToast(context.getResources().getString(R.string.exit_failed));
                break;
            case ReturnCode.GOODS_OPTIONS_INFORMATION_ADDFAILED:
                runOnUiShowToast(context.getResources().getString(R.string.goods_options_information_addfailed));
                break;
            case ReturnCode.GOODS_OPTIONS_LIST_OBTAIN_FAILED:
                runOnUiShowToast(context.getResources().getString(R.string.goods_options_list_obtain_failed));
                break;
            case ReturnCode.GOODS_OPTIONS_IDEMPITY:
                runOnUiShowToast(context.getResources().getString(R.string.goods_options_idempity));
                break;
            case ReturnCode.GOODS_OPTIONS_DETAILS_OBBTAIN_FAILED:
                runOnUiShowToast(context.getResources().getString(R.string.goods_options_details_obbtain_failed));
                break;
        }
    }
}
